package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID = 1;
    public static final int ADMIN_MEAL_ID = MEAL_ID + 1;
    public static final int NOT_FOUND_MEAL_ID = 10;
    private static final LocalDateTime localDateTime = null;
    public static final Meal EXPECTED_MEAL = new Meal(MEAL_ID, localDateTime , "Lunch", 600);
    public static final Meal ADMIN_EXPECTED_MEAL = new Meal(ADMIN_MEAL_ID, localDateTime, "Random food event", 1200);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

    public static Meal getUpdatedMeal() {
        Meal updated = new Meal(EXPECTED_MEAL);
        updated.setDescription("UpdatedMeal");
        updated.setDateTime(LocalDateTime.of(2020, 12, 10, 12, 44));
        return updated;
    }

    public static Meal getMeal() {
        return new Meal(null, LocalDateTime.of(2020, 5, 12, 12, 44), "newMeal", 670);
    }
}
