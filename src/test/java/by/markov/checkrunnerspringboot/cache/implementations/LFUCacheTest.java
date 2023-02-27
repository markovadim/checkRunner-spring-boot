package by.markov.checkrunnerspringboot.cache.implementations;

import by.markov.checkrunnerspringboot.cache.entities.Customer;
import by.markov.checkrunnerspringboot.util.CacheUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LFUCacheTest {

    @Test
    void checkGetShouldReturnIdOf13() {
        LFUCache<Long, Customer> cache = new LFUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));

        assertEquals(13L, CacheUtil.getCustomers().get(5).getId());
    }

    @Test
    void checkPutShouldReturnNotNullObject() {
        LFUCache<Long, Customer> cache = new LFUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));
        cache.put(33L, new Customer(33L, "Anton", 35, "niornd@mail.ru", "Vitebsk"));

        Customer customer = cache.get(33L);

        assertNotNull(customer);
    }

    @Test
    void checkPutShouldUpdateCustomerInCache() {
        LFUCache<Long, Customer> cache = new LFUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));
        cache.put(13L, new Customer(13L, "Sergey Sergeevich Sergeev", 35, "sergio2332@mail.ru", "Vitebsk"));

        Customer customer = cache.get(13L);

        assertNotEquals(CacheUtil.getCustomers().get(5).getName(), customer.getName());
    }

    @Test
    void checkClearShouldReturnEmptyCache() {
        LFUCache<Long, Customer> cache = new LFUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));

        cache.clear();

        assertEquals(0, cache.size());
    }

    @Test
    void checkSizeShouldReturnSizeByDefaultCapacity() {
        LFUCache<Long, Customer> cache = new LFUCache<>();
        CacheUtil.getCustomers().forEach(c -> cache.put(c.getId(), c));

        assertEquals(5, cache.size());
    }
}