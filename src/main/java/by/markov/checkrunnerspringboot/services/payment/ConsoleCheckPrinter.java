package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;
import by.markov.checkrunnerspringboot.entities.Product;
import org.springframework.stereotype.Service;

@Service
public class ConsoleCheckPrinter implements CheckPrinter {

    @Override
    public void printCheck(Check check) {
        printMarketData();
        printCashierInfoAndDate(check);
        printOrder(check);
        printTotalSumAndSumToPay(check);
    }

    public void printMarketData() {
        System.out.printf("%25s%n%29s%n%25s%n", CheckPrinter.DEFAULT_SUPERMARKET_NAME,
                CheckPrinter.DEFAULT_SUPERMARKET_ADDRESS,
                CheckPrinter.DEFAULT_SUPERMARKET_PHONE);
    }

    public void printCashierInfoAndDate(Check check) {
        System.out.printf("%-25sDATE:%1td/%tm/%ty%n%35tH:%tM\n", "CASHIER#" + (CheckPrinter.CASHIER_ID_BOTTOM_BORDER + Math.random() * CheckPrinter.CASHIER_ID_TOP_BORDER),
                check.getDateTime(),
                check.getDateTime(),
                check.getDateTime(),
                check.getDateTime(),
                check.getDateTime());
    }

    public void printOrder(Check check) {
        System.out.printf(CheckPrinter.TABLE_TOP_BORDER + "%n%-7s%10s%10s%10s%n%n", "QTY", "DESCRIPTION", "PRICE", "TOTAL");
        int currentProductAmount;
        for (Product product : check.getOrder().getShopBasket().keySet()) {
            System.out.printf("%-10d%5s%13.1f%10.1f%n", currentProductAmount = check.getOrder().getShopBasket().get(product),
                    product.getProductName(),
                    product.getPrice(),
                    currentProductAmount * product.getPrice());
        }
        System.out.println(CheckPrinter.TABLE_BOTTOM_BORDER);
    }

    public void printTotalSumAndSumToPay(Check check) {
        System.out.printf("%-25s%13.1f%n", "TAXABLE TOT.: $", check.getOrder().getTaxable());
        System.out.printf("%-25s%13.1f%n", "VAT10%: $", check.getOrder().getDiscount());
        System.out.printf("%-25s%13.1f%n", "TOTAL: $", check.getOrder().getSumToPay());
    }
}
