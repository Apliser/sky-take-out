package com.sky.mapper;

import com.sky.entity.Setmeal;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SetmealMapper {

    void add(Setmeal setmeal);

    Setmeal QueryById(Long id);
}
