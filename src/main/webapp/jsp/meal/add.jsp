<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <hr>
    <h2>Add new meal</h2>
    <form method="post" action="meals" enctype="application/x-www-form-urlencoded">
        <h1>Date and Time</h1>
        <dl>
            <input type="datetime-local" name="mealdatetime">
        </dl>
        <dl>
            <input type="text" name="mealdescription">
        </dl>
        <dl>
            <input type="text" name="mealcalories" value="${meal.calories}">
        </dl>
        <button type="submit">Save</button>
    </form>
</body>
</html>
