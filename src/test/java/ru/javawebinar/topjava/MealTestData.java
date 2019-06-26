package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int BREAKFAST_ID = START_SEQ + 2;
    public static final int DINNER_ID = START_SEQ + 3;
    public static final int SUPPER_ID = START_SEQ + 4;


    public static final Meal BREAKFAST = new Meal(BREAKFAST_ID,
            LocalDateTime.of(2019, Month.MAY, 30, 10, 0), "Breakfast", 500);
    public static final Meal DINNER = new Meal(DINNER_ID,
            LocalDateTime.of(2019, Month.MAY, 30, 13, 0), "Dinner", 1000);
    public static final Meal SUPPER = new Meal(SUPPER_ID,
            LocalDateTime.of(2019, Month.MAY, 30, 20, 0), "Supper", 500);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields().isEqualTo(expected);
    }
}
