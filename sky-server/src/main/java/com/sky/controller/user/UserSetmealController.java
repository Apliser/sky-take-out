package com.sky.controller.user;


import com.sky.entity.Dish;
import com.sky.entity.Setmeal;
import com.sky.mapper.SetmealMapper;
import com.sky.result.Result;
import com.sky.service.Interface.root.SetmealService;
import com.sky.service.Interface.user.UserSetmealService;
import com.sky.vo.DishItemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/setmeal")
@Api(tags = "用户端套餐相关接口")
public class UserSetmealController {

    @Autowired
    private UserSetmealService userSetmealService;


    @ApiOperation("根据分类查询套餐信息")
    @GetMapping("/list")
    public Result<List<Setmeal>> QuerySetmealByCategoryId(@RequestParam("categoryId") Long categoryId){
        log.info("用户根据分类查询套餐");
        List<Setmeal> setmealList = userSetmealService.QuerySetmealByCategoryId(categoryId);
        return Result.success(setmealList);
    }

    @ApiOperation("根据套餐id查询套餐包含的菜品信息")
    @GetMapping("/dish/{id}")
    public Result<List<DishItemVO>> QuerySetmealById(@PathVariable("id") Long id){
        log.info("根据套餐id查询套餐包含的菜品信息");
        List<DishItemVO> dishItemVOList = userSetmealService.QuerySetmealById(id);
        return Result.success(dishItemVOList);
    }
}
