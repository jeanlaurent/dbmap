package net.morlhon.dbmap;

import net.morlhon.dbmap.serializer.JDKObjectDiskSerializer;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DBMap<K, V> implements SortedMap<K, V> {
    private SortedMap<K, V> map;
    private DiskSerializer serializer;

    public DBMap(DiskSerializer serializer, Comparator<K> keyComparator) {
        map = new TreeMap<>(keyComparator);
        this.serializer = serializer;
    }

    public DBMap(DiskSerializer serializer) {
        map = new TreeMap<>();
        this.serializer = serializer;
    }

    public DBMap() {
        map = new TreeMap<>();
        serializer = new JDKObjectDiskSerializer();
    }

    // io

    public void save(File location) throws IOException {
        serializer.save(map, location);
    }

    public void load(File location) throws IOException {
        map = serializer.load(location);
    }

    // finders

    public Optional<V> findOne(Predicate<? super V> findPredicate) {
        return map.values().stream().filter(findPredicate).findFirst();
    }

    public List<V> find(Predicate<? super V> findPredicate) {
        return map.values().stream().filter(findPredicate).collect(toList());
    }

    public void findAndUpdate(Predicate<? super V> findPredicate, Consumer<? super V> updateAction) {
        synchronized (map) {
            map.values().stream().filter(findPredicate).forEach(updateAction);
        }
    }

    public void delete(Predicate<? super V> findPredicate) {
        synchronized (map) {
            List<K> keysToRemove = map.entrySet().stream()
                    .filter((e) -> findPredicate.test(e.getValue()))
                    .map((e) -> e.getKey())
                    .collect(Collectors.toList());
            keysToRemove.forEach((k) -> map.remove(k));
        }
    }

    // delegated method

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return map.get(key);
    }

    @Override
    public V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return map.remove(key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> anotherMap) {
        map.putAll(anotherMap);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Comparator<? super K> comparator() {
        return map.comparator();
    }

    @Override
    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        return map.subMap(fromKey, toKey);
    }

    @Override
    public SortedMap<K, V> headMap(K toKey) {
        return map.headMap(toKey);
    }

    @Override
    public SortedMap<K, V> tailMap(K fromKey) {
        return map.tailMap(fromKey);
    }

    @Override
    public K firstKey() {
        return map.firstKey();
    }

    @Override
    public K lastKey() {
        return map.lastKey();
    }

    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        return map.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }
}
