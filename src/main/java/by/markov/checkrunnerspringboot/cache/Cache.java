package by.markov.checkrunnerspringboot.cache;

/**
 * Parametrized cache interface.
 * @param <K> - Class key of map (usually it is id param for fast search entity in cache O(1))
 * @param <V> - Class entity in cache
 */
public interface Cache<K, V> {
    V get(K key);

    void put(K key, V value);

    void clear();

    int size();
}
