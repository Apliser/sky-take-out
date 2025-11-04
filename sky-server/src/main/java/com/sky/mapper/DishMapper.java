package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    Page<Dish> selectByPage(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> QueryById(Long categoryId);
}
