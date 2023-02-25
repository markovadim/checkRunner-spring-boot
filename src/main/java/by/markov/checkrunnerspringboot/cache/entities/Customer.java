package by.markov.checkrunnerspringboot.cache.entities;

import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * Base entity for work with custom cache.
 * Email check on validation by Pattern/Matcher classes.
 */
@Data
@AllArgsConstructor
public class Customer {

    private long id;
    private String name;
    private int age;
    private String email;
    private String address;
}
