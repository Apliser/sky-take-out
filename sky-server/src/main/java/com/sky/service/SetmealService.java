package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

@Service
public interface SetmealService {
    void add(SetmealDTO setmealDTO);

    SetmealVO QueryById(Long id);

    PageResult QueryByPage(SetmealPageQueryDTO setmealPageQueryDTO);
}
