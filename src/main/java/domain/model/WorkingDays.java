package domain.model;

import org.springframework.lang.NonNull;

import java.time.DayOfWeek;
import java.time.temporal.TemporalField;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WorkingDays {
    private static Set<DayOfWeek> fullDays = new HashSet<>();

    static {
        fullDays.addAll(List.of(
                DayOfWeek.SATURDAY, DayOfWeek.SUNDAY, DayOfWeek.MONDAY,
                DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY));
    }

    public DayOfWeek parseDayString(@NonNull String dayOfWeek) {
        switch (dayOfWeek.toLowerCase()) {
            case "sat":
                return DayOfWeek.SATURDAY;
            case "sun":
                return DayOfWeek.SUNDAY;
            case "mon":
                return DayOfWeek.MONDAY;
            case "tue":
                return DayOfWeek.TUESDAY;
            case "wed":
                return DayOfWeek.WEDNESDAY;
            case "thu":
                return DayOfWeek.THURSDAY;
            case "fri":
                return DayOfWeek.FRIDAY;
            default:
                throw new IllegalArgumentException(String.format("invalid input: %s", dayOfWeek));
        }

    }

    public Set<DayOfWeek> calcWorkingDays(@NonNull String dayFrom, @NonNull String dayTo) {
        //ex Mon-Fri
        int fromDay=parseDayString(dayFrom).getValue();
        int toDay=parseDayString(dayTo).getValue();
        return fullDays;
    }
}
