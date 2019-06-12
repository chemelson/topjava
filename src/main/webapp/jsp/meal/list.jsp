<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
        }
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Meals</h2>
    <a href="meals?action=add">Add new record</a>
    <hr>
    <table>
        <tr>
            <th>Date and Time</th>
            <th>Meal Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach var="meal" items="${meals}">
            <tr style="background-color:${meal.excess ? 'red' : 'green'}">
                <td>${meal.dateTime.format(mealTimeFormatter)}</td>
                <td><a href="meals?id=${meal.id}&action=view">${meal.description}</a></td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>