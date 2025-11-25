package com.sky.service.impl.root;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.Interface.root.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 新增套餐
     * @param setmealDTO 套餐传输信息
     */
    //加上事务注解
    @Transactional
    public void add(SetmealDTO setmealDTO) {
        // 新增套餐
        Setmeal setmeal = Setmeal.builder()
                .categoryId(setmealDTO.getCategoryId())
                .name(setmealDTO.getName())
                .price(setmealDTO.getPrice())
                .status(setmealDTO.getStatus())
                .description(setmealDTO.getDescription())
                .image(setmealDTO.getImage())
                .build();
        setmealMapper.add(setmeal);
        // 新增套餐与菜品的关系
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            if(setmealDish.getCopies() == null) setmealDish.setCopies(59); //TODO修复剩余份数的问题
            setMealDishMapper.add(setmealDish);
        });

    }

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     * @return 套餐信息
     */
    public SetmealVO QueryById(Long id) {
        Setmeal setmeal = setmealMapper.QueryById(id);
        SetmealVO setmealVO = new SetmealVO();
        //将setmeal中的属性快速抽取到setmealVO中
        org.springframework.beans.BeanUtils.copyProperties(setmeal, setmealVO);
        //查询分类名称
        String categoryName = categoryMapper.QueryById(setmeal.getCategoryId()).getName();
        setmealVO.setCategoryName(categoryName);
        //查询套餐包含的菜品
        List<SetmealDish> setmealDishes = setMealDishMapper.QueryBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     *
     * @param setmealPageQueryDTO 传输信息
     * @return 返回结果
     */
    public PageResult QueryByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<Setmeal> setmeals = setmealMapper.QueryByPage(setmealPageQueryDTO);
        List<SetmealVO> setmealVOS = new ArrayList<>();
        setmeals.forEach(setmeal -> {
            SetmealVO setmealVO = new SetmealVO();
            org.springframework.beans.BeanUtils.copyProperties(setmeal, setmealVO);
            setmealVO.setCategoryName(categoryMapper.QueryById(setmeal.getCategoryId()).getName());
            //查询套餐包含的菜品
            setmealVO.setSetmealDishes(setMealDishMapper.QueryBySetmealId(setmeal.getId()));
            setmealVOS.add(setmealVO);
        });
        PageInfo<SetmealVO> pageInfo = new PageInfo<>(setmealVOS); //TODO 使用PageHelper的PageInfo类
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

    /**
     * 批量删除套餐
     * @param ids 套餐id列表
     * @return 删除的套餐列表
     */
    //加上事务注解
    @Transactional
    public Long batchDelete(List<Long> ids) {
        //删除套餐与菜品的关系
        setMealDishMapper.batchDeleteBySetmealIds(ids);
        //删除套餐
        Long influenceCount = setmealMapper.batchDelete(ids);
        return influenceCount;
    }

    /**
     * 套餐起售或停售
     * @param status 套餐状态（1起售，0停售）
     * @param id     套餐id
     */
    public void OnOrStop(Integer status, Long id) {
        //更新状态
        Setmeal setmeal = Setmeal.builder()
                .id(id)
                .status(status)
                .build();
        setmealMapper.Update(setmeal);
    }

    /**
     * 更新套餐信息
     * @param setmealDTO 套餐传输信息
     * @return 更新的行数
     */
    //加上事务注解
    @Transactional
    public Long Update(SetmealDTO setmealDTO) {
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        //设置套餐id
        setmealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setmealDTO.getId()));
        //更新套餐与菜品的关系表
        setmealDishes.forEach(setmealDish -> setMealDishMapper.Update(setmealDish));
        //更新菜品
        setmealDishes.forEach(setmealDish -> {
                //更新菜品
                Dish dish = Dish.builder()
                        .id(setmealDish.getDishId())
                        .price(setmealDish.getPrice())
                        .name(setmealDish.getName())
                        .build();
                dishMapper.Update(dish);
        });
        // 更新套餐
        Setmeal setmeal = Setmeal.builder()
                .build();
        org.springframework.beans.BeanUtils.copyProperties(setmealDTO, setmeal);
        if(setmeal.getStatus()==null) setmeal.setStatus(1);
        Long influenceCount = setmealMapper.Update(setmeal);
        return influenceCount;
    }

}
