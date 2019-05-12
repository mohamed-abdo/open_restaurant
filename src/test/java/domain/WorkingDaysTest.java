package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = {WorkingDays.class})
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = {domain.WorkingDays.class})
class WorkingDaysTest {

    @Autowired
    private WorkingDays workingDays;

    @Test
    void test_parseDayString() {
        assertEquals(DayOfWeek.SUNDAY, workingDays.parseDayString("sun"));
    }

    @Test
    void test_parseDatString_CAPS() {
        assertEquals((DayOfWeek.MONDAY), workingDays.parseDayString("MON"));
    }

    @Test
    void test_calcWorkingDays() {
        assertEquals(Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY),
                workingDays.calcWorkingDays(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY));
    }

    @Test
    void test_calcWorkingDays_MON_TO_SUN() {
        assertEquals(
                Set.of(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY,
                        DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
                workingDays.calcWorkingDays(DayOfWeek.MONDAY, DayOfWeek.SUNDAY));
    }

    @Test
    void test_calcWorkingDays_THU_TO_WED() {
        assertThrows(
                IllegalArgumentException.class,
                () -> workingDays.calcWorkingDays(DayOfWeek.THURSDAY, DayOfWeek.WEDNESDAY));
    }
}