package org.launchcode.capstonebackend.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class WatchList extends AbstractEntity {

    @NotBlank
    private String name;
    @NotNull
    private String description;

    public WatchList(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public WatchList() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
