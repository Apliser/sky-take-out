package com.sky.controller.user;


import com.sky.entity.Category;
import com.sky.result.Result;
import com.sky.service.Interface.user.UserCategoryService;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Api(tags="分类相关接口")
@RequestMapping("/user/category")
public class UserCategoryController {

    @Autowired
    private UserCategoryService userCategoryService;

    /**
     * 根据类型查询分类
     * @param type 分类类型
     * @return 分类列表
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<List<Category>> QueryCategoryByType(@RequestParam(value = "type",required = false) String type){
        log.info("用户根据类型查询分类");
        List<Category> categoryList = userCategoryService.QueryCategoryByType(type);
        return Result.success(categoryList);
    }
}
