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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">

        var RestPut = function (login, password, name, last) {
            if (password.length < 8) {
                alert("Короткий пароль от 8 символов")
            } else if (login.length < 1) {
                alert("Пустой логин")
            } else if (password.length < 1) {
                alert("Пустой пароль")
            } else if (name.length < 1) {
                alert("Заполните имя")
            } else if (last.length < 1) {
                alert("Заполните второе имя")
            } else {
                var userOut = {};
                userOut["login"] = login;
                userOut["pass"] = password;
                userOut["name"] = name;
                userOut["last_name"] = last;
                $.ajax({
                    type: 'POST',
                    url: "/dev/reg",
                    contentType: 'application/json;',
                    data: JSON.stringify(userOut),
                    success: function (result) {
                        $('#response').html(JSON.stringify(result))
                        if (result === "succes") {
                            return window.location.href = 'index.jsp';
                        } else {
                            alert("Такой логин уже есть!")
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        $('#response').html(JSON.stringify(jqXHR))
                    }
                });
            }
        };
    </script>
</head>
<body>
<form method="post">
    Log In:<input type="text" id="login"/>
    Password:<input type="password" id="pass"/>
    name:<input type="text" id="name"/>
    last name:<input type="text" id="last"/>
    <input type="button" onclick="RestPut($('#login').val(), $('#pass').val(), $('#name').val(), $('#last').val())">
    <%=request.getAttribute("messageFromReg") != null ? request.getAttribute("messageFromReg") : "" %>
</form>
</body>
</html>
