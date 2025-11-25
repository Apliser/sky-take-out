package com.sky.service.impl.user;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.BaseException;
import com.sky.handler.GlobalExceptionHandler;
import com.sky.mapper.UserMapper;
import com.sky.properties.JwtProperties;
import com.sky.properties.WeChatProperties;
import com.sky.service.Interface.user.UserLoginService;
import com.sky.utils.HttpClientUtil;
import com.sky.utils.JwtUtil;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
@Transactional
public class UserLoginServiceImpl implements UserLoginService {

    private final String urlConstant = "https://api.weixin.qq.com/sns/jscode2session";
    LocalDateTime now = LocalDateTime.now();
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 用户登录
     * @param userLoginDTO 用户登录信息
     * @return 用户登录VO
     */
    @Transactional
    public UserLoginVO Login(UserLoginDTO userLoginDTO) throws IOException {
        String js_code = userLoginDTO.getCode();
        Map<String,String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",js_code);
        map.put("grant_type","authorization_code");
        String result = HttpClientUtil.doPost(urlConstant,map);
        //将result转换为JSONObject
        JSONObject jsonObject = JSONObject.parseObject(result);
        String openid = jsonObject.getString("openid");
        if(openid==null){
            log.error("openid为空");
            globalExceptionHandler.exceptionHandler((new BaseException("openid为空")));
        }
        //判断用户是否存在，如果不存在,则先注册
        User user = userMapper.QueryByOpenid(openid);
        Long id = null;
        //不存在
        if(user==null){
            user = new User();
            user.setOpenid(openid);
            user.setCreateTime(now);
            id = userMapper.insert(user);
        }
        //存在
        id = user.getId();
        Map<String,Object> claims = new HashMap<>();
        claims.put("openid",openid);
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),jwtProperties.getUserTtl(),claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(id)
                .token(token)
                .openid(openid)
                .build();
        return userLoginVO;
    }
}
