package by.markov.checkrunnerspringboot.services.orders;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.exceptions.DiscountCardNotFound;
import by.markov.checkrunnerspringboot.exceptions.ProductNotFoundException;
import by.markov.checkrunnerspringboot.services.discountcards.DiscountCardService;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OrderManagerTest {

    @Mock
    public ProductService productService;
    @Mock
    public DiscountCardService discountCardService;

    public OrderManager orderManager;

    @Test
    @DisplayName("Order building")
    void checkCreateOrderShouldReturnNotNullOrder() {
        orderManager = new OrderManager(productService, MockUtil.getEmptyShopBasket(), discountCardService, MockUtil.getProductInfo());
        when(productService.findById(1L)).thenReturn(MockUtil.getProducts().get(0));
        when(productService.findById(2L)).thenReturn(MockUtil.getProducts().get(1));
        when(productService.findById(3L)).thenReturn(MockUtil.getProducts().get(2));

        Order order = orderManager.createOrder(MockUtil.getProductInfo());

        assertNotNull(order);
    }

    @Test
    @DisplayName("Shop basket creating")
    void checkCreateShopBasketShouldReturnSizeOf3() {
        Map<Product, Integer> shopBasket = MockUtil.getEmptyShopBasket();
        orderManager = new OrderManager(productService, shopBasket, discountCardService, MockUtil.getProductInfo());
        when(productService.findById(1L)).thenReturn(MockUtil.getProducts().get(0));
        when(productService.findById(2L)).thenReturn(MockUtil.getProducts().get(1));
        when(productService.findById(3L)).thenReturn(MockUtil.getProducts().get(2));

        orderManager.createShopBasket(MockUtil.getProductInfo());

        assertEquals(3, shopBasket.size());
    }

    @Test
    @DisplayName("Shop basket creating with exception")
    void checkCreateShopBasketShouldThrowException() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());
        when(productService.findById(1L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> orderManager.createShopBasket(MockUtil.getProductInfo()));
    }

    @Test
    @DisplayName("Sum without discount")
    void getOrderSumWithoutDiscount() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        double actual = orderManager.getOrderSumWithoutDiscount(MockUtil.getShopBasket());

        assertEquals(138.71, Math.round(actual * 100) / 100.0);
    }

    @Test
    @DisplayName("Check discount card by number")
    void checkDiscountCardShouldReturnTrue() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        when(discountCardService.findByNumber(1234)).thenReturn(MockUtil.getProductInfo().getDiscountCard());

        assertTrue(orderManager.checkDiscountCard(MockUtil.getProductInfo().getDiscountCard()));
    }

    @ParameterizedTest
    @ValueSource(longs = {3322L, 3221L, 9099L})
    @DisplayName("Discount card not found exception")
    void checkDiscountCardShouldThrowException(long cardNumber) {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        when(discountCardService.findByNumber(cardNumber)).thenThrow(DiscountCardNotFound.class);

        assertThrows(DiscountCardNotFound.class, () -> orderManager.checkDiscountCard(new DiscountCard(0L, cardNumber)));
    }

    @Test
    @DisplayName("Sum with discount")
    void getOrderSumToPay() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        double actual = orderManager.getOrderSumToPay(MockUtil.getShopBasket());

        assertEquals(130.04, Math.round(actual * 100) / 100.0);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "33.2, 32.2",
            "542.2, 432.2",
            "52.3, 12.2"
    })
    @DisplayName("Order builder")
    void checkBuildOrderShouldReturnDiscountAndNotEmptyOrder(double taxable, double sumToPay) {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        Order order = orderManager.buildOrder(taxable, sumToPay);

        assertAll(
                () -> assertEquals(MockUtil.getProductInfo().getDiscountCard(), order.getDiscountCard()),
                () -> assertEquals(taxable - sumToPay, order.getDiscount()),
                () -> assertFalse(order.getShopBasket().isEmpty())
        );
    }
}