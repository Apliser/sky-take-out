package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sky.context.BaseContext;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setMealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 新增套餐
     * @param setmealDTO 套餐传输信息
     */
    public void add(SetmealDTO setmealDTO) {
        Long idConstant = BaseContext.getCurrentId();
        LocalDateTime now = LocalDateTime.now();
        // 新增套餐
        Setmeal setmeal = Setmeal.builder()
                .categoryId(setmealDTO.getCategoryId())
                .name(setmealDTO.getName())
                .price(setmealDTO.getPrice())
                .status(setmealDTO.getStatus())
                .description(setmealDTO.getDescription())
                .image(setmealDTO.getImage())
                .createTime(now)
                .updateTime(now)
                .createUser(idConstant)
                .updateUser(idConstant)
                .build();
        setmealMapper.add(setmeal);
        // 新增套餐与菜品的关系
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        setmealDishes.forEach(setmealDish -> {
            setmealDish.setSetmealId(setmealId);
            setMealDishMapper.add(setmealDish);
        });

    }

    /**
     * 根据id查询套餐信息
     * @param id 套餐id
     * @return 套餐信息
     */
    public SetmealVO QueryById(Long id) {
        Setmeal setmeal = setmealMapper.QueryById(id);
        SetmealVO setmealVO = new SetmealVO();
        //将setmeal中的属性快速抽取到setmealVO中
        org.springframework.beans.BeanUtils.copyProperties(setmeal, setmealVO);
        //查询分类名称
        String categoryName = categoryMapper.QueryById(setmeal.getCategoryId()).getName();
        setmealVO.setCategoryName(categoryName);
        //查询套餐包含的菜品
        List<SetmealDish> setmealDishes = setMealDishMapper.QueryBySetmealId(id);
        setmealVO.setSetmealDishes(setmealDishes);
        return setmealVO;
    }

    /**
     *
     * @param setmealPageQueryDTO 传输信息
     * @return 返回结果
     */
    public PageResult QueryByPage(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        List<Setmeal> setmeals = setmealMapper.QueryByPage(setmealPageQueryDTO);
        List<SetmealVO> setmealVOS = new ArrayList<>();
        setmeals.forEach(setmeal -> {
            SetmealVO setmealVO = new SetmealVO();
            org.springframework.beans.BeanUtils.copyProperties(setmeal, setmealVO);
            setmealVO.setCategoryName(categoryMapper.QueryById(setmeal.getCategoryId()).getName());
            //查询套餐包含的菜品
            setmealVO.setSetmealDishes(setMealDishMapper.QueryBySetmealId(setmeal.getId()));
            setmealVOS.add(setmealVO);
        });
        PageInfo<SetmealVO> pageInfo = new PageInfo<>(setmealVOS); //TODO 使用PageHelper的PageInfo类
        PageResult pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

}
