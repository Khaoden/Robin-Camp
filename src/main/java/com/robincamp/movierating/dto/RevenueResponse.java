package com.robincamp.movierating.dto;

public class RevenueResponse {

    private Long worldwide;
    private Long openingWeekendUSA;

    public RevenueResponse() {
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
