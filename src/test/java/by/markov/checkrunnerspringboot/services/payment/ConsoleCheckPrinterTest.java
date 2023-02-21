package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ConsoleCheckPrinterTest {
    public static final ByteArrayOutputStream OUTPUT_STREAM = MockUtil.getByteArrayOutputStream();

    @Mock
    public Order order;
    @InjectMocks
    public Check check;


    @BeforeAll
    static void setUpStreams() {
        System.setOut(new PrintStream(OUTPUT_STREAM));
    }

    @ParameterizedTest
    @ValueSource(strings = {"SUPERMARKET 123", "CASHIER#", "DESCRIPTION", "TAXABLE TOT.: $"})
    @DisplayName("Common check printer")
    void printCheck(String line) {
        check = new Check(order, LocalDateTime.now());
        new ConsoleCheckPrinter().printCheck(check);
        assertAll(
                () -> assertFalse(OUTPUT_STREAM.toString().isEmpty()),
                () -> assertTrue(OUTPUT_STREAM.toString().contains(line))
        );
    }

    @AfterAll
    static void cleanUpStreams() {
        System.setOut(null);
    }
}