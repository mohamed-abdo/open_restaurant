package domain.service;

import domain.model.Restaurant;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import utils.CSVContent;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {RestaurantSrv.class, WorkingSheet.class, WorkingDays.class, WorkingHours.class})
@ExtendWith(SpringExtension.class)
class RestaurantSrvTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantSrvTest.class);

    private final Resource csvFile;
    private final CSVContent csvContent;

    @Autowired
    private RestaurantSrv restaurantSrv;

    public RestaurantSrvTest(@Value("classpath:rest_hours.csv") Resource csvFile) throws IOException {
        this.csvFile = csvFile;
        this.csvContent = CSVContent.getInstance(csvFile.getFile().getPath());
    }

    @BeforeAll
    static void setup() {
        LOGGER.info("starting unit test");
    }

    @BeforeEach
    void beforeEach() {
        assertTrue(csvFile.exists());
    }

    @Test
    void loadCSVFile() {
        assertNotNull(csvContent.getFileContent());
        LOGGER.info("printing file content");
        csvContent.getFileContent().forEach(line -> {
            LOGGER.info("getting data of: {}", String.join(",", line));
        });
    }

    @Test
    void loadRestaurantData() {
        List<String[]> csvData = new ArrayList<>() {{
            add("Kyoto Sushi,Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm,,,"
                    .split(","));
        }};

        var id = UUID.randomUUID();
        var restaurantName = "Kyoto Sushi";
        var workingSheetMap = new HashMap<DayOfWeek, Duration>();
        workingSheetMap.put(DayOfWeek.MONDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.TUESDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.WEDNESDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.THURSDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(22, 30)));
        workingSheetMap.put(DayOfWeek.FRIDAY, Duration.between(LocalTime.of(11, 0), LocalTime.of(23, 0)));
        workingSheetMap.put(DayOfWeek.SATURDAY, Duration.between(LocalTime.of(11, 30), LocalTime.of(23, 0)));
        workingSheetMap.put(DayOfWeek.SUNDAY, Duration.between(LocalTime.of(16, 30), LocalTime.of(22, 30)));

        var restaurant = new Restaurant(id, restaurantName, workingSheetMap);

        List<Restaurant> restaurants = csvData.stream().parallel()
                .map(dataRow -> restaurantSrv.builder(id, dataRow))
                .collect(Collectors.toList());
        Assertions.assertNotNull(restaurants);
        Assertions.assertFalse(restaurants.stream().findAny().isEmpty());
        Assertions.assertEquals(restaurant.getId(), restaurants.stream().findAny().get().getId());
    }

    @AfterAll
    static void cleanUp() {
        LOGGER.info("finishing unit test.");
    }
}