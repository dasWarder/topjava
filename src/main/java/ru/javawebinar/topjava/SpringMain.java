package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;
import ru.javawebinar.topjava.web.user.ProfileRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.*;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            ProfileRestController profileRestController = appCtx.getBean(ProfileRestController.class);
            System.out.println("-------------------->");
            
            adminUserController.create(new User (null, "name", "test@mail.ru", "pass", Role.USER));

            System.out.println(adminUserController.getAll());
            //            MealRestController mrc = appCtx.getBean(MealRestController.class);
//            Meal meal = mrc.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 20, 10, 5), "Завтрак 2", 600));
//            Meal meal2 = mrc.create(new Meal(LocalDateTime.of(2020, Month.JANUARY, 21, 12, 5), "Завтрак 3", 700));
//            Meal meal3 = mrc.create(new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 2, 17, 23), "Завтрак 4", 300));





        }
    }

}
