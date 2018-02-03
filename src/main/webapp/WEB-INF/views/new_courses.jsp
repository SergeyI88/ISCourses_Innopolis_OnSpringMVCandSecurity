<%@ page import="ajax.CourseA" %>
<%@ page import="java.util.*" %>
<%@ page import="ajax.ListTopUsers" %>
<%@ page import="db.pojo.TaskCourse" %><%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 31.01.2018
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% for (CourseA courseA : (List<CourseA>) request.getAttribute("courses")) {
%> <%=courseA.getName() + " " + courseA.getDesc()%>
<% for (TaskCourse taskCourse : courseA.getTasksC()) {%>
<%="name:" + taskCourse.getName() + "\n desc:" + taskCourse.getDescription() + "\n task:" +
        taskCourse.getTask() + "\n номер таски:" + taskCourse.getNumber_in_sub_course() + "\n ответ" + taskCourse.getAnswer()%>
<%
    }
%><><%
    }
%>

</body>
</html>
