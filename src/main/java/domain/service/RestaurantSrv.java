package domain.service;

import domain.model.Restaurant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantSrv {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantSrv.class);

    @Autowired
    private WorkingSheet workingSheet;

    public Restaurant builder(@Nullable UUID id, @NonNull String[] dataRow) {
        //ex:Kyoto Sushi,Mon-Thu 11 am - 10:30 pm  / Fri 11 am - 11 pm  / Sat 11:30 am - 11 pm  / Sun 4:30 pm - 10:30 pm,,,
        LOGGER.info("building restaurant object from raw data : {}", dataRow);
        Objects.requireNonNull(dataRow);
        if (dataRow.length < 2)
            LOGGER.error("invalid data format: {}", String.join(",", dataRow));
        var restaurantName = dataRow[0].trim();
        if (restaurantName.length() == 0)
            LOGGER.error("invalid restaurant name format: {}", restaurantName);
        var workingSheetStr = dataRow[1].trim();
        if (workingSheetStr.length() == 0)
            LOGGER.error("invalid working sheet format: {}", workingSheetStr);
        var workingSheetMap = workingSheet.parseWorkingSheet(workingSheetStr);
        return new Restaurant(Optional.ofNullable(id).orElse(UUID.randomUUID()), restaurantName, workingSheetMap);
    }
}
