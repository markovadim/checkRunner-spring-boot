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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @Mock
    public DiscountCardRepository discountCardRepository;
    @InjectMocks
    public DiscountCardService discountCardService;

    @Captor
    public ArgumentCaptor<Long> captor;

    @Test
    @DisplayName("Find discount card without exception")
    void checkFindByNumberShouldReturnId0() {
        when(discountCardRepository.findByNumber(1111)).thenReturn(java.util.Optional.ofNullable(MockUtil.getCards().get(0)));

        assertEquals(0, discountCardService.findByNumber(1111).getId());
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
        when(discountCardRepository.findByNumber(1111)).thenReturn(java.util.Optional.ofNullable(MockUtil.getCards().get(0)));

        discountCardService.findByNumber(1111);
        verify(discountCardRepository).findByNumber(captor.capture());

        assertEquals(1111, captor.getValue());
    }
}