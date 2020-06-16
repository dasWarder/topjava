package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import ru.javawebinar.topjava.web.MealServlet;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


@Repository
public class InMemoryMealRepository implements MealRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private Map<Integer, Meal> userMealMap = new ConcurrentHashMap<>();
    private AtomicInteger  id = new AtomicInteger(1);


    @Override
    public Meal save(Meal meal, int authUserId) {
        log.info("save", meal);
        if (meal != null) {
            meal.setUserId(authUserId);
            saveToList(meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id,int authUserId) {
        log.info("delete", id);
        Meal mealForDelete = userMealMap.get(id);
        if (mealForDelete.getUserId() == authUserId) {
            userMealMap.remove(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Meal get(int id,int authUserId) {
        log.info("get", id);
        Meal mealForCheck = userMealMap.get(id);
        if (authUserId == mealForCheck.getUserId()) {
            return mealForCheck;
        }

        return null;
    }

    @Override
    public List<Meal> getAll(int authUserId) {
        log.info("getAll");
        List<Meal> mealList = new ArrayList<>();
        for(Map.Entry<Integer, Meal> map : userMealMap.entrySet()) {
            if (Integer.valueOf(map.getValue().getUserId()) == authUserId) {
                mealList.add(map.getValue());
            }
        }

        Collections.sort(mealList, (o1, o2) -> - o1.getDate().compareTo(o2.getDate()));

        return mealList;
    }

    @Override
    public Meal update(Meal meal, int id, int authUserId) {
        log.info("update",meal);
        Meal mealFromMap = userMealMap.get(id);
        if (mealFromMap.getUserId() == authUserId) {
            if (mealFromMap != null) {
                meal.setUserId(mealFromMap.getUserId());
                meal.setId(mealFromMap.getId());
                return userMealMap.put(id, meal);
            }
        }

        return null;
    }

    public Meal saveToList(Meal meal) {
        if (meal.isNew()){
            meal.setId(id.getAndIncrement());
        }
        return userMealMap.put(meal.getId(), meal);

    }
}
