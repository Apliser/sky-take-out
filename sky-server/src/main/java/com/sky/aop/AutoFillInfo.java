package com.sky.aop;

import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;

@Aspect
@Slf4j
@Component
public class AutoFillInfo {
    //.*(..)匹配所有参数
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.aop.AutoFillPublic)")
    public void autoFillPointcut() {
    }


    //插入---要用前置通知before
    //匹配到autoFillPointcut()方法，执行auto Fill()方法
    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFillPublic autoFillPublic = methodSignature.getMethod().getAnnotation(AutoFillPublic.class);
        OperationType operationType = autoFillPublic.value();
        //判断操作类型
        LocalDateTime now = LocalDateTime.now();
        Long idConstant = BaseContext.getCurrentId();
        //获取方法的参数
        Object[] args = joinPoint.getArgs();
        if (args.length == 0) {
            return;
        }
        Object entry = args[0];
        //根据操作类型，设置对应的值
        if (operationType == OperationType.INSERT) {
            //获取set方法并赋值
            entry.getClass().getMethod("setCreateTime", LocalDateTime.class).invoke(entry, now);
            entry.getClass().getMethod("setUpdateTime", LocalDateTime.class).invoke(entry, now);
            entry.getClass().getMethod("setCreateUser", Long.class).invoke(entry, idConstant);
            entry.getClass().getMethod("setUpdateUser", Long.class).invoke(entry, idConstant);
        }
        if (operationType == OperationType.UPDATE) {
            entry.getClass().getMethod("setUpdateTime", LocalDateTime.class).invoke(entry, now);
            entry.getClass().getMethod("setUpdateUser", Long.class).invoke(entry, idConstant);
        }
    }
}
