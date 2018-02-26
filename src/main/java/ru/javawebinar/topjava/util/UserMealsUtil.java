package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        getFilteredWithExceeded2(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000).forEach(System.out::println);

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> mealDates = new HashMap<>();
        List<UserMeal> requiredMeals = new ArrayList<>();
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
        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal meal:requiredMeals) {
            if (mealDates.get(meal.getDateTime().toLocalDate())>caloriesPerDay)
                result.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),true));
            else
                result.add(new UserMealWithExceed(meal.getDateTime(),meal.getDescription(),meal.getCalories(),false));
        }
        return result;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate,Integer> mealDates = new HashMap<>();
        List<UserMeal> requiredMeals = mealList.stream().map(userMeal -> {mealDates.merge(userMeal.getDateTime().toLocalDate(),userMeal.getCalories(),Integer::sum); return userMeal; })
                                                        .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(),startTime,endTime))
                                                        .collect(Collectors.toList());

        return requiredMeals.stream().map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                                                                             userMeal.getDescription(),
                                                                             userMeal.getCalories(),
                                                                      mealDates.get(userMeal.getDateTime().toLocalDate())>caloriesPerDay)).collect(Collectors.toList());
    }
}
