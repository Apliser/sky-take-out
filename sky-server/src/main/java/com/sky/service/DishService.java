package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    List<Dish> QueryById(Long categoryId);

    PageResult SelectByPage(DishPageQueryDTO dishPageQueryDTO);
}
