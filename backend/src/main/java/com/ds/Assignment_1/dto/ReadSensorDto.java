package com.ds.Assignment_1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadSensorDto {
    private Long id;
    private String description;
    private Double maximum_value;

    public ReadSensorDto(Long id, String description, Double maximum_value) {
        this.id = id;
        this.description = description;
        this.maximum_value = maximum_value;
    }
}
