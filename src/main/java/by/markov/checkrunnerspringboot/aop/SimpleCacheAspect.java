package by.markov.checkrunnerspringboot.aop;


import by.markov.checkrunnerspringboot.cache.Cache;
import by.markov.checkrunnerspringboot.cache.entities.Customer;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


@Aspect
@Component
@RequiredArgsConstructor
public class SimpleCacheAspect {
    private final Cache<Long, Customer> cache;

    /**
     * Aspect of checking Customer entity in cache.
     * If result by id not null, cache return customer.
     * If result not found aspect delegate control to customer service with database and put customer from database in cache.
     */

    @Around("@annotation(SimpleCache)")
    public Object inspectDao(ProceedingJoinPoint joinPoint) throws Throwable {
        Customer customer = null;
        if (joinPoint.getArgs()[0] instanceof Long) {
            if (cache.get((long) joinPoint.getArgs()[0]) != null) {
                customer = cache.get((Long) joinPoint.getArgs()[0]);
            }
        } else {
            customer = (Customer) joinPoint.proceed();
        }
        cache.put((Long) joinPoint.getArgs()[0], customer);
        return customer;
    }
}
