package by.markov.checkrunnerspringboot.util;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import by.markov.checkrunnerspringboot.mapping.ProductMapper;
import org.modelmapper.ModelMapper;

import java.io.ByteArrayOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MockUtil {

    private MockUtil() {
    }

    public static List<Product> getProducts() {
        return List.of(
                new Product(0L, "Meet", 31.12, false),
                new Product(1L, "Lemon", 1.17, false),
                new Product(2L, "Milk", 2.42, false),
                new Product(3L, "Sugar", 3.21, true),
                new Product(4L, "Lime", 5.29, false),
                new Product(5L, "Fish", 14.45, true)
        );
    }

    public static List<DiscountCard> getCards() {
        return List.of(
                new DiscountCard(0, 1111),
                new DiscountCard(1, 2222),
                new DiscountCard(2, 3333)
        );
    }

    public static ProductMapper getMapper() {
        return new ProductMapper(new ModelMapper());
    }

    public static ProductInfo getProductInfo() {
        return new ProductInfo(List.of(1L, 2L, 3L), List.of(1, 2, 3), new DiscountCard(0L, 1234));
    }

    public static Map<Product, Integer> getShopBasket() {
        return Map.of(getProducts().get(0), 1,
                getProducts().get(2), 2,
                getProducts().get(3), 5,
                getProducts().get(5), 6
        );
    }

    public static Map<Product, Integer> getEmptyShopBasket() {
        return new LinkedHashMap<>();
    }

    public static ByteArrayOutputStream getByteArrayOutputStream() {
        return new ByteArrayOutputStream();
    }

    public static Path getTestFilePath() {
        return Paths.get(System.getProperty("user.dir"), "testReceipt.txt");
    }
}
