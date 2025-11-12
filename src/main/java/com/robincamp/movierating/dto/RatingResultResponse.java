package com.robincamp.movierating.dto;

import java.math.BigDecimal;

public class RatingResultResponse {

    private String movieTitle;
    private String raterId;
    private BigDecimal rating;
    private Boolean isNew;

    public RatingResultResponse() {
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
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

    public Boolean getNew() {
        return isNew;
    }

    public void setNew(Boolean isNew) {
        this.isNew = isNew;
    }
}
