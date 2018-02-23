package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        for (UserMealWithExceed meal:getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000)) {
            System.out.println(meal);
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        HashMap<LocalDate,Integer> mealDates = new HashMap<>();
        ArrayList<UserMeal> requiredMeals = new ArrayList<>();
        LocalDate date;
        LocalTime time;
        for (UserMeal meal:mealList) {
            date = meal.getDateTime().toLocalDate();
            if (mealDates.containsKey(date))
                mealDates.put(date,mealDates.get(date)+meal.getCalories());
            else
                mealDates.put(date,meal.getCalories());
            time = meal.getDateTime().toLocalTime();
            if (TimeUtil.isBetween(time,startTime,endTime))
                requiredMeals.add(meal);
        }
        ArrayList<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal:requiredMeals) {
            if (mealDates.get(meal.getDateTime().toLocalDate())>caloriesPerDay)
                result.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),true));
            else
                result.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),false));
        }
        return result;
    }
}
