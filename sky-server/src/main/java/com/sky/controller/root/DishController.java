package com.sky.controller.root;

import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api("菜品相关接口")
@RestController
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @ApiOperation("菜品分页查询")
    @GetMapping("/page")
    public Result<PageResult> QueryByPage(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询:");
        PageResult pageResult = dishService.SelectByPage(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 根据分类id查询菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> QueryByCategoryId(@RequestParam("categoryId") Long categoryId){
        log.info("根据分类id查询菜品");
        List<Dish> dishes=dishService.QueryByCategoryId(categoryId);
        return Result.success(dishes);
    }

    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> QueryById(@PathVariable("id") Long id){
        log.info("根据id查询菜品");
        DishVO dishVO=dishService.QueryById(id);
        return Result.success(dishVO);
    }
}
