package com.robincamp.movierating.service;

import com.robincamp.movierating.dto.RatingAggregateResponse;
import com.robincamp.movierating.dto.RatingResultResponse;
import com.robincamp.movierating.dto.RatingSubmitRequest;
import com.robincamp.movierating.entity.Movie;
import com.robincamp.movierating.entity.Rating;
import com.robincamp.movierating.repository.MovieRepository;
import com.robincamp.movierating.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MovieRepository movieRepository;

    public RatingService(RatingRepository ratingRepository, MovieRepository movieRepository) {
        this.ratingRepository = ratingRepository;
        this.movieRepository = movieRepository;
    }

    @Transactional
    public RatingResultResponse submitRating(String movieTitle, String raterId, RatingSubmitRequest request) {
        Movie movie = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        BigDecimal ratingValue = request.getRating();
        if (ratingValue.scale() > 1) {
            ratingValue = ratingValue.setScale(1, RoundingMode.HALF_UP);
        }

        if (!isValidRating(ratingValue)) {
            throw new IllegalArgumentException("Invalid rating value");
        }

        boolean isNew = ratingRepository.findByMovieIdAndRaterId(movie.getId(), raterId).isEmpty();

        Rating rating = ratingRepository.findByMovieIdAndRaterId(movie.getId(), raterId)
                .orElse(new Rating());

        rating.setMovie(movie);
        rating.setRaterId(raterId);
        rating.setRating(ratingValue);

        rating = ratingRepository.save(rating);

        RatingResultResponse response = new RatingResultResponse();
        response.setMovieTitle(movieTitle);
        response.setRaterId(raterId);
        response.setRating(rating.getRating());
        response.setNew(isNew);

        return response;
    }

    @Transactional(readOnly = true)
    public RatingAggregateResponse getRatingAggregate(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found"));

        Long count = ratingRepository.countByMovieId(movie.getId());
        Double average = ratingRepository.averageByMovieId(movie.getId());

        RatingAggregateResponse response = new RatingAggregateResponse();
        response.setCount(count != null ? count.intValue() : 0);

        if (average != null && count > 0) {
            BigDecimal avg = BigDecimal.valueOf(average)
                    .setScale(1, RoundingMode.HALF_UP);
            response.setAverage(avg);
        } else {
            response.setAverage(BigDecimal.ZERO.setScale(1));
        }

        return response;
    }

    private boolean isValidRating(BigDecimal rating) {
        if (rating == null) {
            return false;
        }

        double value = rating.doubleValue();
        if (value < 0.5 || value > 5.0) {
            return false;
        }

        double step = 0.5;
        double remainder = (value - 0.5) % step;
        return Math.abs(remainder) < 0.01 || Math.abs(remainder - step) < 0.01;
    }

}
