package org.launchcode.capstonebackend.models.dto;

import jakarta.validation.constraints.NotBlank;

public class TagDTO {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}