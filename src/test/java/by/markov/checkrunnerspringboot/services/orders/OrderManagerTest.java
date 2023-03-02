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

import static by.markov.checkrunnerspringboot.util.TestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderManagerTest {

    @Mock
    private ProductService productService;
    @Mock
    private DiscountCardService discountCardService;

    private OrderManager orderManager;

    @Test
    @DisplayName("Order building")
    void checkCreateOrderShouldReturnNotNullOrder() {
        orderManager = new OrderManager(productService, MockUtil.getEmptyShopBasket(), discountCardService, MockUtil.getProductInfo());
        doReturn(MockUtil.getProducts().get(ZERO)).when(productService).findById(ID_EXAMPLE_ONE);
        doReturn(MockUtil.getProducts().get(ONE)).when(productService).findById(ID_EXAMPLE_TWO);
        doReturn(MockUtil.getProducts().get(TWO)).when(productService).findById(ID_EXAMPLE_THREE);

        Order order = orderManager.createOrder(MockUtil.getProductInfo());

        assertNotNull(order);
    }

    @Test
    @DisplayName("Shop basket creating")
    void checkCreateShopBasketShouldReturnSizeOf3() {
        Map<Product, Integer> shopBasket = MockUtil.getEmptyShopBasket();
        orderManager = new OrderManager(productService, shopBasket, discountCardService, MockUtil.getProductInfo());
        doReturn(MockUtil.getProducts().get(ZERO)).when(productService).findById(ID_EXAMPLE_ONE);
        doReturn(MockUtil.getProducts().get(ONE)).when(productService).findById(ID_EXAMPLE_TWO);
        doReturn(MockUtil.getProducts().get(TWO)).when(productService).findById(ID_EXAMPLE_THREE);

        orderManager.createShopBasket(MockUtil.getProductInfo());
        int actualSize = shopBasket.size();

        assertEquals(EXPECTED_IDS_LIST_SIZE, actualSize);
    }

    @Test
    @DisplayName("Shop basket creating with exception")
    void checkCreateShopBasketShouldThrowException() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());
        doThrow(ProductNotFoundException.class).when(productService).findById(ID_EXAMPLE_ONE);

        assertThrows(ProductNotFoundException.class, () -> orderManager.createShopBasket(MockUtil.getProductInfo()));
    }

    @Test
    @DisplayName("Sum without discount")
    void getOrderSumWithoutDiscount() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        double actual = orderManager.getOrderSumWithoutDiscount(MockUtil.getShopBasket());

        assertEquals(EXPECTED_TAXABLE, actual);
    }

    @Test
    @DisplayName("Check discount card by number")
    void checkDiscountCardShouldReturnTrue() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        doReturn(MockUtil.getProductInfo().getDiscountCard()).when(discountCardService).findByNumber(DEFAULT_CARD_NUMBER);

        assertTrue(orderManager.checkDiscountCard(MockUtil.getProductInfo().getDiscountCard()));
    }

    @ParameterizedTest
    @ValueSource(longs = {3322L, 3221L, 9099L})
    @DisplayName("Discount card not found exception")
    void checkDiscountCardShouldThrowException(long cardNumber) {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        doThrow(DiscountCardNotFound.class).when(discountCardService).findByNumber(cardNumber);

        assertThrows(DiscountCardNotFound.class, () -> orderManager.checkDiscountCard(new DiscountCard(ID_EXAMPLE_ONE, cardNumber)));
    }

    @Test
    @DisplayName("Sum with discount")
    void getOrderSumToPay() {
        orderManager = new OrderManager(productService, MockUtil.getShopBasket(), discountCardService, MockUtil.getProductInfo());

        double actual = orderManager.getOrderSumToPay(MockUtil.getShopBasket());

        assertEquals(EXPECTED_SUM_TO_PAY, actual);
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