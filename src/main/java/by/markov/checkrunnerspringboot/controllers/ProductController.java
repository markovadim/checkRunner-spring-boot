package by.markov.checkrunnerspringboot.controllers;

import by.markov.checkrunnerspringboot.dto.ProductDto;
import by.markov.checkrunnerspringboot.mapping.ProductMapper;
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
    private final ProductMapper productMapper;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok().body(productMapper.getDtoList(productService.findAll()));
    }
}
