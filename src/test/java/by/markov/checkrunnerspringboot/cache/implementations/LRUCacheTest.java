package by.markov.checkrunnerspringboot.cache.implementations;

import by.markov.checkrunnerspringboot.cache.entities.Customer;
import by.markov.checkrunnerspringboot.util.CacheUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LRUCacheTest {

    @Test
    void checkPutShouldReturnSizeOf1() {
        LRUCache<Long, Customer> cache = new LRUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));
        cache.put(2L, new Customer(2L, "Kirill", 31, "kikiki@tut.by", "New-York"));
        assertEquals(5, cache.size());
    }

    @Test
    void checkPutShouldUpdateCustomerInCache() {
        LRUCache<Long, Customer> cache = new LRUCache<>();
        cache.put(CacheUtil.getCustomers().get(0).getId(), CacheUtil.getCustomers().get(0));
        cache.put(12L, new Customer(12L, "Ivan", 32, "kmksxnj@asd.ru", "Moscow"));
        assertEquals(1, cache.size);
    }

    @Test
    void checkGetShouldReturnIdOf1() {
        LRUCache<Long, Customer> cache = new LRUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));

        Customer customer = cache.get(21L);
        assertEquals("Semen", customer.getName());
    }

    @Test
    void checkClearShouldReturnEmptyList() {
        LRUCache<Long, Customer> cache = new LRUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));

        cache.clear();

        assertEquals(0, cache.size());
    }

    @Test
    void checkSizeShouldReturnSizeOf3() {
        LRUCache<Long, Customer> cache = new LRUCache<>(3);
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));

        assertEquals(3, cache.size());
    }
}