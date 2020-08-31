package com.order.payment.generic.log.aspect;

import lombok.extern.slf4j.Slf4j;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAspect {

    @Pointcut("within(com.order.payment..*)")
    public void allMethods() {
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void controller() {
    }

    @Pointcut("@within(org.springframework.stereotype.Service)")
    public void service() {
    }

    @Pointcut("within(com.order.payment.*.aggregator.transactional.*)")
    public void aggregatorTransactional() {
    }

    @Around("controller() && allMethods()")
    public Object logControllers(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAndProceed(joinPoint, LogArea.CONTROLLER);
    }

    @Around("service() && allMethods()")
    public Object logServices(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAndProceed(joinPoint, LogArea.SERVICE);
    }

    @Around("aggregatorTransactional() && allMethods()")
    public Object logAggregatorTransactional(ProceedingJoinPoint joinPoint) throws Throwable {
        return logAndProceed(joinPoint, LogArea.AGGREGATOR_TRANSACTIONAL);
    }

    private Object logAndProceed(ProceedingJoinPoint joinPoint, LogArea logArea) throws Throwable {
        final String className = joinPoint.getSignature()
                .getDeclaringType()
                .getName();
        final String methodName = joinPoint.getSignature()
                .getName();
        final Object[] arguments = joinPoint.getArgs();

        log.debug("Entering logArea={} for className={}, methodName={} with arguments={}.", logArea, className,
                  methodName, arguments);
        Object proceed = joinPoint.proceed();
        log.debug("Exiting logArea={} for className={}, methodName={} with proceed={}.", logArea, className, methodName,
                  proceed);
        return proceed;
    }

    enum LogArea {
        CONTROLLER,
        SERVICE,
        AGGREGATOR_TRANSACTIONAL
    }
}
