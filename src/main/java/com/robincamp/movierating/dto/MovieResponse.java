package com.robincamp.movierating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;
import java.time.LocalDate;

public class MovieResponse {

    private String id;
    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private String genre;
    private String distributor;
    private Long budget;
    private String mpaRating;
    private BoxOfficeResponse boxOffice;

    public MovieResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public BoxOfficeResponse getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(BoxOfficeResponse boxOffice) {
        this.boxOffice = boxOffice;
    }
}
