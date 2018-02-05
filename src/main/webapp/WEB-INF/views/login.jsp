
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/login.css"/>

</head>

<body class="container">
<div class="title"><h2>Войдите и получите бесценный опыт.</h2></div>
<form class="form-container" method="post" action="/dev/login">
    <%--<input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}">--%>
    <div class="form-title"><h2>Sign up</h2></div>
    <div class="form-title">Login</div>
    <input class="form-field" type="text" name="username" id="username" value="login"/><br/>
    <div class="form-title">Password</div>
    <input class="form-field" type="text" name="userpass" id="userpass" value="pass"/><br/>
    <div class="submit-container">
        <input class="submit-button" type="submit" value="Submit"/>
    </div>
    <%if (request.getParameter("error") != null) {%>
    <p><%=request.getParameter("error")%>
    </p>
    <%}%>
</form>
</body>
</html>
