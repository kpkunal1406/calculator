package com.stats.calculator.operations.executors;

import com.stats.calculator.operations.IStatistic;

public class PrintExecutor implements Runnable {
    private final IStatistic statistic;

    public PrintExecutor(IStatistic statistic) {
        this.statistic = statistic;
    }

    @Override
    public void run() {
        for (int i = 0; i < 400; i++) {
            try {
                System.out.println(Thread.currentThread().getName() + " Mean : " + statistic.mean() + " Max : " + statistic.maximum()
                        + " Min : " + statistic.minimum() + " Variance : " + statistic.variance());
                if (i % 60 == 0) {
                    System.out.println("MinutesValue :  1 LastNMinutesMean : " + statistic.mean(1));
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("PrintExecutor Interrupted");
            }
        }
    }
}
