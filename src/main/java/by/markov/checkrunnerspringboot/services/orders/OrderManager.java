package by.markov.checkrunnerspringboot.services.orders;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.services.discountcards.DiscountCardService;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderManager {

    private final static double DISCOUNT_KF = 0.9;

    private final ProductService productService;
    private final Map<Product, Integer> shopBasket;
    private final DiscountCardService discountCardService;
    private final ProductInfo productInfo;

    public Order createOrder(ProductInfo productInfo) {
        createShopBasket(productInfo);
        double taxable = getOrderSumWithoutDiscount(shopBasket);
        double sumToPay = taxable;
        if (checkDiscountCard(productInfo.getDiscountCard())) {
            sumToPay = getOrderSumToPay(shopBasket);
        }
        return buildOrder(taxable, sumToPay);
    }

    public void createShopBasket(ProductInfo productInfo) {
        for (int i = 0; i < productInfo.getProductIds().size(); i++) {
            shopBasket.put(
                    productService.findById(productInfo.getProductIds().get(i)),
                    productInfo.getProductAmount().get(i)
            );
        }
    }

    public double getOrderSumWithoutDiscount(Map<Product, Integer> shopBasket) {
        double sumWithoutDiscount = 0.0;
        for (Map.Entry<Product, Integer> entry : shopBasket.entrySet()) {
            sumWithoutDiscount += entry.getKey().getPrice() * entry.getValue();
        }
        return sumWithoutDiscount;
    }

    private boolean checkDiscountCard(DiscountCard discountCard) {
        return discountCardService.findByNumber(discountCard.getNumber()) != null;
    }

    public double getOrderSumToPay(Map<Product, Integer> shopBasket) {
        double sumToPay = 0.0;
        for (Map.Entry<Product, Integer> entry : shopBasket.entrySet()) {
            if (entry.getKey().isDiscount() && (entry.getValue() > 5)) {
                sumToPay += entry.getKey().getPrice() * entry.getValue() * DISCOUNT_KF;
            } else {
                sumToPay += entry.getKey().getPrice() * entry.getValue();
            }
        }
        return sumToPay;
    }

    private Order buildOrder(double taxable, double sumToPay) {
        return Order.builder()
                .shopBasket(shopBasket)
                .discountCard(productInfo.getDiscountCard())
                .taxable(taxable)
                .sumToPay(sumToPay)
                .discount(taxable - sumToPay)
                .build();
    }
}
