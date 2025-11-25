package com.sky.service.Interface.user;

import com.sky.vo.DishVO;

import java.util.List;

public interface UserDishService {
    List<DishVO> QueryByCategoryId(Long categoryId);
}
