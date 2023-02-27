package by.markov.checkrunnerspringboot.cache.implementations;

import by.markov.checkrunnerspringboot.cache.Cache;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation cache by date. Map has nested classes of Node, which contains entity <V>
 *
 * @param <K> - key of hashmap (use id field of entity)
 * @param <V> - value in hashmap (entity for caching)
 */
public class LRUCache<K, V> implements Cache<K, V> {
    private static final int DEFAULT_CAPACITY = 5;

    Map<K, Node> cache;
    Node head;
    Node tail;
    int capacity;
    int size;

    public LRUCache() {
        this(DEFAULT_CAPACITY);
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>(capacity);

        head = new Node();
        tail = new Node();

        head.next = tail;
        tail.prev = head;
    }

    private class Node {
        V value;
        Node prev;
        Node next;
        K key;
        LocalDateTime localDateTime;
    }

    @Override
    public V get(K key) {
        Node node = cache.get(key);
        node.localDateTime = LocalDateTime.now();
        changeNodeValueInMap(key, node, node.value);
        return node.value;
    }

    @Override
    public void put(K key, V value) {
        Node node = cache.get(key);
        if (cache.get(key) != null) {
            changeNodeValueInMap(key, node, value);
        } else {
            putNewNodeInMap(key, value);
        }
    }

    @Override
    public void clear() {
        cache.clear();
        head = new Node();
        tail = new Node();

        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public int size(){
        return size;
    }

    private void changeNodeValueInMap(K key, Node node, V value) {
        node.value = value;
        node.key = key;
        node.localDateTime = LocalDateTime.now();
        removeFromChain(node);
        cache.put(key, node);
        addToHead(node);
    }

    private void putNewNodeInMap(K key, V value) {
        Node node = new Node();
        node.value = value;
        node.key = key;
        node.localDateTime = LocalDateTime.now();
        cache.put(key, node);
        addToHead(node);
//        size++;
        if (size >= capacity) {
            removeFromChain(tail.prev);
            cache.remove(getKeyLastNode());
        }else size++;
    }

    private K getKeyLastNode() {
        return cache.entrySet()
                .stream()
                .sorted(Comparator.comparing(a -> a.getValue().localDateTime))
                .map(Map.Entry::getKey).findFirst()
                .orElseThrow();
    }

    private void addToHead(Node node) {
        Node currentFirstNode = head.next;

        node.prev = head;
        head.next = node;

        node.next = currentFirstNode;
        currentFirstNode.prev = node;
    }

    private void removeFromChain(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }
}
