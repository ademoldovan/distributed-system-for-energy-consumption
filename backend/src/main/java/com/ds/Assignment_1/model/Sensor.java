package com.ds.Assignment_1.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private Double maximum_value;

    @OneToOne(mappedBy = "sensor")
    private Device device;

    @OneToMany(mappedBy = "sensor",cascade = CascadeType.ALL)
    private List<Consumption> consumptions;

    public Sensor(){}

    public Sensor(String description, Double maximum_value) {
        this.description = description;
        this.maximum_value = maximum_value;
    }

    public Sensor(Long id, String description, Double maximum_value) {
        this.id = id;
        this.description = description;
        this.maximum_value = maximum_value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMaximum_value() {
        return maximum_value;
    }

    public void setMaximum_value(Double maximum_value) {
        this.maximum_value = maximum_value;
    }
}
