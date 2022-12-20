package by.markov.checkrunnerspringboot.controllers;

import by.markov.checkrunnerspringboot.entities.Product;
import by.markov.checkrunnerspringboot.services.products.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok().body(productService.findAll());
    }
}
