package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    List<Dish> QueryByCategoryId(Long categoryId);

    PageResult SelectByPage(DishPageQueryDTO dishPageQueryDTO);

    DishVO QueryById(Long id);

    void OnOrStop(Integer status, Long id);

    void add(DishDTO dishDTO);
}
