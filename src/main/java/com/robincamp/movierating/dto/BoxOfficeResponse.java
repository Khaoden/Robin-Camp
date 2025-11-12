package com.robincamp.movierating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public class BoxOfficeResponse {

    private RevenueResponse revenue;
    private String currency;
    private String source;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant lastUpdated;

    public BoxOfficeResponse() {
    }

    public RevenueResponse getRevenue() {
        return revenue;
    }

    public void setRevenue(RevenueResponse revenue) {
        this.revenue = revenue;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Instant getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Instant lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
