package com.stats.calculator;

import com.stats.calculator.operations.impl.StatisticCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CalculatorApplication implements ApplicationRunner {

    @Autowired
    private StatisticCalculator statisticCalculator;

    public static void main(String[] args) {
        SpringApplication.run(CalculatorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Stats Calculator Starting ....");
        statisticCalculator.start();
    }
}
