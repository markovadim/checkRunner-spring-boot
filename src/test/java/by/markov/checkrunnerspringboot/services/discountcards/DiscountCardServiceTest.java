package by.markov.checkrunnerspringboot.services.discountcards;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.exceptions.DiscountCardNotFound;
import by.markov.checkrunnerspringboot.repositories.DiscountCardRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DiscountCardServiceTest {

    @Mock
    private DiscountCardRepository discountCardRepository;
    @InjectMocks
    private DiscountCardService discountCardService;
    List<DiscountCard> cards = List.of(
            new DiscountCard(0, 1111),
            new DiscountCard(1, 2222),
            new DiscountCard(2, 3333)
    );

    @Test
    void findByNumber() {
        when(discountCardRepository.findByNumber(1111)).thenReturn(java.util.Optional.ofNullable(cards.get(0)));
        assertEquals(0, discountCardService.findByNumber(1111).getId());
        assertThrows(DiscountCardNotFound.class, () -> discountCardService.findByNumber(1234));
    }
}