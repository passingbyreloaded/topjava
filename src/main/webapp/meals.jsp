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
    <th>Description</th>
    <th>Date and Time</th>
    <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr style="${meal.exceed?'color:red':'color:green'}">
            <td>${meal.description}</td>
            <td>
                <javatime:parseLocalDateTime value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" />
                <javatime:format pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"  />
            </td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
