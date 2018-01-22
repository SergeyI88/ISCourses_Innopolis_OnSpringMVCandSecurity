<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 21.01.2018
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="login.css">

</head>
<body class="container">
<form class="form-container" method="post" action="/dev/login">
    <div class="form-title"><h2>Sign up</h2></div>
    <div class="form-title">Login</div>
    <input class="form-field" type="text" name="login" /><br />
    <div class="form-title">Password</div>
    <input class="form-field" type="text" name="password" /><br />
    <div class="submit-container">
        <input class="submit-button" type="submit" value="Submit" />
    </div>
    <%if (request.getAttribute("error") != null) {%>
    <p><%=request.getAttribute("error")%>
    </p>
    <%}%>
</form>
</body>
</html>
