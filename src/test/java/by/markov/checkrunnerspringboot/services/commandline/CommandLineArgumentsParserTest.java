package by.markov.checkrunnerspringboot.services.commandline;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.entities.ProductInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandLineArgumentsParserTest {

    public List<Long> productIds;
    public List<Integer> productAmount;

    public DiscountCard discountCard;
    public ProductInfo productInfo;
    public CommandLineArgumentsParser commandLineArgumentsParser;

    @BeforeEach
    void setUp() {
        productIds = new ArrayList<>();
        productAmount = new ArrayList<>();
        discountCard = new DiscountCard();
        productInfo = new ProductInfo(productIds, productAmount, discountCard);
        commandLineArgumentsParser = new CommandLineArgumentsParser(productInfo);
    }

    @AfterEach
    void cleanLists() {
        productIds.clear();
        productAmount.clear();
    }

    @Test
    @DisplayName("Adding IDs to the list")
    void checkParseDataShouldReturnListSizeOf3() {
        String[] args = new String[]{"1-1", "2-2", "4-6", "card-1111"};

        commandLineArgumentsParser.parseData(args);

        assertEquals(3, productInfo.getProductIds().size());
    }

    @Test
    @DisplayName("Parsing correct input data")
    void checkInputFormatShouldReturnTrue() {
        String[] inputData = new String[]{"1-1", "2-2", "4-6", "card-1951"};

        boolean actual = commandLineArgumentsParser.checkInputFormat(inputData);

        assertTrue(actual);
    }

    @Test
    @DisplayName("Parsing not correct input data")
    void checkInputFormatShouldReturnFalse() {
        String[] inputData = new String[]{"22", "3-1", "4-6w", "card-0611"};

        boolean actual = commandLineArgumentsParser.checkInputFormat(inputData);

        assertFalse(actual);
    }

    @Test
    @DisplayName("Parsing data. Not empty ids list")
    void checkParseCommandLineArgumentsShouldReturn3() {
        String[] inputData = new String[]{"2-1", "3-2", "4-6", "card-1651"};

        commandLineArgumentsParser.parseCommandLineArguments(inputData);

        assertEquals(3, productInfo.getProductIds().size());
    }

    @Test
    @DisplayName("Parsing data with Exception")
    void checkParseCommandLineArgumentsShouldReturnException() {
        String[] inputData = new String[]{"0-1", "2-2j", "4q-6", "card-1111"};

        assertThrows(NumberFormatException.class, () -> commandLineArgumentsParser.parseCommandLineArguments(inputData));
    }
}