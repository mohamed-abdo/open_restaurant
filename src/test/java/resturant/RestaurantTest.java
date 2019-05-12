package resturant;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import utils.CSVContent;

import java.io.IOException;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {RestaurantTest.class})
class RestaurantTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantTest.class);

    private final Resource csvFile;
    private final CSVContent csvContent;

    public RestaurantTest(@Value("classpath:rest_hours.csv") Resource csvFile) throws IOException {
        this.csvFile = csvFile;
        this.csvContent = CSVContent.getInstance(csvFile.getURL().getPath());
    }

    @BeforeAll
    static void test_setup() {
        LOGGER.info("starting unit test");
    }

    @BeforeEach
    void test_beforeEach() {
        Assertions.assertTrue(csvFile.exists());
    }

    @Test
    void test_loadCSVFile() {
        Assertions.assertNotNull(csvContent.getFileContent());
        LOGGER.info("printing file content");
        csvContent.getFileContent().forEach(line -> {
            LOGGER.info("getting data of: {}", String.join(",", line));
        });

    }


    @AfterAll
    static void test_finalize() {
        LOGGER.info("finishing unit test.");
    }
}