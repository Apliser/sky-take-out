package com.sky.mapper;


import com.github.pagehelper.Page;
import com.sky.aop.AutoFillPublic;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface CategoryMapper {


    /**
     * 菜品分页查询
     * @param categoryPageQueryDTO 分页查询参数
     * @return 分类分页结果
     */
    Page<Category> SelectByPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 新增分类
     * @param category 分类信息
     */
    @AutoFillPublic(value = OperationType.INSERT)
    void addCategory(Category category);

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return 分类列表
     */
    List<Category> SelectByType(Integer type);

     /**
     * 根据id删除分类
     * @param id 分类id
     */
    void DeleteById(Long id);

    /**
     * 更新分类
     * @param category 分类信息
     */
    @AutoFillPublic(value = OperationType.UPDATE)
    void Update(Category category);

    /**
     * 根据id查询分类
     * @param id 分类id
     * @return 分类信息
     */
    Category QueryById(Long id);

    /**
     * 查询所有分类
     * @return 分类列表
     */
    List<Category> QueryAll();
}
