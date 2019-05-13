package domain.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Map;
import java.util.UUID;

public class Restaurant {
    private UUID id;
    private String name;
    private Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> workingHours;

    public Restaurant(UUID id, String name, Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> workingHours) {
        this.id = id;
        this.name = name;
        this.workingHours = workingHours;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Map<DayOfWeek, Map.Entry<LocalTime, LocalTime>> getWorkingHours() {
        return workingHours;
    }
}
