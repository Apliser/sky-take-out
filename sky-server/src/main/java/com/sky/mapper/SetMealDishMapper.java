package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetMealDishMapper {

    // 新增套餐菜品关系
    void add(SetmealDish setmealDish);

    List<SetmealDish> QueryBySetmealId(Long id);
}
