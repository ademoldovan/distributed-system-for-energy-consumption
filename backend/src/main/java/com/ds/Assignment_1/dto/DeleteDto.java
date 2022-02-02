package com.ds.Assignment_1.dto;

public class DeleteDto {
    private Long id;

    public DeleteDto() {
    }
    public DeleteDto(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
