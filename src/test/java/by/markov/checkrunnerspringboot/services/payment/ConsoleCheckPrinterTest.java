package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConsoleCheckPrinterTest {

    @Mock
    private Order order;
    private Check check;
    private ConsoleCheckPrinter consoleCheckPrinter;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    void setUpStreams() {
        check = new Check(order, LocalDateTime.now());
        consoleCheckPrinter = new ConsoleCheckPrinter();
        System.setOut(new PrintStream(output));
    }

    @Test
    @DisplayName("Common check printer")
    void printCheck() {
        consoleCheckPrinter.printCheck(check);
        assertAll("consoleCheckPrinter.printCheck(check)",
                () -> assertFalse(output.toString().isEmpty()),
                () -> assertTrue(output.toString().contains("SUPERMARKET 123")),
                () -> assertTrue(output.toString().contains("CASHIER#")),
                () -> assertTrue(output.toString().contains("DESCRIPTION")),
                () -> assertTrue(output.toString().contains("PRICE")),
                () -> assertTrue(output.toString().contains("TAXABLE TOT.: $")));
    }

    @AfterEach
    void cleanUpStreams() {
        System.setOut(null);
    }
}