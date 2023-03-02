package by.markov.checkrunnerspringboot.services.discountcards;

import by.markov.checkrunnerspringboot.exceptions.DiscountCardNotFound;
import by.markov.checkrunnerspringboot.repositories.DiscountCardRepository;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static by.markov.checkrunnerspringboot.util.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private DiscountCardService discountCardService;

    @Captor
    private ArgumentCaptor<Long> captor;

    @Test
    @DisplayName("Find discount card without exception")
    void checkFindByNumberShouldReturnId0() {
        doReturn(Optional.of(MockUtil.getCards().get(ZERO))).when(discountCardRepository).findByNumber(MOCK_ID);

        assertEquals(ZERO, discountCardService.findByNumber(MOCK_ID).getId());
    }

    @ParameterizedTest
    @ValueSource(longs = {3232, 1144, 9009, 5432})
    @DisplayName("Find discount card with exception")
    void checkFindByNumberShouldReturnException(long cardNumber) {
        assertThrows(DiscountCardNotFound.class, () -> discountCardService.findByNumber(cardNumber));
    }

    @Test
    @DisplayName("Using Argument Captor")
    void checkFindByIdWithUsingArgumentCaptorShouldReturnRightNumber() {
        doReturn(Optional.of(MockUtil.getCards().get(ZERO))).when(discountCardRepository).findByNumber(MOCK_ID);

        discountCardService.findByNumber(MOCK_ID);
        verify(discountCardRepository).findByNumber(captor.capture());

        assertEquals(MOCK_ID, captor.getValue());
    }
}