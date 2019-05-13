package domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class WorkingSheet {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkingSheet.class);
    private static final String DAYS_PATTERN = "^\\s*\\D{3}(-\\s*\\D{3})?\\s*(,\\s*\\D{3})?";

    @Autowired
    private WorkingDays workingDays;

    @Autowired
    private WorkingHours workingHours;


    public Map<DayOfWeek, Duration> parseWorkingSheet(@NonNull String workingSheetStr) {
        Objects.requireNonNull(workingSheetStr);
        final var workingSheetTerm = workingSheetStr.trim();
        //ex:"Mon-Mon, Sun 11:30 am - 10 pm ";
        var daysSheet = Pattern.compile(DAYS_PATTERN)
                .matcher(workingSheetTerm).results()
                .map(MatchResult::group)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("invalid input format. %s", workingSheetTerm)));
        String hoursSheet = workingSheetTerm.replace(daysSheet, "");
        var duration = workingHours.calcWorkingHours(hoursSheet);
        return workingDays.parseWorkingDaysSheet(daysSheet).stream().collect(Collectors.toMap(dy -> dy, du -> duration));
    }
}
