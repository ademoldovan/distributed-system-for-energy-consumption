package model;

public class Message {
    private String timestamp;
    private Long sensorId;
    private Double measurementValue;

    public Message(String timestamp, Long sensorId, Double measurementValue) {
        this.timestamp = timestamp;
        this.sensorId = sensorId;
        this.measurementValue = measurementValue;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
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
