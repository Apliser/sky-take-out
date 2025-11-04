package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {


    /**
     * 菜品分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> SelectByPage(CategoryPageQueryDTO categoryPageQueryDTO);


    /**
     * 新增分类
     * @param category
     */
    void addCategory(Category category);

    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    List<Category> SelectByType(Integer type);

    /**
     * 根据id删除分类
     * @param id
     */
    void DeleteById(Long id);

    /**
     * 更新分类
     * @param category
     */
    void Update(Category category);
}
