package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.List;

public interface CategoryService {
    PageResult SelectByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    void addCategory(CategoryDTO categoryDTO);

    List<Category> SelectByType(Integer type);

    void DeleteById(Long id);

    void OnOrStop(Integer status, Long id);

    void Update(CategoryDTO categoryDTO);
}
