package com.ds.Assignment_1.dto;

public class Notification {
    private Long sensorId;
    private String username;

    public Notification(Long sensorId, String username) {
        this.sensorId = sensorId;
        this.username = username;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
