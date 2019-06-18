package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            save(meal, 1);
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        repository.computeIfAbsent(userId, key -> new ArrayList<>());
        List<Meal> meals = repository.get(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        } else {
            meals = meals.stream()
                    .filter(item -> !meal.getId().equals(item.getId())).collect(Collectors.toList());
        }
        meals.add(meal);
        repository.put(userId, meals);
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        List<Meal> meals = repository.get(userId);
        return meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = repository.get(userId);
        if (meals.isEmpty()) {
            return null;
        }
        return meals.stream().
                filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }
}

