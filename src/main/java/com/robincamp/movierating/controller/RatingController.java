package com.robincamp.movierating.controller;

import com.robincamp.movierating.dto.RatingAggregateResponse;
import com.robincamp.movierating.dto.RatingResultResponse;
import com.robincamp.movierating.dto.RatingSubmitRequest;
import com.robincamp.movierating.service.RatingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies/{title}")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/ratings")
    public ResponseEntity<RatingResultResponse> submitRating(
            @PathVariable String title,
            @RequestHeader("X-Rater-Id") String raterId,
            @Valid @RequestBody RatingSubmitRequest request) {
        try {
            RatingResultResponse response = ratingService.submitRating(title, raterId, request);

            if (Boolean.TRUE.equals(response.getNew())) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .location(java.net.URI.create("/movies/" + title + "/ratings"))
                        .body(response);
            } else {
                return ResponseEntity.ok(response);
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Movie not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }

    @GetMapping("/rating")
    public ResponseEntity<RatingAggregateResponse> getRatingAggregate(
            @PathVariable String title) {
        try {
            RatingAggregateResponse response = ratingService.getRatingAggregate(title);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            if (e.getMessage().equals("Movie not found")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
}
