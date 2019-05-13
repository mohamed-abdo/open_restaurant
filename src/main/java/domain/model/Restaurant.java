package domain.model;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.Map;
import java.util.UUID;

public class Restaurant {
    private UUID id;
    private String name;
    private Map<DayOfWeek, Duration> workingHours;

    public Restaurant(UUID id, String name, Map<DayOfWeek, Duration> workingHours) {
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

    public Map<DayOfWeek, Duration> getWorkingHours() {
        return workingHours;
    }
}
