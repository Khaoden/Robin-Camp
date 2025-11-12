package com.robincamp.movierating.repository;

import com.robincamp.movierating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<Rating> findByMovieIdAndRaterId(Long movieId, String raterId);

    @Query("SELECT COUNT(r) FROM Rating r WHERE r.movie.id = :movieId")
    Long countByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movie.id = :movieId")
    Double averageByMovieId(@Param("movieId") Long movieId);
}
