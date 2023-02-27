package by.markov.checkrunnerspringboot.cache.dao;

import by.markov.checkrunnerspringboot.cache.entities.Customer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple example of repository with using customer list.
 * Base CRUD operations.
 */
@Component
public class CustomerDao {
    static List<Customer> customers = new ArrayList<>();

    static {
        customers.add(new Customer(0L, "Ivan", 34, "scaer@Mail.ru", "34111356"));
        customers.add(new Customer(12L, "Semen", 22, "sfbg43w@Mail.ru", "54121456"));
        customers.add(new Customer(32L, "Mihail", 41, "ce31aa@Mail.ru", "23567333"));
    }

    public Customer findById(long id) {
        return customers.stream().filter(c -> c.getId() == id).findFirst().orElseThrow();
    }

    public List<Customer> findAll() {
        return customers;
    }

    public Customer addCustomer(Customer customer) {
        customers.add(customer);
        return customer;
    }

    public void removeById(long id) {
        customers.remove(id);
    }

    public Customer updateById(Customer customer) {
        Customer currentCustomer = findById(customer.getId());
        currentCustomer.setName(customer.getName());
        currentCustomer.setAge(customer.getAge());
        currentCustomer.setEmail(customer.getEmail());
        currentCustomer.setAddress(customer.getAddress());
        return customer;
    }
}
