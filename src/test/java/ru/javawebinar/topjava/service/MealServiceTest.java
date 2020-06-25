package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;
import java.util.*;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.getNew;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;


    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID,USER_ID);
        assertMatch(meal, EXPECTED_MEAL);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertNull(repository.get(MEAL_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, EXPECTED_MEAL);

        all = service.getAll(MealTestData.ADMIN_ID);
        assertMatch(all, ADMIN_EXPECTED_MEAL);
    }

    @Test
    public void update() {
        Meal updated = getUpdatedMeal();
        service.update(updated, USER_ID);
        assertMatch(service.get(MEAL_ID, USER_ID), updated);
    }

    @Test
    public void create() {
        Meal newMeal = getMeal();
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        MealTestData.assertMatch(created, newMeal);
        MealTestData.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(3, USER_ID));
    }

    @Test
    public void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(3, USER_ID));
    }

    @Test
    public void updateNotFound() throws Exception {
        Meal updated = getUpdatedMeal();
        service.update(updated, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, ADMIN_ID));
    }
}