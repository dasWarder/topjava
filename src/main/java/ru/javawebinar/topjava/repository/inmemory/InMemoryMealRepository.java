package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {

    private Map<Integer, Meal> userMealMap = new ConcurrentHashMap<>();
    private AtomicInteger  id = new AtomicInteger(1);
    {
        Meal mealTest = new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        mealTest.setUserId(2);
        userMealMap.put(7, mealTest);
    }


//    {
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
//                saveToList(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
//    }


    @Override
    public Meal save(Meal meal, int authUserId) {
        if (meal != null) {
            meal.setUserId(authUserId);
            saveToList(meal);
            return meal;
        }
        return null;
    }

    @Override
    public boolean delete(int id,int authUserId) {
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
        Meal mealForCheck = userMealMap.get(id);
        if (authUserId == mealForCheck.getUserId()) {
            return mealForCheck;
        }

        return null;
    }

    @Override
    public List<Meal> getAll(int authUserId) {

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
