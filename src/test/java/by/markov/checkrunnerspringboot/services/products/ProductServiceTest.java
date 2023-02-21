package by.markov.checkrunnerspringboot.services.products;

import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.exceptions.ProductNotFoundException;
import by.markov.checkrunnerspringboot.repositories.ProductRepository;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    public ProductRepository productRepository;
    @InjectMocks
    public ProductService productService;

    @Test
    @DisplayName("Get product price")
    void checkFindByIdShouldReturnRightPrice() {
        when(productRepository.findById(1L)).thenReturn(Optional.ofNullable(MockUtil.getProducts().get(1)));

        assertEquals(1.17, productService.findById(1L).getPrice());
    }

    @Test
    @DisplayName("Find with exception")
    void checkFindByIdShouldThrowException() {
        when(productRepository.findById(2L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class, () -> productService.findById(2L));
    }


    @Test
    @DisplayName("Find all products")
    void checkFindByIdShouldReturnSize() {
        when(productRepository.findAll()).thenReturn(MockUtil.getProducts());

        int actual = productService.findAll().size();

        assertEquals(6, actual);
    }
}