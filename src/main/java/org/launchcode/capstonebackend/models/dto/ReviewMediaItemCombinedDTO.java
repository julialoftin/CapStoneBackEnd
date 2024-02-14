package org.launchcode.capstonebackend.models.dto;

import jakarta.validation.constraints.NotNull;
import org.launchcode.capstonebackend.models.MediaItem;

public class ReviewMediaItemCombinedDTO {

    @NotNull
    private ReviewDTO reviewDTO;

    @NotNull
    private MediaItemDTO mediaItemDTO;

    public ReviewDTO getReviewDTO() {
        return reviewDTO;
    }

    public MediaItemDTO getMediaItemDTO() {
        return mediaItemDTO;
    }

}
