package com.sky.aop;


import com.sky.enumeration.OperationType;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@Aspect
public class Redis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;



    //.*(..)匹配所有参数
    @Pointcut("execution(* com.sky.service..*.*(..)) && @annotation(com.sky.aop.Redis_AOP)")
    public void RedisPointcut() {
    }

    @Around("RedisPointcut()")
    public List<DishVO> InsertRedis(ProceedingJoinPoint joinPoint) throws Throwable {

        //获取签名算法
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Redis_AOP redis_AOP = methodSignature.getMethod().getAnnotation(Redis_AOP.class);
        OperationType operationType = redis_AOP.value();

        //获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            return null;
        }
        Object entry = args[0];
        List<DishVO> dishVOList = null;
        //根据操作类型，设置对应的值
        if(operationType == OperationType.SELECT) {
            //获取entry的值
            String categoryId = entry.toString();
            ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
            String Key = "dish_" + categoryId;
            //从Redis中获取数据
            Object value = valueOperations.get(Key);
            if(value != null) {
                dishVOList = (List<DishVO>) value;
                return dishVOList;
            }
            //如果Redis中没有数据，则从数据库中查询
            dishVOList = (List<DishVO>) joinPoint.proceed();
            //将数据写入Redis
            valueOperations.set(Key,dishVOList);
        }
        return dishVOList;
    }

}
