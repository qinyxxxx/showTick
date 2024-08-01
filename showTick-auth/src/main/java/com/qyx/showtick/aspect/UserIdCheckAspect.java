package com.qyx.showtick.aspect;

import com.qyx.showtick.annotation.CheckUserId;
import com.qyx.showtick.common.exception.Asserts;
import com.qyx.showtick.component.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by Yuxin Qin on 8/1/24
 */

@Aspect
@Component
public class UserIdCheckAspect {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;

    @Before("@annotation(checkUserId)")
    public void checkUserId(JoinPoint joinPoint, CheckUserId checkUserId) throws Throwable {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            Asserts.fail("Token is missing");
        }

        Long userIdFromToken = jwtUtil.getUserIdFromToken(token);
        if (userIdFromToken == null) {
            Asserts.fail("Invalid token");
        }

        Object[] args = joinPoint.getArgs();
        Long resourceId = getResourceId(joinPoint); // 从URL路径变量中获取资源ID
        if (resourceId == null) {
            Asserts.fail("Resource ID is missing");
        }

        Long userIdFromResource = getUserIdFromResource(joinPoint, checkUserId.resourceIdMethod(), resourceId);
        if (!userIdFromToken.equals(userIdFromResource)) {
            Asserts.fail("User ID does not match");
        }
    }

    private Long getResourceId(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof PathVariable) {
                    return (Long) args[i];
                }
            }
        }
        return null;
    }

    private Long getUserIdFromResource(JoinPoint joinPoint, String methodName, Long resourceId) throws Exception {
        if (methodName == null || methodName.isEmpty()) {
            return null;
        }

        Object target = joinPoint.getTarget();
        Method userIdMethod = target.getClass().getMethod(methodName, Long.class);
        return (Long) userIdMethod.invoke(target, resourceId);
    }
}