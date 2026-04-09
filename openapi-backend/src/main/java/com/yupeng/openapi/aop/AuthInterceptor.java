package com.yupeng.openapi.aop;

import com.yupeng.openapi.annotation.AuthCheck;
import com.yupeng.openapi.common.ErrorCode;
import com.yupeng.openapi.exception.BusinessException;
import com.yupeng.openapi.model.entity.User;
import com.yupeng.openapi.model.enums.UserRoleEnum;
import com.yupeng.openapi.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Authorization check AOP
 *
 * @author yupeng
 * @from <a href="https://yupi.icu">Code Navigation Community</a>
 */
@Aspect
@Component
public class AuthInterceptor {

    @Resource
    private UserService userService;

    /**
     * Execute interception
     *
     * @param joinPoint
     * @param authCheck
     * @return
     */
    @Around("@annotation(authCheck)")
    public Object doInterceptor(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        // Current logged-in user
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        // No permission required, allow the request
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        // The request must have this role to pass
        UserRoleEnum userRoleEnum = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        if (userRoleEnum == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // If the user is banned, reject directly
        if (UserRoleEnum.BAN.equals(userRoleEnum)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        // Admin permission is required
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)) {
            // User does not have admin permission, reject
            if (!UserRoleEnum.ADMIN.equals(userRoleEnum)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }
        // Permission check passed, allow the request
        return joinPoint.proceed();
    }
}

