package com.stats.calculator.services;

import com.stats.calculator.models.LRUCache;
import com.stats.calculator.operations.IEventHandler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Service
@EnableScheduling
public class StatisticService implements IEventHandler {

    private List<Integer> intList = new CopyOnWriteArrayList<>();
    private AtomicInteger minimum = new AtomicInteger(0);
    private AtomicInteger maximum = new AtomicInteger(0);
    private AtomicReference<Float> variance = new AtomicReference<>(Float.valueOf(0));
    private AtomicReference<Float> mean = new AtomicReference<>(Float.valueOf(0));
    private AtomicInteger currentMinuteIndex = new AtomicInteger(0);
    private AtomicInteger currentListCounter = new AtomicInteger(0);
    private LRUCache lruCache = new LRUCache(60);

    @Override
    public void handleEvent(int value) {
        intList.add(value);

        if (value > getMaximum()) {
            maximum.set(value);
        }
        if (value < getMinimum()) {
            minimum.set(value);
        }
        mean.set((float) intList.stream().mapToInt(n -> n.intValue()).average().getAsDouble());
        variance.set(variance(intList));
    }

    public int getMinimum() {
        return minimum.get();
    }

    public int getMaximum() {
        return maximum.get();
    }

    public float getVariance() {
        return variance.get();
    }

    public float getMean() {
        return mean.get();
    }

    public float getMean(int lastNMinutes) {
        if (lastNMinutes == 0) {
            return getMean();
        }
        
        List<Integer> subIntList = new ArrayList<>();
        for (int i = lruCache.size() - 1; i >= lruCache.size() - lastNMinutes; i--) {
            List<Integer> tempList = lruCache.get(i);
            if (!tempList.isEmpty()) {
                subIntList.addAll(tempList);
            }
        }
        OptionalDouble result = subIntList.stream().mapToInt(n -> n.intValue()).average();
        return (float) (result.isPresent() ? result.getAsDouble() : 0);
    }

    private float variance(List<Integer> list) {
        double variance = 0.0;
        for (int i : list) {
            variance = variance + Math.pow((i - getMean()), 2);
        }
        double var = variance / (list.size());
        return (float) var;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void storeToCache() {
        int listCounter = intList.size() > 1 ? intList.size() - 1 : 0;
        List<Integer> l = new ArrayList<>();
        l.addAll(intList.subList(currentListCounter.get(), listCounter));
        lruCache.put(currentMinuteIndex.getAndIncrement(), l);
        currentListCounter.set(listCounter);
    }

}
