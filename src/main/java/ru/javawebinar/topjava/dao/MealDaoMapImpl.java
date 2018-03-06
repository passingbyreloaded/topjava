package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMapImpl implements MealDao {

    private static ConcurrentMap<Integer,Meal> mealMap = new ConcurrentHashMap<>();
    private static AtomicInteger count;
    static {
        MealsUtil.getHardcodedMeals().forEach(meal -> mealMap.put(meal.getMealId(),meal));
        count = new AtomicInteger(mealMap.size());
    }

    @Override
    public synchronized void addMeal(Meal meal) {
        meal.setMealId(count.incrementAndGet());
        mealMap.put(meal.getMealId(),meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        mealMap.remove(mealId);
    }

    @Override
    public void updateMeal(Meal meal) {
        mealMap.put(meal.getMealId(),meal);
    }

    @Override
    public List<MealWithExceed> getAllMeals() {
        return MealsUtil.getFilteredWithExceeded(new ArrayList<>(mealMap.values()), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    public Meal getMealById(int mealId) {
        return mealMap.get(mealId);
    }
}
