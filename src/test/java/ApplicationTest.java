import domain.service.RestaurantSrv;
import domain.service.WorkingDays;
import domain.service.WorkingHours;
import domain.service.WorkingSheet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import utils.CSVContent;

@SpringBootTest(classes = {RestaurantSrv.class, WorkingSheet.class, WorkingDays.class, WorkingHours.class, CSVContent.class})
@ExtendWith(SpringExtension.class)
class ApplicationTest {

    @Value("classpath:rest_hours.csv")
    private Resource csvFile;

    @Autowired
    private RestaurantSrv restaurantSrv;

    @Test
    void run() throws Exception {
        Application application = new Application(restaurantSrv);
        var filePath = csvFile.getFile().getPath();
        var dateTime = "2019-05-15 9:00 PM";
        application.run(filePath, dateTime);
    }
}