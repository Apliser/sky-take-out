package com.sky.service.Interface.user;

import com.sky.entity.Category;

import java.util.List;

public interface UserCategoryService {
    List<Category> QueryCategoryByType(String type);
}
