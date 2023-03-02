package by.markov.checkrunnerspringboot.services.products;

import by.markov.checkrunnerspringboot.exceptions.ProductNotFoundException;
import by.markov.checkrunnerspringboot.repositories.ProductRepository;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static by.markov.checkrunnerspringboot.util.TestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("Get product price")
    void checkFindByIdShouldReturnRightPrice() {
        doReturn(Optional.of(MockUtil.getProducts().get(ONE))).when(productRepository).findById(ID_EXAMPLE_ONE);

        double actualPrice = productService.findById(ID_EXAMPLE_ONE).getPrice();

        assertEquals(LEMON_PRICE, actualPrice);
    }

    @Test
    @DisplayName("Find with exception")
    void checkFindByIdShouldThrowException() {
        doThrow(ProductNotFoundException.class).when(productRepository).findById(ID_EXAMPLE_TWO);

        assertThrows(ProductNotFoundException.class, () -> productService.findById(ID_EXAMPLE_TWO));
    }


    @Test
    @DisplayName("Find all products")
    void checkFindByIdShouldReturnSize() {
        doReturn(MockUtil.getProducts()).when(productRepository).findAll();

        int actual = productService.findAll().size();

        assertEquals(EXPECTED_SIZE, actual);
    }
}