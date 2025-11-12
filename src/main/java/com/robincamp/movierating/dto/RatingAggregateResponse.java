package com.robincamp.movierating.dto;

import java.math.BigDecimal;

public class RatingAggregateResponse {

    private BigDecimal average;
    private Integer count;

    public RatingAggregateResponse() {
    }

    public BigDecimal getAverage() {
        return average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
