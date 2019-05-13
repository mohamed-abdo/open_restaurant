import domain.service.RestaurantSrv;
import domain.service.WorkingDays;
import domain.service.WorkingHours;
import domain.service.WorkingSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.lang.NonNull;
import utils.CSVContent;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

@SpringBootApplication
@ComponentScan(basePackageClasses = {RestaurantSrv.class, WorkingSheet.class, WorkingDays.class, WorkingHours.class, CSVContent.class})
public class Application implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);


    private RestaurantSrv restaurantSrv;

    public Application(@Autowired RestaurantSrv restaurantSrv) {
        this.restaurantSrv = restaurantSrv;
    }

    public static void main(String... args) {
        LOGGER.info("application had started...");
        System.out.println("Please type executable, file path (csv), datetime as yyyy-MM-dd h:m a ex: " +
                "file.csv 2019-05-15 10:00 PM");
        SpringApplication.run(Application.class, args);
        LOGGER.info("application had finished.");
    }

    @Override
    public void run(@NonNull String... args) throws Exception {
        Objects.requireNonNull(args);
        LOGGER.info("executing command line with params: {}", String.join(", ", args));

        if (!Files.exists(Paths.get(args[0]))) {
            System.out.println(String.format("file [%s] is not exists file, abort!", args[0]));
            return;
        }

        try {
            LocalDateTime.parse(args[1], DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a"));
        } catch (DateTimeParseException ex) {
            System.out.println(String.format("datetime [%s] is not a valid date time format expected format: yyyy-MM-dd h:m a ex: " +
                    " \"2019-05-15 10:00 PM\", abort!", args[1]));
            return;
        }

        System.out.println("Open restaurants: ");

        restaurantSrv.findOpenRestaurant(args[0], args[1]).forEach(r ->
                System.out.println(r));
    }
}
