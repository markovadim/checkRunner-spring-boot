package by.markov.checkrunnerspringboot.mapping;

import by.markov.checkrunnerspringboot.dto.ProductDto;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static by.markov.checkrunnerspringboot.util.TestData.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    @Test
    @DisplayName("Check dto mapper")
    void checkGetDtoListShouldReturnList() {
        List<ProductDto> productDtoList = MockUtil.getMapper().getDtoList(MockUtil.getProducts());

        assertAll(
                () -> assertEquals(EXPECTED_SIZE, productDtoList.size()),
                () -> assertEquals(MockUtil.getProducts().get(ZERO).getProductName(), productDtoList.get(ZERO).getProductName()),
                () -> assertEquals(EXPECTED_PRICE, productDtoList.get(ZERO).getPrice()),
                () -> assertThrows(IndexOutOfBoundsException.class, () -> productDtoList.get(NOT_CORRECT_ID))
        );
    }
}