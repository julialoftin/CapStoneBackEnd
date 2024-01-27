package org.launchcode.capstonebackend.models;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Review extends AbstractEntity {

    @NotNull
    @Size(min = 5, max = 75, message = "Title must be between 5 and 75 characters.")
    private String title;

    @NotNull
    @Size(min = 50, max = 1000, message = "Review must be between 50 and 1000 characters.")
    private String reviewBody;

    public Review(String title, String reviewBody) {
        this.title = title;
        this.reviewBody = reviewBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

}
