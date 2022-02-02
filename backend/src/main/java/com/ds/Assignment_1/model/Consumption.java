package com.ds.Assignment_1.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Consumption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date timestamp;
    private Double energyConsumption;

    @ManyToOne(fetch = FetchType.LAZY)
    private Sensor sensor;

    public Consumption(Date timestamp, double measurementValue, Sensor sensor) {
        this.timestamp = timestamp;
        this.energyConsumption = measurementValue;
        this.sensor = sensor;
    }
}
