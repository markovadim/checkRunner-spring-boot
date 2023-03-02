package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static by.markov.checkrunnerspringboot.util.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@ExtendWith({MockitoExtension.class})
class CheckManagerTest {

    @Mock
    private DiscountCard discountCard;
    @InjectMocks
    private Order order;

    @Test
    @DisplayName("Check builder")
    void checkCreateCheckShouldReturnNonNullObject() {
        order = new Order(MockUtil.getShopBasket(), discountCard, TAXABLE_EXAMPLE, DISCOUNT_EXAMPLE, SUM_TO_PAY_EXAMPLE);
        doReturn(DEFAULT_CARD_NUMBER).when(discountCard).getNumber();

        Check check = new CheckManager().createCheck(order);

        assertAll(
                () -> assertEquals(SUM_TO_PAY_EXAMPLE, check.getOrder().getSumToPay()),
                () -> assertEquals(DEFAULT_CARD_NUMBER, check.getOrder().getDiscountCard().getNumber())
        );
    }

    @Test
    void checkCheckBuilderShouldReturnInstanceOfCheckClass() {
        Check check = Check.builder()
                .order(new Order(MockUtil.getShopBasket(), MockUtil.getCards().get(ZERO), TAXABLE_EXAMPLE, DISCOUNT_EXAMPLE, SUM_TO_PAY_EXAMPLE))
                .dateTime(LocalDateTime.now())
                .build();

        assertThat(check).isNotNull()
                .isInstanceOf(Check.class);
    }
}