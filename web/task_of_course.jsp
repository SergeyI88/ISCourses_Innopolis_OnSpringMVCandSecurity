<%@ page import="db.pojo.Course" %>
<%@ page import="java.util.*" %>
<%@ page import="services.ServiceForWorkUserAndCourse" %>
<%@ page import="db.pojo.TaskCourse" %>
<%@ page import="db.pojo.Comment" %>
<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 18.01.2018
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="main.css" rel="stylesheet">
    <link href="forms.css" rel="stylesheet">
    <link href="task.css" rel="stylesheet">
</head>
<body>
<div class="header">
    <div class="form-container">
        <p align="center"><%= session.getAttribute("name").toString()
        %>  <%= session.getAttribute("last_name").toString()%>
        </p>
        <div class="submit-container">
            <form method="post" action="/dev/inner/LogOut"><input type="submit" value="LogOut"></form>
        </div>
    </div>


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
<%
    TaskCourse taskCourse = (TaskCourse) request.getAttribute("course");
    String name = taskCourse.getName();
    String desc = taskCourse.getDescription();
    String answer = taskCourse.getAnswer();
    String[] task = taskCourse.getTask().split("\n");
    request.setAttribute("id_course", taskCourse.getId_course());
%>
<h1><%=name%>
</h1>
<p><%=desc%>
</p>
<h2>Задание</h2>
<p><%=answer%>
</p>
<form action="/dev/inner/answer" method="post">
    <input type="hidden" value="<%=request.getAttribute("id_course")%>" name="id_course">
    <%=task[0]%><input type="radio" value="1" name="1">
    <%=task[1]%><input type="radio" value="2" name="2">
    <%=task[2]%><input type="radio" value="3" name="3">
    <%=task[3]%><input type="radio" value="4" name="4">
    <input type="submit" value="send">
</form>
<%
    List<Comment> list = (List<Comment>) request.getAttribute("comments");
    for (Comment comment : list) {%>
<h5><%= comment.getLogin()  %>

    <%=comment.getDate()%>
</h5>
<%--<div class="comment">--%>
<%="Комментарии:"%><br/><%= comment.getComment()%>
<%--</div>--%>
<%
    }
%>
<form action="/dev/inner/comment" method="post">
    <%=request.getAttribute("id_task")%>
    <input type="hidden" value="<%=request.getAttribute("id_task")%>" name="id_task">
    <input type="hidden" value="<%=request.getAttribute("id_course")%>" name="id_course">
    <input type="text" placeholder="comment" name="comment">
    <input type="submit" value="оставить комментарий">
</form>

<div class="right-column">right</div>


<div class="basement">basement</div>
</body>
</html>
