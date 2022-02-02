package com.ds.Assignment_1.dto;

import java.sql.Timestamp;
import java.util.Date;

public class ReadConsumptionsDto {
    private Long idSensor;
    private Long id;
    private Double energy_consumption;
    private Date timestamp;


    public ReadConsumptionsDto(Long idSensor, Long id, Double energy_consumption, Date timestamp) {
        this.idSensor = idSensor;
        this.id = id;
        this.energy_consumption = energy_consumption;
        this.timestamp = timestamp;
    }

    public Long getIdSensor() {
        return idSensor;
    }

    public void setIdSensor(Long idSensor) {
        this.idSensor = idSensor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getEnergy_consumption() {
        return energy_consumption;
    }

    public void setEnergy_consumption(Double energy_consumption) {
        this.energy_consumption = energy_consumption;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
