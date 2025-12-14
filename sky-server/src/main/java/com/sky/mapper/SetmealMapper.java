package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.aop.AutoFillPublic;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealMapper {

    @AutoFillPublic(value = OperationType.INSERT)
    void add(Setmeal setmeal);

    Setmeal QueryById(Long id);

    List<Setmeal> QueryByPage(SetmealPageQueryDTO setmealPageQueryDTO);

    Long batchDelete(List<Long> ids);

    @AutoFillPublic(value = OperationType.UPDATE)
    Long Update(Setmeal setmeal);

    List<Setmeal> QuerySetmealByCategory(Long categoryId);
}
