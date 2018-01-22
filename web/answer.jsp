<%@ page import="services.ServiceForWorkUserAndCourse" %>
<%@ page import="db.pojo.TaskCourse" %>
<%@ page import="services.ServiceEditPersonalDataAndGet" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 18.01.2018
  Time: 17:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="main.css" rel="stylesheet">
    <link href="forms.css" rel="stylesheet">
</head>
<body>
<div class="header">

    <div class="form-container">
        <p align="center"><%= session.getAttribute("name").toString()
        %>  <%= session.getAttribute("last_name").toString()%>
        </p>
        <div class="submit-container">
            <form method="post" action="/dev/inner/LogOut"><input type="submit" value="LogOut" ></form>
        </div>
    </div>
    <p align="center"> Добро пожаловать в ...</p>
</div>
<div class="left-column">left
    <ul title="Меню" type="disc">
        <p>Меню</p>
        <li><a href="/dev/courses">Все курсы</a></li>
        <li><a href="">Справочники</a></li>
        <li><a href="">еще</a></li>
        <li><a href="">и еще что-то</a></li>

    </ul>
</div>
<div class="center">
    <form action="/dev/inner/task" method="post">
        <% if ((Boolean) request.getAttribute("answerOnPage")) { %>
        <h3>Вы молодец правильно</h3>
        <input type="hidden" value="<%=request.getAttribute("id_course")%>" name="id_course">
        <input type="submit" value="Перейти к следующему заданию"/>
    </form>
    <%} else {%>
    <h3>Неправильно</h3>
    <form action="/dev/inner/task" method="post">
        <input type="hidden" value="<%=request.getAttribute("id_course")%>" name="id_course">
        <input type="submit" value="Вернуться"/>
    </form>
    <%}%>
</div>
<div class="right-column">right</div>
<div class="basement">basement</div>
</body>
</html>
