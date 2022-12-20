package by.markov.checkrunnerspringboot.exceptions;

public class DiscountCardNotFound extends RuntimeException {
    public DiscountCardNotFound() {
        super("Discount Card Not Found.");
    }
}
