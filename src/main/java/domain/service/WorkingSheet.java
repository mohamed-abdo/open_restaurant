package domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class WorkingSheet {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkingSheet.class);
    private static final String DAYS_PATTERN = "(^\\D{3}-\\D{3}\\s*(,\\s*\\D{3})?)|(^\\s*\\D{3}(-\\s*\\D{3})?\\s*(,\\s*\\D{3})?)";

    @Autowired
    private WorkingDays workingDays;

    @Autowired
    private WorkingHours workingHours;


    private Map<DayOfWeek, Duration> innerParseWorkingSheet(@NonNull String workingSheetStr) {
        //ex:"Mon-Mon, Sun 11:30 am - 10 pm ";
        LOGGER.info("parsing: {}", workingSheetStr);
        Objects.requireNonNull(workingSheetStr);
        final var workingSheetTerm = workingSheetStr.trim();
        var daysSheet = Pattern.compile(DAYS_PATTERN)
                .matcher(workingSheetTerm).results()
                .map(MatchResult::group)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("invalid input format. %s", workingSheetTerm)));
        String hoursSheet = workingSheetTerm.replace(daysSheet, "");
        var duration = workingHours.calcWorkingHours(hoursSheet);
        return workingDays.parseWorkingDaysSheet(daysSheet).stream().collect(Collectors.toMap(dy -> dy, du -> duration));
    }

    public Map<DayOfWeek, Duration> parseWorkingSheet(@NonNull String workingSheetStr) {
        //ex:"Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm";
        LOGGER.info("parsing: {}", workingSheetStr);
        Objects.requireNonNull(workingSheetStr);
        return Arrays.stream(workingSheetStr.split("/"))
                .map(this::innerParseWorkingSheet)
                .reduce(new HashMap<>(), (a, b) -> {
                    a.putAll(b);
                    return a;
                });
    }
}
