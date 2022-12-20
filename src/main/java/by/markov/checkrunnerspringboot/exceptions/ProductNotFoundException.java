package by.markov.checkrunnerspringboot.exceptions;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException() {
        super("Product Not Found.");
    }
}
