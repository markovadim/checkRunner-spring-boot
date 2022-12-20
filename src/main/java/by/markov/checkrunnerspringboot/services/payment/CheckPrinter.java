package by.markov.checkrunnerspringboot.services.payment;

import by.markov.checkrunnerspringboot.entities.Check;

public interface CheckPrinter {
    String DEFAULT_SUPERMARKET_ADDRESS = "12, MILKYWAY Galaxy/Earth";
    String DEFAULT_SUPERMARKET_NAME = "SUPERMARKET 123";
    String DEFAULT_SUPERMARKET_PHONE = "Tel :123-456-7890";
    String TABLE_TOP_BORDER = "______________________________________";
    String TABLE_BOTTOM_BORDER = "======================================";
    int CASHIER_ID_BOTTOM_BORDER = 1000;
    int CASHIER_ID_TOP_BORDER = 8999;

    void printCheck(Check check);
}
