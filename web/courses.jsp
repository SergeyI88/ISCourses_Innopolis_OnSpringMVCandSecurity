<%@ page import="db.pojo.Course" %>
<%@ page import="java.util.*" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 18.01.2018
  Time: 10:15
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
    <% if (session.getAttribute("name") != null) {%>
    <div class="form-container">
        <p align="center"><%= session.getAttribute("name").toString()
        %>  <%= session.getAttribute("last_name").toString()%>
        </p>
        <div class="submit-container">
            <form method="post" action="/dev/inner/LogOut"><input type="submit" value="LogOut"></form>
        </div>
    </div>
    <%} else {%>
    <div class="form-container">
        <form action="/dev/login.jsp">
            <div class="submit-container">
                <input class="submit-button" type="submit" value="Login"/><br/>
            </div>
        </form>

        <div class="submit-container">
            <form action="/dev/Registr.jsp">
                <input class="submit-button" type="submit" value="Logup" onclick="window.location='/Registr.jsp';">
            </form>
        </div>
        <%}%>
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
    <%
        List<Course> list = (List<Course>) request.getAttribute("courses");
        for (Course course : list) {%>
    <%--<div class="course">--%>
    <h2><%= course.
            getName()%>
    </h2>
    <%=course.getDescription()%>
    <%="Статус курса:" + course.getStatusOfCourse()%>
    <%
        if (session.getAttribute("id_user") != null) {
    %>
    <%
        if (course.getId_status_of_course() == 2) {
    %>
    <form action="" method="post">
        <input type="hidden" value="<%=course.getId()%>" name="id_course">
        <input type="submit" value="buy" name="buy">
    </form>
    <%
    } else {
    %>
    <form action="/dev/inner/task" method="post">
        <input type="hidden" value="<%=course.getId()%>" name="id_course">
        <input type="submit" value="Begin course" name="start">
    </form>

    <%--</div>--%>
    <%
                }
            }
        }
    %>
</div>
<div class="right-column">right</div>
<div class="basement">basement</div>
</body>
</html>
