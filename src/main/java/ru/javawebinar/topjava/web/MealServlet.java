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
    private Storage<Meal> storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new MealMemoryStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        if (action == null) {
            List<MealTo> meals = MealsUtil.getFilteredWithExcess(storage.getAll(),
                    LocalTime.MIN, LocalTime.MAX, CALORIES_DAY_LIMIT);
            request.setAttribute("meals", meals);
            request.setAttribute("mealTimeFormatter", mealTimeFormatter);
            request.getRequestDispatcher("jsp/meal/list.jsp").forward(request, response);
            return;
        }

        MealTo mealTo = new MealTo(LocalDateTime.now(), "", 0);
        String dispatchUrl;
        Meal meal;
        switch (action) {
            case "add":
                dispatchUrl = "/jsp/meal/add.jsp";
                break;
            case "edit":
                meal = getMealFromRequestParams(id);
                mealTo = new MealTo(meal.getDateTime(),
                        meal.getDescription(), meal.getCalories(), meal.getId());
                dispatchUrl = "/jsp/meal/edit.jsp";
                request.setAttribute("isoTimeFormatter", DateTimeFormatter.ISO_DATE_TIME);
                break;
            case "view":
                meal = getMealFromRequestParams(id);
                mealTo = new MealTo(meal.getDateTime(),
                        meal.getDescription(), meal.getCalories(), meal.getId());
                dispatchUrl = "/jsp/meal/view.jsp";
                request.setAttribute("mealTimeFormatter", mealTimeFormatter);
                break;
            case "delete":
                meal = getMealFromRequestParams(id);
                storage.delete(meal);
                response.sendRedirect("meals");
                return;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        request.setAttribute("meal", mealTo);
        request.getRequestDispatcher(dispatchUrl).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        LocalDateTime mealDateTime = LocalDateTime.parse(request.getParameter("mealdatetime"),
                DateTimeFormatter.ISO_DATE_TIME);
        String mealDescription = request.getParameter("mealdescription");
        int mealCalories = Integer.valueOf(request.getParameter("mealcalories"));

        final boolean isCreate = (id == null || id.length() == 0);
        if (isCreate) {
            storage.save(new Meal(mealDateTime, mealDescription, mealCalories));
        } else {
            long mealId = Long.valueOf(id);
            storage.update(new Meal(mealDateTime, mealDescription, mealCalories, mealId));
        }
        response.sendRedirect("meals");
    }

    private Meal getMealFromRequestParams(String id) {
        long mealId = Long.valueOf(id);
        return storage.get(mealId);
    }
}
