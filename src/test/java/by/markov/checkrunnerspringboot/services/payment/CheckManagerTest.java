package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckManagerTest {

    private DiscountCard discountCard;
    private Map<Product, Integer> shopBasket;
    private double taxable;
    private double discount;
    private double sumToPay;
    private CheckManager checkManager;
    private Order order;

    @BeforeEach
    void setUp() {
        discountCard = new DiscountCard(0L, 1111);
        shopBasket = Map.of(new Product(0L, "Meet", 13.0, false), 1,
                new Product(1L, "Milk", 2.0, true), 12,
                new Product(2L, "Sugar", 1.5, false), 1,
                new Product(3L, "Lemon", 3.0, true), 7);
        taxable = 100.0;
        discount = 10.0;
        sumToPay = 90.0;
        checkManager = new CheckManager();
        order = new Order(shopBasket, discountCard, taxable, discount, sumToPay);
    }


    @Test
    @DisplayName("Check builder")
    void createCheck() {
        Check check = checkManager.createCheck(order);

        assertAll("order", () -> assertEquals(90.0, check.getOrder().getSumToPay()),
                () -> assertEquals(1111, check.getOrder().getDiscountCard().getNumber()),
                () -> assertEquals(2, check.getOrder().getShopBasket().keySet()
                        .stream()
                        .filter(Product::isDiscount).count()));
    }
}