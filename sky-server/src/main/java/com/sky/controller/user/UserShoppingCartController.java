package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.Interface.user.UserShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user/shoppingCart")
@Api(tags = "购物车相关接口")
public class UserShoppingCartController {

    @Autowired
    private UserShoppingCartService userShoppingCartService;

    /**
     * 查看购物车
     * @return 购物车列表
     */
    @GetMapping("/list")
    @ApiOperation("查看购物车")
    public Result<List<ShoppingCart>> QueryAll() {
        log.info("用户查看购物车");
        List<ShoppingCart> shoppingCartList = userShoppingCartService.QueryAll();
        return Result.success(shoppingCartList);
    }

    /**
     * 删除购物车
     * @param shoppingCartDTO 购物车DTO
     * @return 删除成功
     */
    @PostMapping("/sub")
    @ApiOperation("删除购物车")
    public Result<String> DeleteShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("用户删除购物车");
        userShoppingCartService.DeleteShoppingCart(shoppingCartDTO);
        return Result.success("删除成功");
    }

    /**
     * 清空购物车
     * @return 清空成功
     */
    @ApiOperation("清空购物车")
    @DeleteMapping("/clean")
    public Result<String> ClearAll(){
        log.info("清空购物车");
        userShoppingCartService.ClearAll();
        return Result.success("清空成功");
    }

    /**
     * 添加购物车
     * @param shoppingCartDTO 购物车DTO
     * @return 添加成功
     */
    @ApiOperation("添加购物车")
    @PostMapping("/add")
    public Result<String> InsertShoppingCart(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("用户添加购物车");
        userShoppingCartService.InsertShoppingCart(shoppingCartDTO);
        return Result.success("添加成功");
    }
}
