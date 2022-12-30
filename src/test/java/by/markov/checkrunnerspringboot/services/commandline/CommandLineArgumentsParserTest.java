package by.markov.checkrunnerspringboot.services.commandline;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandLineArgumentsParserTest {

    List<Long> productIds = new ArrayList<>();
    List<Integer> productAmount = new ArrayList<>();
    String[] args = new String[]{"1-1", "2-2", "4-6", "card-1111"};
    String[] argsNotPattern = new String[]{"11", "2-2", "4-6w", "card1111"};

    DiscountCard discountCard = new DiscountCard();
    ProductInfo productInfo = new ProductInfo(productIds, productAmount, discountCard);
    CommandLineArgumentsParser commandLineArgumentsParser = new CommandLineArgumentsParser(productInfo);

    @Test
    void parseNotCorrectData() {
        commandLineArgumentsParser.parseData(argsNotPattern);

        assertEquals(0, productInfo.getProductIds().size());
    }

    @Test
    void parseCorrectData() {
        commandLineArgumentsParser.parseData(args);

        assertEquals(2, productInfo.getProductIds().get(1));
    }
}