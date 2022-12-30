package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.entities.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class FileCheckPrinterTest {

    @Mock
    private Order order;
    private FileCheckPrinter fileCheckPrinter;
    private Path testFile;
    private Check check;
    private Map<Product, Integer> shopBasket;

    @BeforeEach
    void setUp() throws IOException {
        shopBasket = Map.of(new Product(0L, "Meet", 13.0, false), 1,
                new Product(1L, "Milk", 2.0, true), 12,
                new Product(2L, "Sugar", 1.5, false), 1,
                new Product(3L, "Lemon", 3.0, true), 7);
        testFile = Files.createFile(Paths.get(System.getProperty("user.dir"), "testCheck.txt"));
        fileCheckPrinter = new FileCheckPrinter();
        check = new Check(order, LocalDateTime.now());
    }

    @Test
    void createFile() throws IOException {
        assertTrue(fileCheckPrinter.createFile().exists());
    }

    @Test
    void writeInFile() throws IOException {
        Mockito.when(order.getShopBasket()).thenReturn(shopBasket);

        fileCheckPrinter.writeCheckInFile(check, testFile.toFile());

        assertTrue(testFile.toFile().exists());
    }

    @AfterEach
    void removeTestFile() throws IOException {
        Files.deleteIfExists(testFile);
    }
}