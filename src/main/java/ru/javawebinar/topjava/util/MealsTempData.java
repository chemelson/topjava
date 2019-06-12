package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealsTempData {
    public static List<Meal> getTempData() {
        final List<Meal> meals = Arrays.asList(
                new Meal(1L, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Breakfast", 500),
                new Meal(2L, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Dinner", 1000),
                new Meal(3L, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Supper", 500),
                new Meal(4L, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Breakfast", 1000),
                new Meal(5L, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Dinner", 500),
                new Meal(6L, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Supper", 510)
        );
        return meals;
    }
}
