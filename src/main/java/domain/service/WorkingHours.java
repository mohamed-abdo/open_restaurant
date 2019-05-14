package domain.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class WorkingHours {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkingHours.class);
    private final String timeFormat = "h[:m] a";//with optional minutes

    public Map<DayOfWeek, Set<Pair<LocalTime, LocalTime>>> calcWorkingHours(@NonNull DayOfWeek dayOfWeek, @NonNull String timeInStr) {
        Objects.requireNonNull(timeInStr);
        String[] timeSplit = timeInStr.toUpperCase().split("-");//in case am | pm will be in CAPS
        if (timeSplit.length == 1)
            throw new IllegalArgumentException(String.format("invalid working hours format. %s", timeInStr));

        var fromTime = LocalTime.parse(timeSplit[0].trim(), DateTimeFormatter.ofPattern(timeFormat));
        var toTime = LocalTime.parse(timeSplit[1].trim(), DateTimeFormatter.ofPattern(timeFormat)).minusNanos(1L);
        var dayShifts = new HashMap<DayOfWeek, Set<Pair<LocalTime, LocalTime>>>();
        // in case of shift is overnight (cross day)
        if (toTime.compareTo(fromTime) < 0) {
            var nextDayOfWeekIdx = dayOfWeek.getValue() == 7 ? 1 : dayOfWeek.getValue() + 1;
            dayShifts.put(dayOfWeek,
                    Set.of(Pair.of(fromTime, LocalTime.MAX)));
            dayShifts.put(DayOfWeek.of(nextDayOfWeekIdx), Set.of(Pair.of(LocalTime.MIDNIGHT, toTime)));
        } else {
            dayShifts.put(dayOfWeek, Set.of(Pair.of(fromTime, toTime)));
        }
        return dayShifts;
    }

}
