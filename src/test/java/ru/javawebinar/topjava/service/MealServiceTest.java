package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

import static org.junit.Assert.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "test food", 1500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), DINNER, newMeal, SUPPER);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal();
        updated.setId(SUPPER_ID);
        updated.setDateTime(LocalDateTime.now());
        updated.setCalories(1000);
        updated.setDescription("new description");
        service.update(updated, USER_ID);
        assertMatch(service.get(SUPPER_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateOtherUserFood() throws Exception {
        Meal updated = new Meal();
        updated.setId(BREAKFAST_ID);
        updated.setDateTime(LocalDateTime.now());
        updated.setCalories(1000);
        updated.setDescription("new description");
        service.update(updated, USER_ID);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> meals = service.getAll(USER_ID);
        assertMatch(meals, DINNER, SUPPER);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(SUPPER_ID, USER_ID);
        assertMatch(meal, SUPPER);
    }

    @Test(expected = NotFoundException.class)
    public void getOtherUserFood() throws Exception {
        Meal meal = service.get(BREAKFAST_ID, USER_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(SUPPER_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), DINNER);
    }

    @Test(expected = NotFoundException.class)
    public void deleteOtherUserFood() {
        service.delete(BREAKFAST_ID, USER_ID);
    }
}