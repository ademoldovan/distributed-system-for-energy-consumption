package com.ds.Assignment_1.dto;

import java.util.Date;

public class Message {
    private Date timestamp;
    private Long sensorId;
    private Double measurementValue;

    public Message(Date timestamp, Long sensorId, Double measurementValue) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.measurementValue = measurementValue;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Double getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(Double measurementValue) {
        this.measurementValue = measurementValue;
    }
}
