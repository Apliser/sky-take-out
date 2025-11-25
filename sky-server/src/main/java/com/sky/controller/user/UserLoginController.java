package com.sky.controller.user;


import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.service.Interface.user.UserLoginService;
import com.sky.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
@Api(tags = "用户端用户相关接口")
@RequestMapping("/user/user")
public class UserLoginController {


    @Autowired
    private UserLoginService userLoginService;

    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息
     * @return 用户登录信息
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO) throws IOException {
        log.info("用户登录");
        UserLoginVO userLoginVO = userLoginService.Login(userLoginDTO);
        return Result.success(userLoginVO);
    }

    /**
     * 用户登出
     * @return 操作成功
     */
    @ApiOperation("登出功能接口")
    @PostMapping("/logout")
    public Result<String> logout(){
        log.info("用户登出");
        return Result.success("操作成功");
    }
}
