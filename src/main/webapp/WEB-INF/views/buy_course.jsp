<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 24.01.2018
  Time: 13:59
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
"Begin buy this course!"
<form action="/dev/inner/buy">
    <input type="hidden" value="<%=request.getParameter("id_course")%>" name="id_course">
    <input type="submit" value="купить этот курс">
</form>
</body>
</html>
