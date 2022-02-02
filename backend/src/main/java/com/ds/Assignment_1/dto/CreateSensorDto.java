package com.ds.Assignment_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateSensorDto  {
    private String description;
    private Double maximum_value;
}
