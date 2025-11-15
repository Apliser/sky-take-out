package com.sky.mapper;

import com.sky.aop.AutoFillPublic;
import com.sky.entity.DishFlavor;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {

    /**
     * 根据菜品id查询菜品口味
     * @param id 菜品id
     * @return 菜品口味列表
     */
    List<DishFlavor> QueryByDishId(Long id);

    /**
     * 新增菜品口味
     * @param flavors 菜品口味
     */
    void addFlavors(List<DishFlavor> flavors);

    /**
     * 根据菜品id批量删除菜品口味
     * @param ids 菜品id列表
     */
    void batchDeleteByDishIds(List<Long> ids);

    /**
     * 更新菜品口味
     * @param flavors 菜品口味
     */
    void updateFlavors(DishFlavor flavors);
}
