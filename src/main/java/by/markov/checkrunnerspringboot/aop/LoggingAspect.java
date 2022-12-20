package by.markov.checkrunnerspringboot.aop;

import by.markov.checkrunnerspringboot.entities.Check;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {


    @Around("@annotation(CheckInLog)")
    public Object inspectController(ProceedingJoinPoint joinPoint) throws Throwable {
        Check check = (Check) joinPoint.proceed();
        log.info("Payment has been completed. Your the order: {} \nDate: {}", check.getOrder(), check.getDateTime());
        return check;
    }
}
