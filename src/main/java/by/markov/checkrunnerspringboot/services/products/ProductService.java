package by.markov.checkrunnerspringboot.services.products;

import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.exceptions.ProductNotFoundException;
import by.markov.checkrunnerspringboot.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product findById(long id) {
        return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
