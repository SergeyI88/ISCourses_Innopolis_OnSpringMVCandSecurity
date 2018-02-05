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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/forms.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var showMore = function (a) {

            (document.getElementById("more" + a)).removeAttribute("hidden");
            (document.getElementById("appear" + a)).setAttribute("hidden", "hidden");
            (document.getElementById("disappear" + a)).removeAttribute("hidden");
        };
        var dis = function (a) {
            (document.getElementById("more" + a)).setAttribute("hidden", "hidden");
            (document.getElementById("appear" + a)).removeAttribute("hidden");
            (document.getElementById("disappear" + a)).setAttribute("hidden", "hidden");
        };
    </script>
</head>
<body>
<div class="header">
    <% if (session.getAttribute("name") != null) {%>
    <div class="form-container">
        <p align="center">${name} ${last_name}
        </p>
        <div class="submit-container">
            <form method="post" action="/dev/inner/LogOut"><input type="submit" value="LogOut"></form>
        </div>
    </div>
    <%} else {%>
    <div class="form-container">
        <form action="/dev/login">
            <div class="submit-container">
                <input class="submit-button" type="submit" value="Login"/><br/>
            </div>
        </form>

        <div class="submit-container">
            <form action="/dev/reg">
                <input class="submit-button" type="submit" value="Logup"/>
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
        <li><a href="/dev/top">Топ-10 курсов</a></li>
        <li><a href="dev/news">Новости</a></li>
        <li><a href="/dev/about">о нас</a></li>
    </ul>
</div>
<div class="center">
    <%
        List<Course> courses = (List<Course>) request.getAttribute("courses");
        for (int i = 0; i < courses.size(); i++) {%>
    <%--<div class="course">--%>
    <h2><%= courses.get(i).
            getName()%>
    </h2>
    <input id="disappear<%=courses.get(i).getId()%>" hidden type="button" onclick="dis(<%=courses.get(i).getId()%>)" value="disappear">
    <input id="appear<%=courses.get(i).getId()%>" type="button" onclick="showMore(<%=courses.get(i).getId()%>)" value="more info">
    <div id="more<%=courses.get(i).getId()%>" hidden><%= "Рейтинг: " + courses.get(i).getRating()%>
    <br>
    <%="Статус курса:" + courses.get(i).getStatusOfCourse()%>
    <br>
    <%=" Описание: " + courses.get(i).getDescription()%><br>
        <form action="/dev/reviews" method="post">
            <input type="hidden" value="<%=courses.get(i).getId()%>" name="id_course">
            <input type="submit" value="look reviews">
        </form>
    </div>
    <%
        if (session.getAttribute("user") != null) {
    %>
    <%
        if (courses.get(i).getId_status_of_course() == 2) {
    %>
    <form action="/dev/buy_course.jsp" method="post">
        <input type="hidden" value="<%=courses.get(i).getId()%>" name="id_course">
        <input type="submit" value="buy" name="buy">
    </form>
    <%
    } else {
    %>
    <form action="/dev/inner/task" method="post">
        <input type="hidden" value="<%=courses.get(i).getId()%>" name="id_course">
        <input type="submit" value="Begin course" name="start">
    </form>

    <%--</div>--%>
    <%
                }
            }
        }
    %>
</div>
<div class="basement">basement</div>
</body>
</html>
