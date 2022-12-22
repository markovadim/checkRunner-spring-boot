package by.markov.checkrunnerspringboot.configuration;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StartConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public DiscountCard discountCard() {
        return new DiscountCard();
    }
}
