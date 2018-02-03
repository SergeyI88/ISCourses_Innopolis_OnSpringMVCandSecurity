<%@ page import="db.pojo.Course" %>
<%@ page import="java.util.*"%>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 08.01.2018
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <%--<meta charset="UTF-8">--%>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/forms.css"/>
</head>
<body class="container">
<div class="header">
    <% if (session.getAttribute("name") != null) {%>
    <div class="form-container">
        <p align="center">${name} ${last_name}
        </p>
        <div class="submit-container">
            <form method="post" action="/dev/inner/LogOut"><input type="submit" value="LogOut"></form>
        </div>
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
            <input class="submit-button" type="submit" value="Logup"
                   onclick="window.location='/WEB-INF/views/Registr.jsp';">
        </form>
    </div>
    <%}%>


</div>
<%=request.getAttribute("messageFromReg") != null ? request.getAttribute("messageFromReg") : "" %>
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
<div class="center">Main Page
    <%if (request.getAttribute("top") != null) { %>
    <%for (Course course : (List<Course>)request.getAttribute("top")) {%>
    <h2><%= course.
            getName()%>
    </h2>

    <div><%= "Рейтинг: " + course.getRating()%></div>
    <hr><br>
    <%
            }
        }
    %>
</div>
<div class="right-column">right</div>
<div class="basement">basement</div>
</body>
</html>
