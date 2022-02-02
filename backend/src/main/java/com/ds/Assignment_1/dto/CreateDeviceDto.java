package com.ds.Assignment_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CreateDeviceDto {
    private String address;
    private String description;
    private Double maximum_energy;
    private Double average;
    private Long sensor;
    private Long client;

}
