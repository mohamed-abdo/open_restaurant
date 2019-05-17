package openRestaurant.API;

import openRestaurant.domain.model.Restaurant;
import openRestaurant.domain.service.OpenRestaurantSrv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import utils.CSVContent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantController {

    private final OpenRestaurantSrv openRestaurantSrv;

    @Value("classpath:rest_hours.csv")
    private Resource csvFile;

    public RestaurantController(@Autowired OpenRestaurantSrv openRestaurantSrv) {
        this.openRestaurantSrv = openRestaurantSrv;
    }

    @RequestMapping("/restaurants/{now}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ExceptionHandler
    public List<Restaurant> findOpenRestaurants(@Nullable @PathVariable String now) throws IOException {
        var content = CSVContent.getInstance(csvFile.getURI().getPath()).getFileContent();
        var param = Optional.ofNullable(now)
                .map(n -> LocalDateTime.parse(n, DateTimeFormatter.ofPattern("yyyy-MM-dd h:m a")))
                .orElse(LocalDateTime.now());
        return openRestaurantSrv.findOpenRestaurantsFromRawData(content, param);
    }
}
