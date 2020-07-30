package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.TestMatcher;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.ResourceControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Collection;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;

public class MealRestControllerTest extends AbstractControllerTest {

    public static final int USER_ID = SecurityUtil.authUserId();
    @Autowired
    private MealService mealService;
    private static final String REST_URL = MealRestController.REST_URL;

    @Test
    public void testGet() throws Exception {
        perform(get("/meals/" + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        perform(delete("/meals/" + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = MealTestData.getUpdated();
        perform(put("/meals/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        MEAL_MATCHER.assertMatch(mealService.get(MEAL1_ID, USER_ID), updated);
    }

    @Test
    public void testCreate() throws Exception {
        Meal expected = getNew();
        ResultActions actions = perform(post("/meals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isCreated())
                .andDo(print());
        Meal returned = TestUtil.readFromJson(actions, Meal.class);
        expected.setId(returned.getId());

        MEAL_MATCHER.assertMatch(expected, returned);
//        MEAL_MATCHER.assertMatch(mealService.getAll(USER_ID), MEALS);
    }

    @Test
    public void testGetAll() throws Exception {
        ResultActions resultActions = perform(get(REST_URL)
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(mealService.getAll(USER_ID)));
    }

    @Test
    public void testGetBetween() throws Exception {
        perform(get("/meals/by?=" + MEAL1.getDateTime())
        .contentType("ISO_LOCAL_DATE_TIME"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MEAL_MATCHER.contentJson(MEALS));

    }



}
