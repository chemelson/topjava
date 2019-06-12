package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsTempData;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final int CALORIES_DAY_LIMIT = 2000;
    private static DateTimeFormatter mealTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> meals = MealsUtil.getFilteredWithExcess(MealsTempData.getTempData(),
                LocalTime.MIN, LocalTime.MAX, CALORIES_DAY_LIMIT);
        request.setAttribute("meals", meals);
        request.setAttribute("mealTimeFormatter", mealTimeFormatter);
        log.debug("forwarding to meals");
        request.getRequestDispatcher("jsp/meal/list.jsp").forward(request, response);
    }

}
