package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Product;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Service
@Primary
public class FileCheckPrinter implements CheckPrinter {

    @SneakyThrows
    @Override
    public void printCheck(Check check) {
        File file = createFile();
        writeCheckInFile(check, file);
    }

    public File createFile() throws IOException {
        File file = new File(System.getProperty("user.dir"), "check.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public void writeCheckInFile(Check check, File file) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, false)) {
            fileWriter.append(String.format("%25s%n%29s%n%25s%n", CheckPrinter.DEFAULT_SUPERMARKET_NAME,
                    CheckPrinter.DEFAULT_SUPERMARKET_ADDRESS,
                    CheckPrinter.DEFAULT_SUPERMARKET_PHONE));
            fileWriter.append(String.format("%-25sDATE:%1td/%tm/%ty%n%35tH:%tM\n", "CASHIER#" + (CheckPrinter.CASHIER_ID_BOTTOM_BORDER + (int) (Math.random() * CheckPrinter.CASHIER_ID_TOP_BORDER)),
                    check.getDateTime(),
                    check.getDateTime(),
                    check.getDateTime(),
                    check.getDateTime(),
                    check.getDateTime()));
            fileWriter.append(String.format(CheckPrinter.TABLE_TOP_BORDER + "%n%-7s%10s%10s%10s%n%n", "QTY", "DESCRIPTION", "PRICE", "TOTAL"));
            int currentProductAmount;
            for (Product product : check.getOrder().getShopBasket().keySet()) {
                fileWriter.append(String.format("%-10d%5s%13.1f%10.1f%n", currentProductAmount = check.getOrder().getShopBasket().get(product),
                        product.getProductName(),
                        product.getPrice(),
                        currentProductAmount * product.getPrice()));
            }
            fileWriter.append(CheckPrinter.TABLE_BOTTOM_BORDER);
            fileWriter.append(String.format("\n%-25s%13.1f%n", "TAXABLE TOT.: $", check.getOrder().getTaxable()));
            fileWriter.append(String.format("%-25s%13.1f%n", "VAT10%: $", check.getOrder().getDiscount()));
            fileWriter.append(String.format("%-25s%13.1f%n", "TOTAL: $", check.getOrder().getSumToPay()));
        }
    }
}
