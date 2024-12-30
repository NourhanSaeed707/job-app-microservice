package com.example.reviewms.review.controller;
import com.example.reviewms.review.messaging.ReviewMessageProducer;
import com.example.reviewms.review.model.Review;
import com.example.reviewms.review.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private ReviewService reviewService;
    private ReviewMessageProducer reviewMessageProducer;

    public ReviewController( ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAll(@RequestParam Long companyId) {
        return new ResponseEntity<>(reviewService.getAll(companyId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestParam Long companyId, @RequestBody Review review) {
       boolean isReviewSaved = reviewService.create(companyId, review);
       if(isReviewSaved) {
           reviewMessageProducer.sendMessage(review);
           return new ResponseEntity<>("Review created successfully", HttpStatus.CREATED);
       }
        return new ResponseEntity<>("Review not saved", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReview(@PathVariable Long reviewId) {
        return new ResponseEntity<>(reviewService.getReview(reviewId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> update( @PathVariable Long reviewId, @RequestBody Review updatedReview) {
        boolean isReviewUpdated = reviewService.update(reviewId, updatedReview);
        if(isReviewUpdated) {
            return new ResponseEntity<>("Review updated successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review not updated", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> delete(@PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.delete( reviewId);
        if(isReviewDeleted) {
            return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>("Review not deleted", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageReview(@RequestParam Long companyId) {
        List<Review> reviewList = reviewService.getAll(companyId);
        return reviewList.stream().mapToDouble(Review::getRating).average().orElse(0.0);
    }
}
