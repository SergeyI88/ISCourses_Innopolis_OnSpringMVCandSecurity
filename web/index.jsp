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
    <link rel="stylesheet" href="main.css">
    <link rel="stylesheet" href="forms.css">

</head>
<body class="container">
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
<%=request.getAttribute("messageFromReg") != null ? request.getAttribute("messageFromReg") : "" %>
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
<div class="center">Main Page</div>
<div class="right-column">right</div>
<div class="basement">basement</div>
</body>
</html>
