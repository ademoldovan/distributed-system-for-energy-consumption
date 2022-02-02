package com.ds.Assignment_1.dto;

public class BaselineDto {
    private int hour;
    private double average;

    public BaselineDto(int hour, double average) {
        this.hour = hour;
        this.average = average;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }
}
