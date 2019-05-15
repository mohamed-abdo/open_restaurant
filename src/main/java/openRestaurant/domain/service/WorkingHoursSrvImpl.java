package openRestaurant.domain.service;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class WorkingHoursSrvImpl implements WorkingHoursSrv {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorkingHoursSrvImpl.class);
}
