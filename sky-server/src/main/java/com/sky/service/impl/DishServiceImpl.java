package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据分类id查询菜品
     * @param categoryId 分类id
     * @return
     */
    public List<Dish> QueryByCategoryId(Long categoryId) {
        List<Dish> list = new ArrayList<Dish>();
        list = dishMapper.QueryByCategoryId(categoryId);
        return list;
    }

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult SelectByPage(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<Dish> page = dishMapper.selectByPage(dishPageQueryDTO);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    /**
     * 根据id查询菜品
     * @param id 菜品id
     * @return
     */
    public DishVO QueryById(Long id) {
        Dish dish = dishMapper.QueryById(id);
        String name = categoryMapper.QueryById(dish.getCategoryId()).getName();
        List<DishFlavor> flavors = dishFlavorMapper.QueryByDishId(id);
        DishVO dishVo =DishVO.builder()
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
}
