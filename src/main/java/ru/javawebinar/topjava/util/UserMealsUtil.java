package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        List<UserMealWithExceed> mealsWithExceed = getFilteredWithExceeded(
                meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(mealsWithExceed);

        List<UserMealWithExceed> mealsWithExceedStream = getFilteredWithExceededStream(
                meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        System.out.println(mealsWithExceedStream);
    }

    private static List<UserMealWithExceed> getFilteredWithExceededStream(
            List<UserMeal> meals, LocalTime mealStartTime, LocalTime mealEndTime, int caloriesLimitPerDay) {
        Map<LocalDate, Integer> caloriesForMealsInTimeByDate = meals
                .stream()
                .collect(Collectors.groupingBy(UserMeal::getDate, Collectors.summingInt(UserMeal::getCalories)));

        return meals
                .stream()
                .filter(meal -> {
                    LocalTime mealConsumedTime = meal.getTime();
                    return mealConsumedTime.isAfter(mealStartTime) && mealConsumedTime.isBefore(mealEndTime);
                })
                .filter(meal -> caloriesForMealsInTimeByDate.getOrDefault(meal.getDate(), 0) > caloriesLimitPerDay)
                .map(meal -> new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(
            List<UserMeal> meals, LocalTime mealStartTime, LocalTime mealEndTime, int caloriesLimitPerDay) {
        Map<LocalDate, Integer> caloriesPerDay = countCaloriesPerDay(meals);
        List<UserMealWithExceed> mealsWithExceed = new ArrayList<>();
        meals.forEach(userMeal -> {
            boolean mealInTime = mealInTime(userMeal, mealStartTime, mealEndTime);
            boolean mealCaloriesExceededPerDay = caloriesPerDay.getOrDefault(userMeal.getDate(), 0) > caloriesLimitPerDay;
            if (mealInTime && mealCaloriesExceededPerDay) {
                LocalDateTime mealDateAndTime = userMeal.getDateTime();
                String mealDescription = userMeal.getDescription();
                int mealCalories = userMeal.getCalories();
                mealsWithExceed.add(new UserMealWithExceed(mealDateAndTime, mealDescription, mealCalories, true));
            }
        });
        return mealsWithExceed;
    }

    private static Map<LocalDate, Integer> countCaloriesPerDay(List<UserMeal> meals) {
        Map<LocalDate, Integer> caloriesPerDay = new HashMap<>();
        meals.forEach(userMeal -> caloriesPerDay.merge(userMeal.getDate(), userMeal.getCalories(), Integer::sum));
        return caloriesPerDay;
    }

    private static boolean mealInTime(UserMeal userMeal, LocalTime mealStartTime, LocalTime mealEndTime) {
        LocalTime mealConsumedTime = userMeal.getTime();
        return mealConsumedTime.isAfter(mealStartTime) && mealConsumedTime.isBefore(mealEndTime);
    }
}
