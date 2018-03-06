package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public interface MealDao {

    public void addMeal(Meal meal);

    public void deleteMeal(int mealId);

    public void updateMeal(Meal meal);

    public List<MealWithExceed> getAllMeals();

    public Meal getMealById(int mealId);
}
