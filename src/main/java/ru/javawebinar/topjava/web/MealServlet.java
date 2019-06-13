package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.storage.Storage;
import ru.javawebinar.topjava.storage.meal.MealMemoryStorage;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final int CALORIES_DAY_LIMIT = 2000;
    private static DateTimeFormatter mealTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static DateTimeFormatter isoTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
    private Storage<Meal> storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealMemoryStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        action = action != null ? action : "";
        String id = request.getParameter("id");

        Meal meal;
        switch (action) {
            case "add":
                meal = new Meal(null, "", 0);
                break;
            case "edit":
                meal = getMealFromRequestParams(id);
                request.setAttribute("isoTimeFormatter", isoTimeFormatter);
                break;
            case "delete":
                storage.delete(Long.valueOf(id));
                response.sendRedirect("meals");
                return;
            default:
                List<MealTo> meals = MealsUtil.getFilteredWithExcess(storage.getAll(),
                        LocalTime.MIN, LocalTime.MAX, CALORIES_DAY_LIMIT);
                request.setAttribute("meals", meals);
                request.setAttribute("mealTimeFormatter", mealTimeFormatter);
                request.getRequestDispatcher("WEB-INF/jsp/meal/meals.jsp").forward(request, response);
                return;
        }
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("WEB-INF/jsp/meal/edit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        LocalDateTime mealDateTime = LocalDateTime.parse(request.getParameter("mealdatetime"),
                isoTimeFormatter);
        String mealDescription = request.getParameter("mealdescription");
        int mealCalories = Integer.valueOf(request.getParameter("mealcalories"));

        final boolean isCreate = (id == null || id.length() == 0);
        if (isCreate) {
            storage.save(new Meal(mealDateTime, mealDescription, mealCalories));
        } else {
            long mealId = Long.valueOf(id);
            storage.update(new Meal(mealId, mealDateTime, mealDescription, mealCalories));
        }
        response.sendRedirect("meals");
    }

    private Meal getMealFromRequestParams(String id) {
        long mealId = Long.valueOf(id);
        return storage.get(mealId);
    }
}
