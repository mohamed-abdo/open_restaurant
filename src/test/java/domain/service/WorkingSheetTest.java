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
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {WorkingSheet.class, WorkingDays.class, WorkingHours.class})
@ExtendWith(SpringExtension.class)
class WorkingSheetTest {

    @Autowired
    private WorkingSheet workingSheet;

    @Test
    void parseWorkingSheet() {
        String workingSheetStr = "Mon-Mon, Sun 11:30 am - 10 pm ";
        var workingSheetMap = new HashMap<DayOfWeek, Duration>();
        workingSheetMap.put(DayOfWeek.MONDAY, Duration.between(LocalTime.of(11, 30), LocalTime.of(22, 0)));
        workingSheetMap.put(DayOfWeek.SUNDAY, Duration.between(LocalTime.of(11, 30), LocalTime.of(22, 0)));
        assertEquals(workingSheetMap, workingSheet.parseWorkingSheet(workingSheetStr));
    }

    @Test
    void parseWorkingSheet_Full() {
        String workingSheetStr = "Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm";
        var workingSheetMap = new HashMap<DayOfWeek, Duration>();
        workingSheetMap.put(DayOfWeek.MONDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.TUESDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.WEDNESDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.THURSDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.FRIDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(23, 0)));
        workingSheetMap.put(DayOfWeek.SATURDAY, Duration.between(LocalTime.of(11, 30), LocalTime.of(23, 0)));
        workingSheetMap.put(DayOfWeek.SUNDAY, Duration.between(LocalTime.of(16, 30), LocalTime.of(22, 30)));
        var result = workingSheet.parseWorkingSheet(workingSheetStr);
        assertEquals(workingSheetMap, result);
    }
}