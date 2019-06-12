<%--
  Created by IntelliJ IDEA.
  User: chemelson
  Date: 12.06.19
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add new meal</h2>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <h1>Date and Time</h1>
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <input type="datetime-local" name="mealdatetime"
                   value="${meal.dateTime.format(isoTimeFormatter)}">
        </dl>
        <dl>
            <input type="text" name="mealdescription" value="${meal.description}">
        </dl>
        <dl>
            <input type="text" name="mealcalories" value="${meal.calories}">
        </dl>
        <button type="submit">Save</button>
    </form>
</body>
</html>
