package domain.service;

import domain.service.WorkingDays;
import domain.service.WorkingHours;
import domain.service.WorkingSheet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {WorkingSheet.class, WorkingDays.class, WorkingHours.class})
@ExtendWith(SpringExtension.class)
class WorkingSheetTest {

    @Autowired
    private WorkingSheet workingSheet;

    @Test
    void parseWorkingSheet() {
        String workingSheetStr = "Mon-Mon, Sun 11:30 am - 10 pm ";
        var workingSheetMap = new HashMap<DayOfWeek, Map.Entry<LocalTime,LocalTime>>();
        workingSheetMap.put(DayOfWeek.MONDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 30), LocalTime.of(22, 0).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.SUNDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 30), LocalTime.of(22, 0).minusNanos(1L)));
        assertEquals(workingSheetMap, workingSheet.parseWorkingSheet(workingSheetStr));
    }

    @Test
    void parseWorkingSheet_Full() {
        String workingSheetStr = "Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm";
        var workingSheetMap = new HashMap<DayOfWeek, Map.Entry<LocalTime,LocalTime>>();
        workingSheetMap.put(DayOfWeek.MONDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 0), LocalTime.of(22, 30).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.TUESDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 0), LocalTime.of(22, 30).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.WEDNESDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 0), LocalTime.of(22, 30).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.THURSDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 0), LocalTime.of(22, 30).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.FRIDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 0), LocalTime.of(23, 0).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.SATURDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(11, 30), LocalTime.of(23, 0).minusNanos(1L)));
        workingSheetMap.put(DayOfWeek.SUNDAY, new AbstractMap.SimpleEntry<>(LocalTime.of(16, 30), LocalTime.of(22, 30).minusNanos(1L)));
        var result = workingSheet.parseWorkingSheet(workingSheetStr);
        assertEquals(workingSheetMap, result);
    }
}