package com.sky.controller.root;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api("分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

     /**
     * 分页查询分类信息
     * @param categoryPageQueryDTO 分页查询参数
     * @return 返回分页结果
     */
    @ApiOperation("分页查询分类信息")
    @GetMapping("/page")
    public Result<PageResult> SelectByPage(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分页查询开始");
        PageResult pageResult = categoryService.SelectByPage(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

     /**
     * 新增分类
     * @param categoryDTO 分类传输信息
     * @return 新增成功
     */
    @ApiOperation("新增分类")
    @PostMapping
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类");
        categoryService.addCategory(categoryDTO);
        return Result.success("操作成功");
    }

     /**
     * 根据类型查询分类信息
     * @param type 分类类型
     * @return 分类列表
     */
    @ApiOperation("根据类型查询分类信息")
    @GetMapping("/list")
    public Result<List<Category>> SelectByType(@RequestParam("type") Integer type){
        log.info("根据类型查询分类信息");
        List<Category> categoryList = categoryService.SelectByType(type);
        return Result.success(categoryList);
    }

     /**
     * 根据id删除分类
     * @param id 分类id
     * @return 删除成功
     */
    @ApiOperation("根据id删除分类")
    @DeleteMapping//TODO 分类下有菜品或者套餐时不能删除
    public Result<String> DeleteById(@RequestParam("id") Long id){
        log.info("根据id删除分类");
        categoryService.DeleteById(id);
        return Result.success("操作成功");
    }

     /**
     * 启用或停用分类
     * @param status 状态
     * @param id 分类id
     * @return 操作成功
     */
    @ApiOperation("启用或停用分类")
    @PostMapping("/status/{status}")
    public Result<String> OnOrStop(@PathVariable("status") Integer status, @RequestParam("id") Long id){
        log.info("根据id启用或停用分类");
        categoryService.OnOrStop(status, id);
        return Result.success("操作成功");
    }

     /**
     * 更新分类
     * @param categoryDTO 分类传输信息
     * @return 更新成功
     */
    @ApiOperation("更新分类")
    @PutMapping
    public Result<String> Update(@RequestBody CategoryDTO categoryDTO){
        log.info("更新分类");
        categoryService.Update(categoryDTO);
        return Result.success("操作成功");
    }
}
