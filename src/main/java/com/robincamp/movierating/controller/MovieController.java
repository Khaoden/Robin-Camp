package com.robincamp.movierating.controller;

import com.robincamp.movierating.dto.*;
import com.robincamp.movierating.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(
            @Valid @RequestBody MovieCreateRequest request) {
        MovieResponse response = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .location(URI.create("/movies/" + response.getId()))
                .body(response);
    }

    @GetMapping
    public ResponseEntity<MoviePageResponse> searchMovies(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String distributor,
            @RequestParam(required = false) Long budget,
            @RequestParam(required = false) String mpaRating,
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) String cursor) {
        MoviePageResponse response = movieService.searchMovies(
                q, year, genre, distributor, budget, mpaRating, limit, cursor);
        return ResponseEntity.ok(response);
    }
}
