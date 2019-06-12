<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Meals</title>
    <link rel="stylesheet" href="css/style.css">
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
            <th colspan="2">Actions</th>
        </tr>
        <c:forEach var="meal" items="${meals}">
            <tr style="background-color:${meal.excess ? 'red' : 'green'}">
                <td>${meal.dateTime.format(mealTimeFormatter)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&action=edit">Edit</a></td>
                <td><a href="meals?id=${meal.id}&action=delete">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>