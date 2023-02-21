package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Order;
import by.markov.checkrunnerspringboot.util.MockUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FileCheckPrinterTest {

    @Mock
    public Order order;
    @InjectMocks
    public Check check;
    public FileCheckPrinter fileCheckPrinter;
    public Path testFile;


    @BeforeEach
    void setUp() throws IOException {
        fileCheckPrinter = new FileCheckPrinter();
        check = new Check(order, LocalDateTime.now());
        testFile = Files.createFile(MockUtil.getTestFilePath());
    }

    @Test
    @DisplayName("Print check")
    void checkPrintCheckShouldReturnTrue() {
        fileCheckPrinter.printCheck(check);
        File file = new File("check.txt");
        assertTrue(file.exists());
    }

    @Test
    @DisplayName("Create file")
    void checkCreateFileShouldCreateTestFile() throws IOException {
        assertTrue(fileCheckPrinter.createFile().exists());
    }

    @Test
    @DisplayName("File size bigger than 0")
    void checkWriteInFileShouldReturnSizeOfFileWhichBiggerThanZero() throws IOException {
        when(order.getShopBasket()).thenReturn(MockUtil.getShopBasket());

        fileCheckPrinter.writeCheckInFile(check, testFile.toFile());

        assertTrue(testFile.toFile().length() > 0);
    }

    @AfterEach
    void removeTestFile() throws IOException {
        Files.deleteIfExists(testFile);
    }
}