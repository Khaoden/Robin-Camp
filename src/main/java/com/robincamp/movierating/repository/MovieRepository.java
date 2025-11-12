package com.robincamp.movierating.repository;

import com.robincamp.movierating.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByTitle(String title);

    @Query("SELECT m FROM Movie m WHERE " +
            "(:q IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :q, '%'))) AND " +
            "(:year IS NULL OR EXTRACT(YEAR FROM m.releaseDate) = :year) AND " +
            "(:genre IS NULL OR LOWER(m.genre) = LOWER(:genre)) AND " +
            "(:distributor IS NULL OR LOWER(m.distributor) = LOWER(:distributor)) AND " +
            "(:budget IS NULL OR m.budget <= :budget) AND " +
            "(:mpaRating IS NULL OR m.mpaRating = :mpaRating) " +
            "ORDER BY m.id ASC")
    Page<Movie> searchMovies(
            @Param("q") String q,
            @Param("year") Integer year,
            @Param("genre") String genre,
            @Param("distributor") String distributor,
            @Param("budget") Long budget,
            @Param("mpaRating") String mpaRating,
            Pageable pageable);
}
