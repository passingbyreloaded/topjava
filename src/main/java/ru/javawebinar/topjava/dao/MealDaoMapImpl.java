package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMapImpl implements MealDao {

    private static Map<Integer,Meal> mealMap = new ConcurrentHashMap<>();
    private static AtomicInteger count;
    static {
        MealsUtil.getHardcodedMeals().forEach(meal -> mealMap.put(meal.getMealId(),meal));
        count = new AtomicInteger(mealMap.size());
    }

    @Override
    public Meal add(Meal meal) {
        meal.setMealId(count.incrementAndGet());
        return mealMap.put(meal.getMealId(),meal);
    }

    @Override
    public void delete(int mealId) {
        mealMap.remove(mealId);
    }

    @Override
    public void update(Meal meal) {
        mealMap.put(meal.getMealId(),meal);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public Meal getById(int mealId) {
        return mealMap.get(mealId);
    }
}
