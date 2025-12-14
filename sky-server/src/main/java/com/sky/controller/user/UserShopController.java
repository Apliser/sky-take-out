package com.sky.controller.user;


import com.sky.result.Result;
import com.sky.service.Interface.root.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Api(tags = "用户端店铺接口")
@RequestMapping("/user/shop/")
public class UserShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 获取店铺状态
     * @return 店铺状态
     */
    @ApiOperation("获取店铺状态")
    @GetMapping("/status")
    public Result<Integer> getStatus() {
        log.info("获取店铺状态");
        Integer status = shopService.getStatus();
        return Result.success(status);
    }

}
