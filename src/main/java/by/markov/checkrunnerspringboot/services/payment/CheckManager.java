package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.aop.CheckInLog;
import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class CheckManager {

    @CheckInLog
    public Check createCheck(Order order) {
        return Check.builder()
                .order(order)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
