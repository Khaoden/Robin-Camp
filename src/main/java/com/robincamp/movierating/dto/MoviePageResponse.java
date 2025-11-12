package com.robincamp.movierating.dto;

import java.util.List;

public class MoviePageResponse {

    private List<MovieResponse> items;
    private String nextCursor;

    public MoviePageResponse() {
    }

    public List<MovieResponse> getItems() {
        return items;
    }

    public void setItems(List<MovieResponse> items) {
        this.items = items;
    }

    public String getNextCursor() {
        return nextCursor;
    }

    public void setNextCursor(String nextCursor) {
        this.nextCursor = nextCursor;
    }
}
