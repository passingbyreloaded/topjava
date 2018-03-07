package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMapImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealDao mealDao;
    private DateTimeFormatter formatter;

    @Override
    public void init() throws ServletException {
        mealDao = new MealDaoMapImpl();
        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");

        if (action==null){
            forward = "/meals.jsp";
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        }else if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDao.delete(mealId);
            forward = "/meals.jsp";
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else if (action.equalsIgnoreCase("edit")){
            forward = "/meal.jsp";
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDao.getById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forward = "/meals.jsp";
            request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        } else {
            forward = "/meal.jsp";
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"),formatter);
        Meal meal = new Meal(dateTime,request.getParameter("description"),Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealId");
        if(mealId == null || mealId.isEmpty())
        {
            mealDao.add(meal);
        }
        else
        {
            meal.setMealId(Integer.parseInt(mealId));
            mealDao.update(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher("/meals.jsp");
        request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000));
        view.forward(request, response);
    }
}
