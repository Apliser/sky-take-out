package com.sky.service.impl.user;


import com.sky.aop.Redis_AOP;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.Interface.user.UserDishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDishServiceImpl implements UserDishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 用户根据菜品分类查询菜品信息
     * @param categoryId 菜品分类id
     * @return 菜品信息
     */
    @Transactional
    @Redis_AOP(value = OperationType.SELECT)
    public List<DishVO> QueryByCategoryId(Long categoryId) {
        List<Dish> dishes = dishMapper.QueryByCategoryId(categoryId);
        String categoryName = categoryMapper.QueryById(categoryId).getName();
        List<DishVO> dishVOList = new ArrayList<>();
        dishes.forEach(dish -> {
            DishVO dishVO = new DishVO();
            BeanUtils.copyProperties(dish, dishVO);
            List<DishFlavor> dishFlavors = dishFlavorMapper.QueryByDishId(dish.getId());
            dishVO.setFlavors(dishFlavors);
            dishVO.setCategoryName(categoryName);
            dishVOList.add(dishVO);
        });
        return dishVOList;
    }
}
