package com.example.school.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import java.time.Duration;
import java.time.Instant;

@Slf4j
@Aspect
@Component
public class LoggerAspect {
    @Around("execution(* com.example.school..*.*(..))")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("--------------------------------------------------------->");
        log.info(joinPoint.getSignature().toString() + " Method execution starts");
        Instant start = Instant.now();
        Object returnObj = joinPoint.proceed();
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        log.info("Time elapsed to execute " + joinPoint.getSignature().toString() + " method is " + timeElapsed);
        log.info(joinPoint.getSignature().toString() + " method execution completed");
        log.info("------------------------------------------------------------->");
        return returnObj;
    }
    @AfterThrowing(value = "execution(* com.example.school..*.*(..))", throwing = "ex")
    public void logException(JoinPoint joinPoint, Exception ex) {
        log.error(joinPoint.getSignature() + " An Exception happened due to: " + ex.getMessage());
    }
}
