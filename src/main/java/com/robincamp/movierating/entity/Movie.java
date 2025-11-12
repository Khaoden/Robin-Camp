package com.robincamp.movierating.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies", indexes = {
        @Index(name = "idx_movie_title", columnList = "title"),
        @Index(name = "idx_movie_release_date", columnList = "release_date"),
        @Index(name = "idx_movie_genre", columnList = "genre")
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 500)
    private String title;

    @Column(name = "release_date", nullable = false)
    private LocalDate releaseDate;

    @Column(nullable = false, length = 100)
    private String genre;

    @Column(length = 200)
    private String distributor;

    @Column(name = "budget")
    private Long budget;

    @Column(name = "mpa_rating", length = 10)
    private String mpaRating;

    @Embedded
    private BoxOfficeData boxOffice;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings = new ArrayList<>();

    public Movie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public BoxOfficeData getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOfficeData boxOffice) {
        this.boxOffice = boxOffice;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
}
