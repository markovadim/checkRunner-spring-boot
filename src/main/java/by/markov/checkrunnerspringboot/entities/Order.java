package by.markov.checkrunnerspringboot.entities;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Map<Product, Integer> shopBasket;
    private DiscountCard discountCard;

    private double taxable;
    private double discount;
    private double sumToPay;
}
