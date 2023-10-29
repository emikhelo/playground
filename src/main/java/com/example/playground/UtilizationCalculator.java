package com.example.playground;

import com.example.playground.equipment.Equipment;

import java.util.List;

public interface UtilizationCalculator {
    double calcUtilization(List<Equipment> equipmentList);
}
