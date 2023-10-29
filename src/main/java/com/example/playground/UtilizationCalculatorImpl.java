package com.example.playground;

import com.example.playground.equipment.Equipment;

import java.util.List;

public class UtilizationCalculatorImpl implements UtilizationCalculator {
    private static UtilizationCalculatorImpl instance;

    private UtilizationCalculatorImpl() {};

    public static UtilizationCalculatorImpl getInstance() {
        if (instance == null) {
            instance = new UtilizationCalculatorImpl();
        }
        return instance;
    }

    public double calcUtilization(List<Equipment> equipmentList) {
        double average = equipmentList.stream()
                .mapToDouble(Equipment::calcUtilization)
                .average()
                .orElse(0.0);
        return average;
    }
}
