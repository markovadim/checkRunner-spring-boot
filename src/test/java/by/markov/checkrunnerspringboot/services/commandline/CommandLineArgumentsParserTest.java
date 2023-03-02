package by.markov.checkrunnerspringboot.services.commandline;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static by.markov.checkrunnerspringboot.util.TestData.EXPECTED_IDS_LIST_SIZE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandLineArgumentsParserTest {

    private List<Long> productIds;
    private List<Integer> productAmount;

    private DiscountCard discountCard;
    private ProductInfo productInfo;
    private CommandLineArgumentsParser commandLineArgumentsParser;

    @BeforeEach
    void setUp() {
        productIds = new ArrayList<>();
        productAmount = new ArrayList<>();
        discountCard = new DiscountCard();
        productInfo = new ProductInfo(productIds, productAmount, discountCard);
        commandLineArgumentsParser = new CommandLineArgumentsParser(productInfo);
    }

    @Test
    @DisplayName("Parsing correct input data")
    void checkInputFormatShouldReturnTrue() {
        String[] inputData = new String[]{"1-1", "2-2", "4-6", "card-1951"};

        boolean actual = commandLineArgumentsParser.checkInputFormat(inputData);

        assertThat(actual).isTrue();
    }

    @Test
    @DisplayName("Parsing not correct input data")
    void checkInputFormatShouldReturnFalse() {
        String[] inputData = new String[]{"22", "3-1", "4-6w", "card-0611"};

        boolean actual = commandLineArgumentsParser.checkInputFormat(inputData);

        assertThat(actual).isFalse();
    }

    @Test
    @DisplayName("Parsing data. Not empty ids list")
    void checkParseCommandLineArgumentsShouldReturn3() {
        String[] inputData = new String[]{"2-1", "3-2", "4-6", "card-1651"};

        commandLineArgumentsParser.parseCommandLineArguments(inputData);
        int actualSize = productInfo.getProductIds().size();

        assertThat(actualSize).isEqualTo(EXPECTED_IDS_LIST_SIZE);
    }

    @Test
    @DisplayName("Parsing data with Exception")
    void checkParseCommandLineArgumentsShouldReturnException() {
        String[] inputData = new String[]{"0-1", "2-2j", "4q-6", "card-1111"};

        assertThrows(NumberFormatException.class, () -> commandLineArgumentsParser.parseCommandLineArguments(inputData));
    }
}