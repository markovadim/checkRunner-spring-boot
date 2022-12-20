package by.markov.checkrunnerspringboot.services.discountcards;


import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.exceptions.DiscountCardNotFound;
import by.markov.checkrunnerspringboot.repositories.DiscountCardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiscountCardService {

    private final DiscountCardRepository discountCardRepository;

    public DiscountCard findByNumber(long number) {
        return discountCardRepository.findByNumber(number).orElseThrow(DiscountCardNotFound::new);
    }
}
