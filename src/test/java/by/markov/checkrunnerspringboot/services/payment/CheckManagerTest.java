package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
class CheckManagerTest {

    public static final double TAXABLE_EXAMPLE = 59.5;
    public static final double DISCOUNT_EXAMPLE = 2.1;
    public static final double SUM_TO_PAY_EXAMPLE = 57.4;

    @Mock
    public DiscountCard discountCard;
    @InjectMocks
    public Order order;

    @Test
    @DisplayName("Check builder")
    void checkCreateCheckShouldReturnNonNullObject() {
        order = new Order(MockUtil.getShopBasket(), discountCard, TAXABLE_EXAMPLE, DISCOUNT_EXAMPLE, SUM_TO_PAY_EXAMPLE);
        Mockito.when(order.getDiscountCard().getNumber()).thenReturn(1111L);

        Check check = new CheckManager().createCheck(order);

        assertAll(
                () -> assertEquals(57.4, check.getOrder().getSumToPay()),
                () -> assertEquals(1111, check.getOrder().getDiscountCard().getNumber()),
                () -> assertEquals(2, check.getOrder().getShopBasket().keySet()
                        .stream()
                        .filter(Product::isDiscount).count()));
    }
}