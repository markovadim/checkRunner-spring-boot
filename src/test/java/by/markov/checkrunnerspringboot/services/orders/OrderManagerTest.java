package by.markov.checkrunnerspringboot.services.orders;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.repositories.DiscountCardRepository;
import by.markov.checkrunnerspringboot.repositories.ProductRepository;
import by.markov.checkrunnerspringboot.services.discountcards.DiscountCardService;
import by.markov.checkrunnerspringboot.services.payment.CheckManager;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderManagerTest {
    @Mock
    private ProductService productService;
    private final Map<Product, Integer> shopBasket = new HashMap<>();
    @Mock
    private CheckManager checkManager;
    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private DiscountCardService discountCardService;

    @InjectMocks
    private OrderManager orderManager = new OrderManager(productService, shopBasket, checkManager, discountCardService);


    @Test
    void createShopBasket() {
    }

    @Test
    void getOrderSumWithoutDiscount() {
        shopBasket.put(new Product(0L, "Meet", 13.0, false), 1);
        shopBasket.put(new Product(1L, "Milk", 2.0, false), 1);
        shopBasket.put(new Product(2L, "Sugar", 1.5, false), 1);
        shopBasket.put(new Product(3L, "Lemon", 3.0, false), 1);
        assertEquals(19.5, orderManager.getOrderSumWithoutDiscount(shopBasket));
    }

    @Test
    void getOrderSumToPay() {
        shopBasket.put(new Product(0L, "Meet", 13.0, false), 1);
        shopBasket.put(new Product(1L, "Milk", 2.0, true), 12);
        shopBasket.put(new Product(2L, "Sugar", 1.5, false), 1);
        shopBasket.put(new Product(3L, "Lemon", 3.0, true), 7);

        assertEquals(55, orderManager.getOrderSumToPay(shopBasket));
    }
}