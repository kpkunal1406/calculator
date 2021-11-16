package com.stats.calculator.operations.executors;

import com.stats.calculator.operations.IStatistic;

public class StreamExecutor implements Runnable {
    private IStatistic statistic;

    public StreamExecutor(IStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public void run() {
        for (int i = 0; i < 400; i++) {
            try {
                statistic.event(i);
                System.out.println(Thread.currentThread().getName() + " [StreamExecutor] pushed : " + i);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("StreamExecutor Interrupted");
            }
        }

    }
}
