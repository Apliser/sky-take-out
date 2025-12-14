package com.sky.service.impl.user;

import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.Interface.user.UserCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserCategoryServiceImpl implements UserCategoryService{

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return 分类列表
     */
    public List<Category> QueryCategoryByType(String type) {
        if(type == null){
            return categoryMapper.QueryAll();
        }
        List<Category> categoryList = categoryMapper.SelectByType(Integer.valueOf(type));
        return categoryList;
    }
}
