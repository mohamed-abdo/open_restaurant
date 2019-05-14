package domain.service;

import domain.model.Restaurant;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public interface OpenRestaurant {
    default List findOpenRestaurant(@NonNull List<Restaurant> restaurants, @NonNull LocalDateTime dateTime) {
        Objects.requireNonNull(restaurants);
        Objects.requireNonNull(dateTime);
        var dayOfWeek = dateTime.getDayOfWeek();
        var time = dateTime.toLocalTime();
        return restaurants.stream().filter(r ->
                r.getWorkingHours().containsKey(dayOfWeek) &&
                        r.getWorkingHours().values().stream()
                                .anyMatch(t -> t.stream().
                                        anyMatch(tt -> tt.getLeft().compareTo(time) <= 0 && tt.getRight().compareTo(time) > 0)))
                .map(Restaurant::getName)
                .collect(Collectors.toList());
    }
}
