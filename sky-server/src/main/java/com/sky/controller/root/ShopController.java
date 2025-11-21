package com.sky.controller.root;


import com.sky.result.Result;
import com.sky.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop/")
@Api(tags = "店铺相关接口")
@Slf4j
public class ShopController {
    @Autowired
    private ShopService shopService;

    /**
     * 设置店铺状态
     * @param status 店铺状态
     * @return 店铺状态
     */
    @ApiOperation("设置店铺状态")
    @PutMapping("{status}")
    public Result<Integer> setStatus(@PathVariable("status") Integer status) {
        log.info("设置店铺状态为：{}", status);
        shopService.setStatus(status);
        return Result.success(status);
    }

    @ApiOperation("获取店铺状态")
    @GetMapping("status")
    public Result<Integer> getStatus(){
        log.info("获取店铺状态");
        Integer status = shopService.getStatus();
        return Result.success(status);
    }
}
