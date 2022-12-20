package by.markov.checkrunnerspringboot.mapping;


import by.markov.checkrunnerspringboot.dto.ProductDto;
import by.markov.checkrunnerspringboot.entities.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ModelMapper modelMapper;

    public List<ProductDto> getDtoList(List<Product> products) {
        return products.stream().map(p -> modelMapper.map(p, ProductDto.class)).collect(Collectors.toList());
    }
}
