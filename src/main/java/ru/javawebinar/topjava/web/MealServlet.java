package ru.javawebinar.topjava.web;

import java.sql.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealListHard;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;


public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Meal> meals = new MealListHard().getMeals();

        Map<LocalDate, Integer> hashMeal = meals.stream().collect(
                Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories)));

        List<MealTo> mealToArrayList = meals.stream().map(
                meal -> new MealTo
                        (meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                hashMeal.get(meal.getDate()) > 2000)).
                collect(Collectors.toList());

        LOG.debug("redirect to meals");


        req.setAttribute("mealList", mealToArrayList);

        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
