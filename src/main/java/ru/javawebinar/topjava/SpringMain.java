package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.*;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            MealRestController mrc = appCtx.getBean(MealRestController.class);
            Meal meal = mrc.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 20, 10, 5), "Завтрак 2", 600));
            Meal meal2 = mrc.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 21, 12, 5), "Завтрак 3", 700));
            Meal meal3 = mrc.create(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 17, 23), "Завтрак 4", 300));

            mrc.update(new Meal(LocalDateTime.of(2022, Month.MARCH, 21, 10, 5), "Завтрак 4", 800), 1);
            printMeal(mrc);
            System.out.println("------------------------------------->");

            mrc.delete(0);

            printMeal(mrc);

            System.out.println("------------------------------------->");

            Meal mealCheck = mrc.get(1);
            System.out.println(mealCheck.getId() + " " +
                    mealCheck.getDescription() +
                    " " + mealCheck.getCalories() + " " +
                    mealCheck.getDateTime());



        }
    }

    private static void printMeal(MealRestController mrc) {
        mrc.getAll().stream().forEach(mealCheck -> {
            System.out.println(mealCheck.getId() + " " + mealCheck.getDescription() + " " + mealCheck.getCalories() + " " + mealCheck.getDateTime());
        });
    }
}
