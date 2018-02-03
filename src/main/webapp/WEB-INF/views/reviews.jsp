<%@ page import="db.pojo.Course" %>
<%@ page import="java.util.*" %>
<%@ page import="ajax.ReviewA" %><%--
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
            <form action="/dev/Registr">
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
        List<ReviewA> reviewAList = (List<ReviewA>) request.getAttribute("reviews");
        for (ReviewA reviewA : reviewAList) {%>
    <h3>Автор отзыва: <%=reviewA.getLogin()%></h3>
    <h3>Оценка: <%=reviewA.getId_assessement()%></h3>
    <p>Отзыв: <%=reviewA.getReview()%></p>

    <%}%>
</div>
<div class="right-column">right</div>
<div class="basement">basement</div>
</body>
</html>
