package com.sky.service;


import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService {
    void add(SetmealDTO setmealDTO);

    SetmealVO QueryById(Long id);

    PageResult QueryByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    Long batchDelete(List<Long> ids);

    void OnOrStop(Integer status, Long id);

    Long Update(SetmealDTO setmealDTO);
}
