package ru.vnevzorov.Shop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
/*
    @AfterReturning(returning = "result", value = "@annotation(ru.vnevzorov.Shop.aspect.annotation.LogResult)")
    public void logResult(Object result) {

        System.out.println("result" + result);

    }*/

    @Around("execution(* ru.vnevzorov.Shop.service..*(..)) && !@annotation(ru.vnevzorov.Shop.aspect.annotation.LogResult)")
    public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {

        Long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();


        Long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executionTime = " + executionTime);
        return proceed;
    }

}
