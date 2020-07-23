package ru.javawebinar.topjava.web.meal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractController {



    @GetMapping("/delete")
    public String deleteMeal(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }


    @GetMapping
    public String getAllMeals(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }


    @GetMapping("/create")
    public String createMeal(Model model) {
        model.addAttribute("meal", new Meal(LocalDateTime.now(), "", 1000));
        return "mealForm";
    }


    @GetMapping("/update")
    public String updateMeal(HttpServletRequest request, Model model) {
        model.addAttribute("meal", super.get(getId(request)));
        return "mealForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request, Model model) {
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty()? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));

        if(meal.isNew()) {
            super.create(meal);
        } else {
            super.update(meal, meal.getId());
        }
        return "redirect:/meals";
    }


    @PostMapping("/filter")
    public String getMealBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));

        return "meals";
    }


    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }
}
