package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;

    /**
     * 分页查询分类信息
     * @param categoryPageQueryDTO 分页查询参数
     * @return 返回分页结果
     */
    public PageResult SelectByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.SelectByPage(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

     /**
     * 新增分类
     * @param categoryDTO 分类信息
     */
    public void addCategory(CategoryDTO categoryDTO) {
        Category category =Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .status(StatusConstant.ENABLE)
                .build();
        categoryMapper.addCategory(category);
    }

    /**
     * 根据类型查询分类信息
     * @param type 分类类型
     * @return 返回分类列表
     */
    public List<Category> SelectByType(Integer type) {
        List<Category> categories = categoryMapper.SelectByType(type);
        return categories;
    }

    /**
     * 根据id删除分类
     * @param id 分类id
     */
    public void DeleteById(Long id) {
        //分类下存在菜品或者套餐则不能删除
        Dish dishCount = dishMapper.QueryById(id);
        Setmeal setmealCount = setmealMapper.QueryById(id);
        if(dishCount != null || setmealCount != null){
            throw new IllegalArgumentException("分类下存在菜品或者套餐，不能删除");
        }
        categoryMapper.DeleteById(id);
    }

     /**
     * 启用或停用分类
     * @param status 状态
     * @param id 分类id
     */
    public void OnOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .build();
        categoryMapper.Update(category);
    }

     /**
     * 更新分类
     * @param categoryDTO 分类信息
     */
    public void Update(CategoryDTO categoryDTO) {
        Category category =Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .build();
        categoryMapper.Update(category);
    }
}
