package com.sky.controller.root;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/admin/category")
@Api("分类相关接口")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    /**
     * 分页查询分类信息
     * @param categoryPageQueryDTO
     * @return
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
     * @param categoryDTO
     * @return
     */
    @ApiOperation("新增分类")
    @PostMapping
    public Result<String> addCategory(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类");
        categoryService.addCategory(categoryDTO);
        return Result.success("操作成功");
    }
}
