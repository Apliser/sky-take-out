package com.sky.controller.user;

import com.sky.result.Result;
import com.sky.service.Interface.user.UserDishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "用户端菜品相关接口")
@Slf4j
@RequestMapping("/user/dish")
public class UserDishViewController {

    @Autowired
    private UserDishService userDishService;

    public Result<List<DishVO>> QueryByCategoryId(Long categoryId) {
        log.info("用户根据菜品分类查询菜品信息,categoryId:{}",categoryId);
        List<DishVO> dishVOs = userDishService.QueryByCategoryId(categoryId);
        return Result.success(dishVOs);
    }
}
