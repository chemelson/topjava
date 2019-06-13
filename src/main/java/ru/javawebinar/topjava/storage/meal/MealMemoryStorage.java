package ru.javawebinar.topjava.storage.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.util.MealsTempData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MealMemoryStorage implements Storage<Meal> {
    private Map<Long, Meal> storage = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(0);

    public MealMemoryStorage() {
        MealsTempData.getTempData().forEach(this::save);
    }

    @Override
    public void save(Meal meal) {
        Long id = counter.getAndIncrement();
        meal.setId(id);
        storage.putIfAbsent(id, meal);
    }

    @Override
    public Meal get(Long id) {
        return storage.get(id);
    }

    @Override
    public void update(Meal meal) {
        storage.replace(meal.getId(), meal);
    }

    @Override
    public void delete(Long id) {
        storage.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(storage.values());
    }
}
