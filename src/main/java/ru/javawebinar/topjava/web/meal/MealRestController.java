package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public Meal get(int id) {
        return service.get(id, AuthorizedUser.id());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id) {
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }

}