package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = 1;
    public static final int NOT_FOUND_MEAL_ID = 10;

//    (100000, '25.11.2020 10:33:00', 'Lunch', 600),
//    LocalDateTime.of(2020, 11, 25, 10, 33, 00);
    private static final LocalDateTime localDateTime = null;
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final Meal EXPECTED_MEAL = new Meal(MEAL_ID, localDateTime , "Lunch", 600);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }
}
