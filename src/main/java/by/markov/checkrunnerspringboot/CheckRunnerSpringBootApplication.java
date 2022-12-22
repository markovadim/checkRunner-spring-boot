package by.markov.checkrunnerspringboot;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import by.markov.checkrunnerspringboot.services.commandline.CommandLineArgumentsParser;
import by.markov.checkrunnerspringboot.services.orders.OrderManager;
import by.markov.checkrunnerspringboot.services.payment.CheckManager;
import by.markov.checkrunnerspringboot.services.payment.CheckPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class CheckRunnerSpringBootApplication implements CommandLineRunner {
    private final CommandLineArgumentsParser commandLineArgumentsParser;
    private final OrderManager orderManager;
    private final CheckManager checkManager;
    private final CheckPrinter checkPrinter;

    public static void main(String[] args) {
        SpringApplication.run(CheckRunnerSpringBootApplication.class, args);
    }

    @Override
    public void run(String[] args) {
        /* toDo add check by empty args */
        ProductInfo productInfo = commandLineArgumentsParser.parseData(args);
        Order order = orderManager.createOrder(productInfo);
        Check check = checkManager.createCheck(order);
        checkPrinter.printCheck(check);
    }
}
