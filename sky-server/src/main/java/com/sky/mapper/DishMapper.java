package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.aop.AutoFillPublic;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper {
    Page<Dish> selectByPage(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> QueryByCategoryId(Long categoryId);

    Dish QueryById(Long id);

    @AutoFillPublic(value = OperationType.UPDATE)
    void Update(Dish dish);

    @AutoFillPublic(value = OperationType.INSERT)
    Long Insert(Dish dish);

    void batchDelete(List<Long> ids);
}
