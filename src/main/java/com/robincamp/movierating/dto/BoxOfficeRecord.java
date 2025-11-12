package com.robincamp.movierating.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class BoxOfficeRecord {

    private String title;
    private String distributor;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private Long budget;
    private RevenueData revenue;

    @JsonProperty("mpaRating")
    private String mpaRating;

    public BoxOfficeRecord() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Long getBudget() {
        return budget;
    }

    public void setBudget(Long budget) {
        this.budget = budget;
    }

    public RevenueData getRevenue() {
        return revenue;
    }

    public void setRevenue(RevenueData revenue) {
        this.revenue = revenue;
    }

    public String getMpaRating() {
        return mpaRating;
    }

    public void setMpaRating(String mpaRating) {
        this.mpaRating = mpaRating;
    }

    public static class RevenueData {
        private Long worldwide;

        @JsonProperty("openingWeekendUSA")
        private Long openingWeekendUSA;

        public RevenueData() {
        }

        public Long getWorldwide() {
            return worldwide;
        }

        public void setWorldwide(Long worldwide) {
            this.worldwide = worldwide;
        }

        public Long getOpeningWeekendUSA() {
            return openingWeekendUSA;
        }

        public void setOpeningWeekendUSA(Long openingWeekendUSA) {
            this.openingWeekendUSA = openingWeekendUSA;
        }
    }
}
