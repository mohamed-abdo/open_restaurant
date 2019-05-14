package domain.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Component
public class WorkingSheet {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkingSheet.class);
    private static final String DAYS_PATTERN = "(^\\D{3}\\s*,\\s*\\D{3}-\\s*\\D{3})|(^\\D{3}-\\D{3}\\s*(,\\s*\\D{3})?)|(^\\D{3}(-\\s*\\D{3})?\\s*(,\\s*\\D{3})?)";

    @Autowired
    private WorkingDays workingDays;

    @Autowired
    private WorkingHours workingHours;


    private Map<DayOfWeek, Set<Pair<LocalTime, LocalTime>>> innerParseWorkingSheet(@NonNull String workingSheetStr) {
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
        var rawData = new HashMap<DayOfWeek, Set<Pair<LocalTime, LocalTime>>>();
        workingDays.parseWorkingDaysSheet(daysSheet)
                .stream()
                .map(d -> workingHours.calcWorkingHours(d, hoursSheet))
                .forEach(m -> {
                    m.forEach((k, v) -> {
                        if (rawData.containsKey(k)) {
                            rawData.put(k, new HashSet<>() {{
                                addAll(rawData.get(k));
                                addAll(v);
                            }});
                        } else {
                            rawData.put(k, v);
                        }
                    });
                });
        return rawData;
    }

    public Map<DayOfWeek, Set<Pair<LocalTime, LocalTime>>> parseWorkingSheet(@NonNull String workingSheetStr) {
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
