package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepositoryImpl.class);
    private Map<Integer, List<Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> {
            save(meal, 1);
        });
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save {}", meal);
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
        log.info("delete {}", id);
        List<Meal> meals = repository.get(userId);
        return meals.removeIf(meal -> meal.getId() == id);
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        List<Meal> meals = repository.get(userId);
        if (meals.isEmpty()) {
            return null;
        }
        return meals.stream().
                filter(meal -> meal.getId() == id).findAny().orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll for user {}", userId);
        return repository.get(userId).stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }
}

