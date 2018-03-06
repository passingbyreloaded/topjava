<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h2>Meals</h2>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Date and Time</th>
        <th>Description</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="${meal.exceed?'color:red':'color:green'}">
            <td>${meal.mealId}</td>
            <td>
                <javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm"
                                             var="parsedDateTime"/>
                <javatime:format pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}"/>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.mealId}"/>">Update</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.mealId}"/>">Delete</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>
