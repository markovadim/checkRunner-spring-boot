package by.markov.checkrunnerspringboot.controllers;


import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.exceptions.DiscountCardNotFound;
import by.markov.checkrunnerspringboot.services.discountcards.DiscountCardService;
import by.markov.checkrunnerspringboot.services.orders.OrderManager;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ShopManagerController {

    private final DiscountCardService discountCardService;
    private final OrderManager orderManager;

    @GetMapping("/check")
    public ResponseEntity<Check> getCheck(@RequestParam long id,
                                          @RequestParam int amount,
                                          @RequestParam long card) {

        return ResponseEntity.ok().body(orderManager.createOrderAndDelegateCreateCheck(
                List.of(id),
                List.of(amount),
                discountCardService.findByNumber(card))
        );
    }

}
