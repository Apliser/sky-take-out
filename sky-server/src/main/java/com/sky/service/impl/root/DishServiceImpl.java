package com.sky.service.impl.root;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.BaseException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.Interface.root.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;

     /**
     * 根据分类id查询菜品
     *
     * @param categoryId 分类id
     * @return 菜品列表
     */
    public List<Dish> QueryByCategoryId(Long categoryId) {
        List<Dish> list = dishMapper.QueryByCategoryId(categoryId);
        return list;
    }

    /**
     * 菜品分页查询
     *
     * @param dishPageQueryDTO 菜品分页查询数据传输对象
     * @return 菜品分页结果对象
     */
    public PageResult SelectByPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.selectByPage(dishPageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询菜品
     *
     * @param id 菜品id
     * @return 菜品视图对象
     */
    public DishVO QueryById(Long id) {
        Dish dish = dishMapper.QueryById(id);
        String name = categoryMapper.QueryById(dish.getCategoryId()).getName();
        List<DishFlavor> flavors = dishFlavorMapper.QueryByDishId(id);
        DishVO dishVo = DishVO.builder()
                .id(id)
                .price(dish.getPrice())
                .image(dish.getImage())
                .categoryId(dish.getCategoryId())
                .name(dish.getName())
                .status(dish.getStatus())
                .flavors(flavors)
                .description(dish.getDescription())
                .categoryName(name)
                .updateTime(dish.getUpdateTime())
                .build();
        return dishVo;
    }

    /**
     * 菜品启用或禁用
     *
     * @param status 启用或禁用状态码
     * @param id     菜品id
     */
    public void OnOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishMapper.Update(dish);
    }

    /**
     * 新增菜品
     *
     * @param dishDTO 菜品数据传输对象
     */
    //加上事务注解
    @Transactional
    public void add(DishDTO dishDTO) {
        Dish dish = Dish.builder()
                .name(dishDTO.getName())
                .categoryId(dishDTO.getCategoryId())
                .price(dishDTO.getPrice())
                .image(dishDTO.getImage())
                .description(dishDTO.getDescription())
                .build();
        if (dishDTO.getStatus() == null) dish.setStatus(1);
        else dish.setStatus(dishDTO.getStatus());
        dishMapper.Insert(dish);
        Long dishId = dish.getId();
        if (dishDTO.getFlavors() != null) {
            List<DishFlavor> dishFlavors = dishDTO.getFlavors();
            dishFlavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId)); //TODO 后期添加新增的dish的id(已解决)
            dishFlavorMapper.addFlavors(dishFlavors);
        }
    }

     /**
     * 批量删除菜品
     *
     * @param ids 菜品id列表
     */
    //加上事务注解
    @Transactional
    public void batchDelete(List<Long> ids) {
        //首先删除菜品对应的口味
        ids.forEach(id -> {
            Integer result = setMealDishMapper.QueryByDishId(id);
            Integer status = dishMapper.QueryById(id).getStatus();
            if(status == 1) throw new BaseException("该菜品已起售，不能删除");
            if(result > 0)  throw new BaseException("该菜品已被套餐关联，不能删除");
        });
        dishFlavorMapper.batchDeleteByDishIds(ids);
        dishMapper.batchDelete(ids);
    }

     /**
     * 更新菜品
     *
     * @param dishDTO 菜品数据传输对象
     */
    //加上事务注解
    @Transactional
    public void Update(DishDTO dishDTO) {
        Long dishId = dishDTO.getId();
        //判断口味是否为空，如果不为空，则修改对应的口味(先删除后新增菜品口味)
        if(dishDTO.getFlavors() != null) dishFlavorMapper.deleteBySetmealId(dishId);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        flavors.forEach(dishFlavor -> dishFlavor.setDishId(dishId));
        dishFlavorMapper.addFlavors(flavors);
        //更新菜品信息
        Dish dish = Dish.builder()
                .id(dishId)
                .name(dishDTO.getName())
                .categoryId(dishDTO.getCategoryId())
                .price(dishDTO.getPrice())
                .image(dishDTO.getImage())
                .description(dishDTO.getDescription())
                .build();
        if (dishDTO.getStatus() == null) dish.setStatus(1);
        else dish.setStatus(dishDTO.getStatus());
        dishMapper.Update(dish);

    }

}
