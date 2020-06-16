package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        return service.create(meal, authUserId());
    }

    public Meal get(int mealId) {
        return service.get(mealId, authUserId());
    }

    public void delete(int mealId) {
        service.delete(mealId, authUserId());
    }

    public void update(Meal meal, int id) {
        service.update(meal, id, authUserId());
    }

    public List<MealTo> getAll() {
        List<Meal> listOfMeal = service.getAll(authUserId());
        return MealsUtil.getTos(listOfMeal, authUserCaloriesPerDay());
    }

}