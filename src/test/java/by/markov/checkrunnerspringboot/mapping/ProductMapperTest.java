package by.markov.checkrunnerspringboot.mapping;

import by.markov.checkrunnerspringboot.dto.ProductDto;
import by.markov.checkrunnerspringboot.entities.Product;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    public static ModelMapper modelMapper;
    public static ProductMapper productMapper;
    public static List<Product> product;

    @BeforeAll
    static void setUp() {
        modelMapper = new ModelMapper();
        productMapper = new ProductMapper(modelMapper);
        product = List.of(new Product(1, "fish", 34.12, false));
    }

    @Test
    @DisplayName("Check dto mapper")
    void checkGetDtoListShouldReturnList() {
        List<ProductDto> productDtoList = productMapper.getDtoList(product);

        assertAll(
                () -> assertEquals(1, productDtoList.size()),
                () -> assertEquals(product.get(0).getProductName(), productDtoList.get(0).getProductName()),
                () -> assertEquals(34.12, productDtoList.get(0).getPrice()),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> productDtoList.get(1))
        );
    }
}