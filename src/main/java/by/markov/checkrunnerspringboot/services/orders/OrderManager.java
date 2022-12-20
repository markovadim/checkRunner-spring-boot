package by.markov.checkrunnerspringboot.services.orders;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.services.discountcards.DiscountCardService;
import by.markov.checkrunnerspringboot.services.payment.CheckManager;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class OrderManager {

    private final static double DISCOUNT_KF = 0.9;

    private final ProductService productService;
    private final Map<Product, Integer> shopBasket;
    private final CheckManager checkManager;
    private final DiscountCardService discountCardService;

    public Check createOrderAndDelegateCreateCheck(List<Long> productIds, List<Integer> productAmount, DiscountCard discountCard) {
        createShopBasket(productIds, productAmount);
        double taxable = getOrderSumWithoutDiscount(shopBasket);
        double sumToPay = taxable;
        if (checkDiscountCard(discountCard)) {
            sumToPay = getOrderSumToPay(shopBasket);
        }
        return checkManager.createAndPrintCheck(buildOrder(shopBasket, discountCard, taxable, sumToPay));
    }

    public void createShopBasket(List<Long> productIds, List<Integer> productAmount) {
        Product product;
        for (int i = 0; i < productIds.size(); i++) {
            product = productService.findById(productIds.get(i));
            shopBasket.put(product, productAmount.get(i));
        }
    }

    public double getOrderSumWithoutDiscount(Map<Product, Integer> shopBasket) {
        AtomicReference<Double> sumWithoutDiscount = new AtomicReference<>(0.0);
        shopBasket.keySet()
                .forEach(p -> sumWithoutDiscount.updateAndGet(v -> (v + p.getPrice() * shopBasket.get(p))));
        return sumWithoutDiscount.get();
    }

    private boolean checkDiscountCard(DiscountCard discountCard) {
        return discountCardService.findByNumber(discountCard.getNumber()) != null;
    }

    public double getOrderSumToPay(Map<Product, Integer> shopBasket) {
        AtomicReference<Double> sumToPay = new AtomicReference<>(0.0);
        shopBasket
                .keySet().forEach(p -> {
                    if (p.isDiscount() && (shopBasket.get(p) > 5)) {
                        sumToPay.updateAndGet(v -> (v + p.getPrice() * shopBasket.get(p) * DISCOUNT_KF));
                    } else {
                        sumToPay.updateAndGet(v -> (v + p.getPrice() * shopBasket.get(p)));
                    }
                });
        return sumToPay.get();
    }

    private Order buildOrder(Map<Product, Integer> shopBasket, DiscountCard discountCard, double taxable, double sumToPay) {
        return Order.builder()
                .shopBasket(shopBasket)
                .discountCard(discountCard)
                .taxable(taxable)
                .sumToPay(sumToPay)
                .discount(taxable - sumToPay)
                .build();
    }
}
