<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table style="border: 1px solid black">
    <tr>
        <th>Время</th>
        <th>Что ели</th>
        <th>Калорийность</th>
    </tr>

    <c:forEach var="meal" items="${meals}">
        <tr style="background-color:${meal.excess ? 'red' : 'white'}">
            <td>${meal.dateTime.format(mealTimeFormatter)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>