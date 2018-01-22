<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 16.01.2018
  Time: 17:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/dev/registration" method="post">
    Log In:<input type="text" name="login"/>
    Password:<input type="password" name="pass"/>
    name:<input type="text" name="name"/>
    last name:<input type="text" name="last"/>
    <input type="submit">
    <%=request.getAttribute("messageFromReg") != null ? request.getAttribute("messageFromReg") : "" %>
</form>
</body>
</html>
