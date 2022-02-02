package com.ds.Assignment_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class UpdateClientDto {
    private Long id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String address;
    private String email;
}
