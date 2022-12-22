package by.markov.checkrunnerspringboot.controllers;


import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import by.markov.checkrunnerspringboot.services.commandline.CommandLineArgumentsParser;
import by.markov.checkrunnerspringboot.services.orders.OrderManager;
import by.markov.checkrunnerspringboot.services.payment.CheckManager;
import by.markov.checkrunnerspringboot.services.payment.CheckPrinter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopManagerController {

    private final CommandLineArgumentsParser commandLineArgumentsParser;
    private final OrderManager orderManager;
    private final CheckManager checkManager;
    private final CheckPrinter checkPrinter;

    @GetMapping("/check")
    public ResponseEntity<Check> getCheck(@RequestParam (required = false) String... args) {
        ProductInfo productInfo = commandLineArgumentsParser.parseData(args);
        Order order = orderManager.createOrder(productInfo);
        Check check = checkManager.createCheck(order);
        checkPrinter.printCheck(check);
        return ResponseEntity.ok().body(check);
    }
}
