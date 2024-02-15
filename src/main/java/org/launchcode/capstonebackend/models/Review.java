package org.launchcode.capstonebackend.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.launchcode.capstonebackend.controllers.AuthenticationController;
import org.launchcode.capstonebackend.models.data.MediaItemRepository;
import org.launchcode.capstonebackend.models.data.ReviewRepository;
import org.launchcode.capstonebackend.models.data.UserRepository;
import org.launchcode.capstonebackend.models.dto.MediaItemDTO;
import org.launchcode.capstonebackend.models.dto.ReviewDTO;
import org.launchcode.capstonebackend.models.dto.ReviewMediaItemCombinedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Entity
public class Review extends AbstractEntity {

    @NotNull
    @Size(min = 5, max = 75, message = "Title must be between 5 and 75 characters.")
    private String title;

    @NotNull
    @Size(min = 50, max = 1000, message = "Review must be between 50 and 1000 characters.")
    private String reviewBody;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "tmdb_Id")
    private MediaItem mediaItem;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Review(String title, String reviewBody, MediaItem mediaItem, User user) {
        this.title = title;
        this.reviewBody = reviewBody;
        this.mediaItem = mediaItem;
        this.user = user;
    }

    public Review() {}

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

    public MediaItem getMediaItem() {
        return mediaItem;
    }

    public void setMediaItem(MediaItem mediaItem) {
        this.mediaItem = mediaItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
