package org.example.springbootjpa.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class ProfilingAspect {

    private final ConcurrentHashMap<String, AtomicInteger> methodCallCounts = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Long> methodExecutionTimes = new ConcurrentHashMap<>();

    @Around("execution(* org.example.springbootjpa..*(..))")
    public Object profileMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long timeTaken = System.currentTimeMillis() - start;

        methodCallCounts.computeIfAbsent(methodName, k -> new AtomicInteger(0)).incrementAndGet();
        methodExecutionTimes.merge(methodName, timeTaken, Long::sum);

        System.out.println("Метод " + methodName + " выполнен за " + timeTaken + " мс. " +
                "Общее количество вызовов: " + methodCallCounts.get(methodName) +
                ". Общая сумма времени выполнения: " + methodExecutionTimes.get(methodName) + " мс.");

        return result;
    }
}

