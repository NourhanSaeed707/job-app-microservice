package com.example.reviewms.review.service;
import com.example.reviewms.review.model.Review;
import java.util.List;

public interface ReviewService {
    List<Review> getAll(Long companyId);
    boolean create(Long companyId, Review review);
    Review getReview(Long reviewId);
    boolean update( Long reviewId, Review updatedReview);

    boolean delete( Long reviewId);
}
