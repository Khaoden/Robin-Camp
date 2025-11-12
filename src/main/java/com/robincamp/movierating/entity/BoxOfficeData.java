package com.robincamp.movierating.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Embeddable
public class BoxOfficeData {

    @Embedded
    private Revenue revenue;

    @Column(name = "box_office_currency", length = 10)
    private String currency;

    @Column(name = "box_office_source", length = 200)
    private String source;

    @Column(name = "box_office_last_updated")
    private Instant lastUpdated;

    public BoxOfficeData() {
    }

    public Revenue getRevenue() {
        return revenue;
    }

    public void setRevenue(Revenue revenue) {
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
