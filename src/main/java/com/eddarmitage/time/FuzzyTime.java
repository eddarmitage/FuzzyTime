package com.eddarmitage.time;

import java.time.temporal.TemporalUnit;

public class FuzzyTime {
    private final long quantity;
    private final TemporalUnit unit;

    public FuzzyTime(long quantity, TemporalUnit unit) {
        this.quantity = quantity;
        this.unit = unit;
    }

    public long getQuantity() {
        return quantity;
    }

    public TemporalUnit getUnit() {
        return unit;
    }
}
