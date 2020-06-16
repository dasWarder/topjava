package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.util.List;

public interface MealRepository {


    Meal save(Meal meal, int authUserId);

    boolean delete(int id,int userId);

    Meal get(int id,int userId);

    List<Meal> getAll(int userId);

    Meal update (Meal meal, int id, int authUserId);
}
