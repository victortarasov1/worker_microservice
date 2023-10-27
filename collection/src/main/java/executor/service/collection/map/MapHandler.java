package executor.service.collection.map;

import java.util.Set;

public interface MapHandler <K, V> {
    void put(K key, V value);
    Set<V> remove(K key);
}
