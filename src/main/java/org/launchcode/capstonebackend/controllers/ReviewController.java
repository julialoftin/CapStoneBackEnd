package org.launchcode.capstonebackend.controllers;

import org.launchcode.capstonebackend.models.Review;
import org.launchcode.capstonebackend.models.data.ReviewRepository;
import org.launchcode.capstonebackend.models.dto.MediaItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    // Awaiting Authentication Controller to be able to verify user's session to associate review with user
//    @PostMapping("/add-review")
//    public ResponseEntity<Review> addReviewToMediaItem(@RequestBody MediaItemDTO mediaItemDTO,
//                                                       ) {
//
//    }


}
