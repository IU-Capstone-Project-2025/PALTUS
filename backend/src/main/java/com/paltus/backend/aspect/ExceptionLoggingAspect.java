package com.paltus.backend.aspect;

import java.util.Arrays;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.slf4j.LoggerFactory;

@Aspect
@Component
public class ExceptionLoggingAspect {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ExceptionLoggingAspect.class);

    @org.aspectj.lang.annotation.Around("execution(* com.paltus.backend..*(..)) && !within(com.paltus.backend.config.JwtFilter)")
    public Object logAround(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Throwable ex) {
            String methodName = joinPoint.getSignature().toShortString();
            Object[] args = joinPoint.getArgs();

            log.error("Exception in method: {}", methodName);
            log.error("Arguments: {}", Arrays.toString(args));
            log.error("Type: {}", ex.getClass().getName());
            log.error("Message: {}", ex.getMessage());

            StackTraceElement element = ex.getStackTrace()[0];
            log.error("Location: {}.{} (line {})", element.getClassName(), element.getMethodName(),
                    element.getLineNumber());

            throw ex;
        }
    }
}
