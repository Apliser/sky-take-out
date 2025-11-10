package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetMealDishMapper {

    // 新增套餐菜品关系
    void add(SetmealDish setmealDish);
}
