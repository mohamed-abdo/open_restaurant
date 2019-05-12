package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;

@SpringBootTest(classes = {WorkingHoursTest.class})
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackageClasses = {domain.WorkingHours.class})
public class WorkingHoursTest {

    @Autowired
    private WorkingHours workingHours;

    @Test
    void test_calcWorkingHours() {
        var workingDay = new HashMap<DayOfWeek, Duration>();
        workingDay.put(DayOfWeek.SUNDAY, Duration.between(LocalTime.of(11, 30), LocalTime.of(21, 00)));
        Assertions.assertEquals(workingDay, workingHours.calcWorkingHours("11:30 am - 9 pm"));
    }
}
