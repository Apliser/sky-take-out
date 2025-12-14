package com.sky.service.impl.user;

import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetMealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.Interface.user.UserSetmealService;
import com.sky.vo.DishItemVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "套餐缓存")
@Service
public class UserSetmealServiceImpl implements UserSetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetMealDishMapper setmealDishmapper;
    @Autowired
    private DishMapper dishMapper;

    /**
     * 根据分类查询套餐
     * @param categoryId 分类id
     * @return 套餐列表
     */
    @Cacheable(key = "#categoryId",cacheNames = "套餐总览")
    public List<Setmeal> QuerySetmealByCategoryId(Long categoryId) {
        List<Setmeal> setmeals = setmealMapper.QuerySetmealByCategory(categoryId);
        return setmeals;
    }

    /**
     * 根据套餐id查询套餐包含的菜品信息
     * @param id 套餐id
     * @return 菜品信息列表
     */
    @Transactional
    @Cacheable(key = "id",cacheNames = "套餐所包含的菜品")
    public List<DishItemVO> QuerySetmealById(Long id) {
        List<SetmealDish> setmealDishes = setmealDishmapper.QueryBySetmealId(id);
        List<DishItemVO> dishItemVOs = new ArrayList<DishItemVO>();
        setmealDishes.forEach(item -> {
            Dish dish = dishMapper.QueryById(item.getId());
            DishItemVO dishItemVO = DishItemVO.builder()
                    .name(dish.getName())
                    .copies(item.getCopies())
                    .image(dish.getImage())
                    .description(dish.getDescription())
                    .build();
            dishItemVOs.add(dishItemVO);
        });
        return dishItemVOs;
    }
}
