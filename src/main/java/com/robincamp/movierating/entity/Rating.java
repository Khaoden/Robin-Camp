package com.robincamp.movierating.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ratings", uniqueConstraints = @UniqueConstraint(columnNames = { "movie_id", "rater_id" }), indexes = {
        @Index(name = "idx_rating_movie", columnList = "movie_id"),
        @Index(name = "idx_rating_rater", columnList = "rater_id")
})
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(name = "rater_id", nullable = false, length = 200)
    private String raterId;

    @Column(nullable = false, precision = 2, scale = 1)
    private BigDecimal rating;

    public Rating() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public String getRaterId() {
        return raterId;
    }

    public void setRaterId(String raterId) {
        this.raterId = raterId;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
