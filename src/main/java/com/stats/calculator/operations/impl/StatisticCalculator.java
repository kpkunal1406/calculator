package com.stats.calculator.operations.impl;

import com.stats.calculator.operations.IStatistic;
import com.stats.calculator.operations.executors.PrintExecutor;
import com.stats.calculator.operations.executors.StreamExecutor;
import com.stats.calculator.services.StatisticService;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class StatisticCalculator implements IStatistic {
    private final StatisticService statisticService;

    public StatisticCalculator(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    @Override
    public void event(int value) {
        statisticService.handleEvent(value);
    }

    @Override
    public float mean() {
        return statisticService.getMean();
    }

    @Override
    public float mean(int lastNMinutes) {
        return statisticService.getMean(lastNMinutes);
    }

    @Override
    public float variance() {
        return statisticService.getVariance();
    }

    @Override
    public int minimum() {
        return statisticService.getMinimum();
    }

    @Override
    public int maximum() {
        return statisticService.getMaximum();
    }

    public void start() throws InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        executor.execute(new PrintExecutor(this));
        Thread.sleep(2000);
        for (int i = 0; i < 4; i++) {
            executor.execute(new StreamExecutor(this));
            Thread.sleep(1000);
        }
        executor.shutdownNow();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
