package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 分页查询分类信息
     * @param categoryPageQueryDTO
     * @return
     */
    public PageResult SelectByPage(CategoryPageQueryDTO categoryPageQueryDTO) {
        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.SelectByPage(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 新增分类
     * @param categoryDTO
     */
    public void addCategory(CategoryDTO categoryDTO) {
        LocalDateTime now = LocalDateTime.now();
        Long idConstant = BaseContext.getCurrentId();
        Category category =Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .createTime(now)
                .updateTime(now)
                .updateUser(idConstant)
                .createUser(idConstant)
                .status(StatusConstant.ENABLE)
                .build();
        categoryMapper.addCategory(category);
    }


    /**
     * 根据类型查询分类信息
     * @param type
     * @return
     */
    public List<Category> SelectByType(Integer type) {
        List<Category> categories = categoryMapper.SelectByType(type);
        return categories;
    }

    /**
     * 根据id删除分类
     * @param id
     */
    public void DeleteById(Long id) {
        categoryMapper.DeleteById(id);
    }

    /**
     * 启用或停用分类
     * @param status
     * @param id
     */
    public void OnOrStop(Integer status, Long id) {
        LocalDateTime now = LocalDateTime.now();
        Long idConstant = BaseContext.getCurrentId();
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(now)
                .updateUser(idConstant) // TODO 从线程中获取用户id常量
                .build();
        categoryMapper.Update(category);
    }

    /**
     * 更新分类
     * @param categoryDTO
     */
    public void Update(CategoryDTO categoryDTO) {
        LocalDateTime now = LocalDateTime.now();
        Long idConstant = BaseContext.getCurrentId();
        Category category =Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .updateTime(now)
                .updateUser(idConstant) // TODO 从线程中获取用户id常量
                .build();
        categoryMapper.Update(category);
    }
}
