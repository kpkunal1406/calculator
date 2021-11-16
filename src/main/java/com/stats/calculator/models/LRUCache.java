package com.stats.calculator.models;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class LRUCache extends LinkedHashMap<Integer, List<Integer>> {
    private int maxSize;

    public LRUCache(Integer capacity) {
        super(capacity, 0.75f, true);
        this.maxSize = capacity;
    }

    public List<Integer> get(int key) {
        List<Integer> result = super.get(key);
        return result == null ? new ArrayList<>() : result;
    }

    public void put(int key, List<Integer> value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, List<Integer>> eldest) {
        return this.size() > maxSize; //must override it if used in a fixed cache
    }
}
