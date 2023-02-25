package by.markov.checkrunnerspringboot.cache.services;

import by.markov.checkrunnerspringboot.aop.SimpleCache;
import by.markov.checkrunnerspringboot.cache.dao.CustomerDao;
import by.markov.checkrunnerspringboot.cache.entities.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Base service for work with 'repository'.
 * @SimpleCache annotation is used for work aspects.
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @SimpleCache
    public Customer findById(long id) {
        return customerDao.findById(id);
    }

    public void remove(long id) {
        customerDao.removeById(id);
    }

    @SimpleCache
    public Customer update(Customer customer) {
        return customerDao.updateById(customer);
    }

    @SimpleCache
    public void add(Customer customer) {
        if (checkInputEmail(customer.getEmail())) {
            customerDao.addCustomer(customer);
        } else throw new RuntimeException("Incorrect email.");
    }

    public boolean checkInputEmail(String email) {
        Pattern pattern = Pattern.compile("\\b\\w+@\\w+[.]\\w{1,4}");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
