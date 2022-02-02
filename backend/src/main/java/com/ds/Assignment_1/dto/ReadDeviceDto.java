package com.ds.Assignment_1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ReadDeviceDto {
    private Long id;
    private String address;
    private String description;
    private Double maximum_energy;
    private Double average;
    private Long sensor_id;
    private Long client_id;

    public ReadDeviceDto(Long id, String address, String description, Double maximum_energy, Double average, Long sensor_id, Long client_id) {
        this.id = id;
        this.address = address;
        this.description = description;
        this.maximum_energy = maximum_energy;
        this.average = average;
        this.sensor_id = sensor_id;
        this.client_id = client_id;
    }
}
