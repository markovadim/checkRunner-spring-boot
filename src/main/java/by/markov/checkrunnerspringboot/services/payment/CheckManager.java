package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.aop.CheckInLog;
import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class CheckManager {

    private final CheckPrinter checkPrinter;

    @CheckInLog
    public Check createAndPrintCheck(Order order) {
        Check check = Check.builder()
                .order(order)
                .dateTime(LocalDateTime.now())
                .build();
        checkPrinter.printCheck(check);
        return check;
    }
}
