<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <link type="text/css"
          href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet"/>
    <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
    <title>Add new meal</title>
</head>
<body>
<script>
    $(function () {
        $('input[name=dateTime]').datepicker();
    });
</script>

<form method="POST" action='meals' name="frmAddMeal">
    Meal ID : <input type="text" readonly="readonly" name="mealId"
                     value="<c:out value="${meal.mealId}" />"/> <br/>
    Date and Time : <input type="text" name="dateTime"
                           value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${meal.dateTime}" />"/> <br/>
    Description : <input type="text" name="description"
                         value="<c:out value="${meal.description}" />"/> <br/>
    Calories : <input type="text" name="calories"
                      value="<c:out value="${meal.calories}" />"/> <br/>
    <input type="submit" value="Submit"/>
</form>
</body>
</html>
