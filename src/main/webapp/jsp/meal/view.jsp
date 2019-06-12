<%--
  Created by IntelliJ IDEA.
  User: chemelson
  Date: 12.06.19
  Time: 17:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>View meal</h2>
    <p>
        Date and Time: ${meal.dateTime.format(mealTimeFormatter)}<br>
        Description: ${meal.description}<br>
        Calories: ${meal.calories}
    </p>
    <a href="meals?id=${meal.id}&action=delete">Delete</a>
    <a href="meals?id=${meal.id}&action=edit">Edit</a>
</body>
</html>
