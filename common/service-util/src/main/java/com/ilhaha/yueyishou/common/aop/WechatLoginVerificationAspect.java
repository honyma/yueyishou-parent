package com.ilhaha.yueyishou.common.aop;

import com.ilhaha.yueyishou.common.constant.RedisConstant;
import com.ilhaha.yueyishou.common.execption.YueYiShouException;
import com.ilhaha.yueyishou.common.result.ResultCodeEnum;
import com.ilhaha.yueyishou.common.util.AuthContextHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Author ilhaha
 * @Create 2024/8/18 10:17
 * @Version 1.0
 */
@Aspect
@Component
public class WechatLoginVerificationAspect {

    @Resource
    private RedisTemplate redisTemplate;

    @Pointcut("@annotation(com.ilhaha.yueyishou.common.anno.WechatLoginVerification)")
    public void annotatedClasses() {
    }

    @Around("annotatedClasses()")
    public void wechatLoginVerification(ProceedingJoinPoint joinPoint) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("Token");

        if (ObjectUtils.isEmpty(token)) {
            throw new YueYiShouException(ResultCodeEnum.LOGIN_AUTH);
        }

        String customerId = (String) redisTemplate.opsForValue().get(RedisConstant.USER_LOGIN_KEY_PREFIX + token);
        if (ObjectUtils.isEmpty(customerId)) {
            AuthContextHolder.removeCustomerId();
            throw new YueYiShouException(ResultCodeEnum.LOGIN_AUTH);
        }
        if (!ObjectUtils.isEmpty(customerId)) {
            AuthContextHolder.setCustomerId(Long.valueOf(customerId));
        }

        // 执行目标方法
        joinPoint.proceed();
    }
}
