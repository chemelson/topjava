package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.MealsUtil.getWithExcess;

import java.util.List;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<MealTo> getAllByUser() {
        int userId = authUserId();
        log.info("getAll for user " + userId);
        return getWithExcess(service.getAllByUser(userId), authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        int userId = authUserId();
        log.info("get meal " + id + " for user " + userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        log.info("create meal for user " + userId);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("delete meal " + id + " for user " + userId);
        service.delete(id, userId);
    }

    public void update(Meal meal) {
        int userId = authUserId();
        log.info("update meal " + meal.getId() + " for user " + userId);
        service.update(meal, userId);
    }
}