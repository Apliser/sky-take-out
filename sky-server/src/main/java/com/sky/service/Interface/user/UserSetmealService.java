package com.sky.service.Interface.user;

import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;

import java.util.List;

public interface UserSetmealService {
    List<Setmeal> QuerySetmealByCategoryId(Long categoryId);

    List<DishItemVO> QuerySetmealById(Long id);
}
