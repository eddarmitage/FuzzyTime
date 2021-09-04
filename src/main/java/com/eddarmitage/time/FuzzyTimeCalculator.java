package com.eddarmitage.time;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;

public class FuzzyTimeCalculator {

    private static final ArrayList<ChronoUnit> TIME_UNITS_DESCENDING_ORDER = new ArrayList<>();

    static {
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.MILLENNIA);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.CENTURIES);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.DECADES);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.YEARS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.MONTHS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.WEEKS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.DAYS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.HOURS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.MINUTES);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.SECONDS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.MILLIS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.MICROS);
        TIME_UNITS_DESCENDING_ORDER.add(ChronoUnit.NANOS);
    }
    public static FuzzyTime differenceBetween(Instant from, Instant to) {
        return fuzzify(Duration.between(from, to));
    }

    public static FuzzyTime calculateUntil(Instant to) {
        return differenceBetween(Instant.now(), to);
    }

    public static FuzzyTime fuzzify(Duration duration) {
        for (ChronoUnit unit: TIME_UNITS_DESCENDING_ORDER) {
            if (shouldUseUnit(unit, duration)) {
                return convert(duration, unit);
            }
        }

        return convert(duration, ChronoUnit.NANOS);
    }

    private static boolean shouldUseUnit(ChronoUnit unit, Duration duration) {
        int currentUnitIndex = TIME_UNITS_DESCENDING_ORDER.indexOf(unit);

        if (currentUnitIndex == TIME_UNITS_DESCENDING_ORDER.size() - 1) {
            return true;
        }

        ChronoUnit nextUnit = TIME_UNITS_DESCENDING_ORDER.get(currentUnitIndex + 1);
        Duration thresholdDuration = nextUnit.getDuration().multipliedBy(3).dividedBy(2);

        return duration.compareTo(thresholdDuration) > 0;
    }

    private static FuzzyTime convert(Duration duration, TemporalUnit unit) {
        return new FuzzyTime(duration.truncatedTo(unit).get(unit), unit);
    }
}
