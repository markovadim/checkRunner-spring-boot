package by.markov.checkrunnerspringboot.services.orders;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import by.markov.checkrunnerspringboot.repositories.DiscountCardRepository;
import by.markov.checkrunnerspringboot.repositories.ProductRepository;
import by.markov.checkrunnerspringboot.services.discountcards.DiscountCardService;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class OrderManagerTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private DiscountCardRepository discountCardRepository;
    private Map<Product, Integer> shopBasket;
    private ProductInfo productInfo;
    private ProductService productService;
    private DiscountCardService discountCardService;
    private OrderManager orderManager;

    @BeforeEach
    void setUp() {
        productInfo = new ProductInfo(
                List.of(1L, 2L, 3L),
                List.of(1, 2, 3),
                new DiscountCard(0L, 1234)
        );
        shopBasket = Map.of(new Product(0L, "Meet", 13.0, false), 1,
                new Product(1L, "Milk", 2.0, true), 12,
                new Product(2L, "Sugar", 1.5, false), 1,
                new Product(3L, "Lemon", 3.0, true), 7);
        productService = new ProductService(productRepository);
        discountCardService = new DiscountCardService(discountCardRepository);
        orderManager = new OrderManager(productService, shopBasket, discountCardService, productInfo);
    }


    @Test
    @DisplayName("Sum with discount")
    void getOrderSumToPay() {
        assertEquals(55, Math.round(orderManager.getOrderSumToPay(shopBasket)));
    }

    @Test
    @DisplayName("Sum without discount")
    void getOrderSumWithoutDiscount() {
        assertEquals(59.5, orderManager.getOrderSumWithoutDiscount(shopBasket));
    }

    @Test
    void checkDiscountCard() {
        Mockito.when(discountCardRepository.findByNumber(1234)).thenReturn(Optional.of(productInfo.getDiscountCard()));

        assertTrue(orderManager.checkDiscountCard(productInfo.getDiscountCard()));
    }

    @Test
    @DisplayName("Order builder")
    void buildOrder() {
        Order order = orderManager.buildOrder(100.0, 90.0);
        assertAll("order",
                () -> assertEquals(productInfo.getDiscountCard(), order.getDiscountCard()),
                () -> assertEquals(10.0, order.getDiscount()),
                () -> assertEquals(4, order.getShopBasket().size())
        );
    }
}