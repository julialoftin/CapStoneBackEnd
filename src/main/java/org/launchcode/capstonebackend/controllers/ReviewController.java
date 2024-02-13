package org.launchcode.capstonebackend.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.capstonebackend.models.MediaItem;
import org.launchcode.capstonebackend.models.Review;
import org.launchcode.capstonebackend.models.User;
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

@RestController
@CrossOrigin
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MediaItemRepository mediaItemRepository;

    @Autowired
    AuthenticationController authenticationController;

    private MediaItem convertMediaItemDTOToEntity(MediaItemDTO mediaItemDTO) {
        MediaItem mediaItem = new MediaItem(mediaItemDTO.getTmdbId(), mediaItemDTO.getMediaType());
        return mediaItem;
    }

    private Review convertReviewDTOToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setTitle(reviewDTO.getTitle());
        review.setReviewBody(reviewDTO.getReviewBody());
        return review;
    }

    // test in postman, first create user account
    @PostMapping("/add")
    public ResponseEntity<Review> addReviewToMediaItem(@RequestBody @Valid ReviewMediaItemCombinedDTO reviewMediaItemCombinedDTO,
                                                       Errors errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            User user = authenticationController.getUserFromSession(session);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Review review = convertReviewDTOToEntity(reviewMediaItemCombinedDTO.getReviewDTO());
            MediaItem mediaItem = convertMediaItemDTOToEntity(reviewMediaItemCombinedDTO.getMediaItemDTO());

            review.setUser(user);
            review.setMediaItem(mediaItem);

            reviewRepository.save(review);
            mediaItem.addReviewToList(review);
            mediaItemRepository.save(mediaItem);

            user.addReview(review);
            userRepository.save(user);

            return ResponseEntity.ok().body(review);
        } catch (Exception exception) {
            System.out.println("Error saving review: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("get-reviews-by-media-item")
    public ResponseEntity<List<Review>> getAllReviewsByMediaItem(@RequestBody @Valid MediaItemDTO mediaItemDTO,
                                                                 Errors errors) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            MediaItem mediaItem = convertMediaItemDTOToEntity(mediaItemDTO);
            return ResponseEntity.ok().body(mediaItem.getReviews());
        } catch (Exception exception) {
            System.out.println("Error retrieving reviews: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint to list all Reviews a logged-in User has made
    @GetMapping("get-reviews-by-user")
    public ResponseEntity<List<Review>> getAllReviewsByUser(HttpSession session) {
        User user = authenticationController.getUserFromSession(session);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(user.getReviews());

    }

    // Endpoint for a user to delete a review they have made
    @DeleteMapping("delete/{reviewId}")
    public ResponseEntity<Review> deleteReview(@PathVariable int reviewId,
                                               Errors errors, HttpSession session) {
        try {
            if (errors.hasErrors()) {
                return ResponseEntity.badRequest().build();
            }

            User user = authenticationController.getUserFromSession(session);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            Optional<Review> optionalReview = reviewRepository.findById(reviewId);
            if (optionalReview.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Review review = optionalReview.get();
            if (!review.getUser().equals(user)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            reviewRepository.deleteById(review.getId());

            user.deleteReview(review);
            userRepository.save(user);

            return ResponseEntity.ok().build();
        } catch (Exception exception) {
            System.out.println("Error deleting review: " + exception.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


}
