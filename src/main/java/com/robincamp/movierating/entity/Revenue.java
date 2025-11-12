package com.robincamp.movierating.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Revenue {

    @Column(name = "revenue_worldwide")
    private Long worldwide;

    @Column(name = "revenue_opening_weekend_usa")
    private Long openingWeekendUSA;

    public Revenue() {
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
