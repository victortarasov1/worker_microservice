package executor.service.collection.map;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultThreadSafeMapHandler<K, V > implements MapHandler<K, V>{
    private final ConcurrentMap<K, Set<V>> map = new ConcurrentHashMap<>();

    @Override
    public void put(K key, V value) {
        map.computeIfAbsent(key, k -> new HashSet<>()).add(value);
    }

    @Override
    public Set<V> remove(K key) {
        return map.remove(key);
    }
}
