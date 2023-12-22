package services;

public class CustomPair<K, V> {
    private K key;
    private V value;

    private CustomPair(){}

    public CustomPair(K k, V v) {
        this.key = k;
        this.value = v;
    }
    public static <K, V> CustomPair of (K key, V value) {
        return new CustomPair<>(key, value);
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return value;
    }
}