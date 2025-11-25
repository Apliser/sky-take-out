package com.sky.service.impl.root;

import com.sky.service.Interface.root.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置店铺状态
     * @param status 店铺状态
     */
    public void setStatus(Integer status) {
        //将店铺装填status存储到redis中
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set("status", status.toString());
    }

    /**
     * 获取店铺状态
     * @return 返回结果
     */
    public Integer getStatus() {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        return Integer.valueOf((String)valueOperations.get("status"));
    }
}
