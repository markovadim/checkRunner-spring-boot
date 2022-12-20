package by.markov.checkrunnerspringboot.services.products;

import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.exceptions.ProductNotFoundException;
import by.markov.checkrunnerspringboot.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductService productService;
    List<Product> products = List.of(
            new Product(0L, "Meet", 13.0, false),
            new Product(1L, "Sugar", 3.0, false),
            new Product(2L, "Lemon", 4.0, true),
            new Product(3L, "Milk", 2.5, false)
    );

    @Test
    void findById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(products.get(1)));
        assertEquals("Sugar", productService.findById(1).getProductName());
        assertThrows(ProductNotFoundException.class, () -> productService.findById(0));
    }

    @Test
    void findAll() {
        when(productRepository.findAll()).thenReturn(products);
        assertEquals(4, productService.findAll().size());
        assertEquals(13.0, productService.findAll().get(0).getPrice());
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> productService.findAll().get(4));
    }
}