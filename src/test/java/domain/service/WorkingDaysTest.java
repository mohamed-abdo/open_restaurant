package domain.service;

import domain.service.WorkingDays;
import domain.service.WorkingHours;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {WorkingDays.class, WorkingHours.class})
@ExtendWith(SpringExtension.class)
class WorkingDaysTest {

    @Autowired
    private WorkingDays workingDays;

    @Test
    void parseDayString() {
        assertEquals(DayOfWeek.SUNDAY, workingDays.parseDayString("sun"));
    }

    @Test
    void parseDatString_CAPS() {
        assertEquals((DayOfWeek.MONDAY), workingDays.parseDayString("MON"));
    }

    @Test
    void calcWorkingDays() {
        assertEquals(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                workingDays.calcWorkingDays(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
    }

    @Test
    void calcWorkingDays_MON_TO_SUN() {
        assertEquals(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                workingDays.calcWorkingDays(DayOfWeek.MONDAY, DayOfWeek.SUNDAY));
    }

    @Test
    void calcWorkingDays_THU_TO_WED() {
        assertThrows(
                IllegalArgumentException.class,
                () -> workingDays.calcWorkingDays(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY));
    }

    @Test
    void parseWorkingDays() {
        String workingSheetStr = "Mon-Thu, Sun";
        var workingSheet = Set.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.SUNDAY);
        assertEquals(workingSheet, workingDays.parseWorkingDaysSheet(workingSheetStr));
    }


}