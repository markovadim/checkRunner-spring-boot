package by.markov.checkrunnerspringboot.services.commandline;

import by.markov.checkrunnerspringboot.services.orders.OrderManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CommandLineArgumentsParserTest {

    @Mock
    private OrderManager orderManager;

    List<Long> productIds = new ArrayList<>();
    List<Integer> productAmount = new ArrayList<>();
    String[] args = new String[]{"1-1", "2-2", "4-6", "card-1111"};
    String[] argsNotPattern = new String[]{"11", "2-2", "4-6w", "card1111"};
    CommandLineArgumentsParser commandLineArgumentsParser = new CommandLineArgumentsParser(orderManager, productIds, productAmount);


    @Test
    void checkInputFormat() {
        assertTrue(commandLineArgumentsParser.checkInputFormat(args));
        assertFalse(commandLineArgumentsParser.checkInputFormat(argsNotPattern));
    }

    @Test
    void parseCommandLineArgumentsAndDelegateCreateOrder() {
        assertEquals(1111, commandLineArgumentsParser.parseCommandLineArgumentsAndDelegateCreateOrder(args).getNumber());
    }
}