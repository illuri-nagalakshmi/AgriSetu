package com.agrisetu.controller;

import com.agrisetu.dto.ApiResponse;
import com.agrisetu.dto.ReviewCreateRequest;
import com.agrisetu.dto.ReviewDto;
import com.agrisetu.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReviewDto>> createReview(@Valid @RequestBody ReviewCreateRequest request) {
        ReviewDto review = reviewService.createReview(request);
        return ResponseEntity.ok(ApiResponse.success("Review submitted successfully", review));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<ReviewDto>>> getReviewsForUser(@PathVariable Long userId) {
        List<ReviewDto> list = reviewService.getReviewsForUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User reviews retrieved", list));
    }
}
