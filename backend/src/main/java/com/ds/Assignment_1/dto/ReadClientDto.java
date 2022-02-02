package com.ds.Assignment_1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
public class ReadClientDto {
    private Long id;
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String address;
    private String email;

    public ReadClientDto(Long id, String lastName, String firstName, Date birthDate, String address, String email) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.address = address;
        this.email = email;
    }
}
