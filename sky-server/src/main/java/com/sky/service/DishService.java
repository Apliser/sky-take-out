package com.sky.service;

import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

public interface DishService {
    PageResult SelectByPage(DishPageQueryDTO dishPageQueryDTO);
}
