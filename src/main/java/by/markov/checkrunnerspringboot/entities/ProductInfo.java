package by.markov.checkrunnerspringboot.entities;


import by.markov.checkrunnerspringboot.entities.DiscountCard;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@RequiredArgsConstructor
public class ProductInfo {

    private final List<Long> productIds;
    private final List<Integer> productAmount;
    private final DiscountCard discountCard;
}
