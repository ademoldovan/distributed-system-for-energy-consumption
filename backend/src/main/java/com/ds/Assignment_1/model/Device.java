package com.ds.Assignment_1.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String description;
    private Double maximum_energy;
    private Double average;

    @JsonIgnore
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToOne(optional = true)
    private Sensor sensor;

    public Device(){

    }

    public Device(String address, String description, Double maximum_energy, Double average, Sensor sensor, Client client) {
        this.address = address;
        this.description = description;
        this.maximum_energy = maximum_energy;
        this.average = average;
        this.sensor = sensor;
        this.client = client;
    }

    public Device(Long id, String address, String description, Double maximum_energy, Double average, Sensor sensor, Client client) {
        this.id = id;
        this.address = address;
        this.description = description;
        this.maximum_energy = maximum_energy;
        this.average = average;
        this.sensor = sensor;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaximum_energy() {
        return maximum_energy;
    }

    public void setMaximum_energy(Double maximum_energy) {
        this.maximum_energy = maximum_energy;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
