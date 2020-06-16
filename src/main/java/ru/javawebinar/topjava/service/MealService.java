package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {

        this.repository = repository;
    }

    public Meal create(Meal meal, int authUserId) {

        return repository.save(meal, authUserId);
    }

    public void delete(int id, int authUserId) {
        checkNotFoundWithId(repository.delete(id, authUserId), id);
    }

    public Meal get(int mealid, int authUserId) {
        return checkNotFoundWithId(repository.get(mealid, authUserId), mealid);
    }

    public List<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    public void update(Meal meal, int id, int authUserId) {
        checkNotFoundWithId(repository.update(meal, id, authUserId), meal.getId());
    }



}