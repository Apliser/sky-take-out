package com.sky.controller.root;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.Interface.root.DishService;
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
     * @param dishPageQueryDTO 分页查询参数
     * @return 菜品分页结果
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
     * @param categoryId 分类id
     * @return 菜品列表
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
     * @param id 菜品id
     * @return 菜品信息
     */
    @ApiOperation("根据id查询菜品")
    @GetMapping("/{id}")
    public Result<DishVO> QueryById(@PathVariable("id") Long id){
        log.info("根据id查询菜品");
        DishVO dishVO=dishService.QueryById(id);
        return Result.success(dishVO);
    }

    /**
     * 菜品启用或禁用
     * @param status 菜品状态（1起售，0停售）
     * @param id 菜品id
     * @return 操作成功
     */
    @ApiOperation("菜品启用或禁用")
    @PostMapping("/status/{status}")
    public Result<String> OnOrStop(@PathVariable("status") Integer status,@RequestParam("id") Long id){
        log.info("菜品启用或禁用：{}，id：{}",status,id);
        dishService.OnOrStop(status,id);
        return Result.success("操作成功");
    }

    /**
     * 新增菜品
     * @param dishDTO 菜品传输信息
     * @return 新增成功
     */
    @ApiOperation("新增菜品")
    @PostMapping
    public Result<String> add(@RequestBody DishDTO dishDTO) {
        log.info("新增菜品");
        dishService.add(dishDTO);
        return Result.success("新增成功");
    }

     /**
     * 批量删除菜品
     * @param ids 菜品id列表
     * @return 删除成功
     */
    @ApiOperation("批量删除菜品")
    @DeleteMapping()
    public Result<String> batchDelete(@RequestParam("ids") List<Long> ids) {
        log.info("删除菜品：{}",ids);
        dishService.batchDelete(ids);
        return Result.success("删除成功");
    }

    /**
     * 更新菜品
     * @param dishDTO 菜品传输信息
     * @return 更新成功
     */
    @ApiOperation("更新菜品")
    @PutMapping
    public Result<String> update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品");
        dishService.Update(dishDTO);
        return Result.success("更新成功");
    }

}
