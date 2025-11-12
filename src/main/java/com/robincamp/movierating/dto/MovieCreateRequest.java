package com.robincamp.movierating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class MovieCreateRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 500)
    private String title;

    @NotBlank(message = "Genre is required")
    @Size(max = 100)
    private String genre;

    @NotNull(message = "Release date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @Size(max = 200)
    private String distributor;

    private Long budget;

    @Size(max = 10)
    private String mpaRating;

    public MovieCreateRequest() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
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
}
