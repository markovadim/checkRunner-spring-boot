package by.markov.checkrunnerspringboot.util;

import by.markov.checkrunnerspringboot.cache.entities.Customer;

import java.util.List;

public class CacheUtil {

    public static List<Customer> getCustomers() {
        return List.of(
                new Customer(12L, "Ivan", 32, "saxw@asd.ru", "Minsk"),
                new Customer(1L, "Igor", 31, "wqewq2113@wxa.ru", "Moscow"),
                new Customer(31L, "Max", 26, "yrtte32bc@asd.by", "Brest"),
                new Customer(41L, "Igor jr.", 24, "sabec2323@mail.ru", "Gomel"),
                new Customer(21L, "Semen", 29, "semasema2323@mail.ru", "Bruklin"),
                new Customer(13L, "Sergey", 32, "sergio2323@mail.ru", "LA")
        );
    }
}
