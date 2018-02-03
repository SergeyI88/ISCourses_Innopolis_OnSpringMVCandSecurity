<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <%--<meta charset="UTF-8">--%>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/forms.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var changeName = function (name) {

            if (name.length < 3) {
                alert("Слишком маленькое имя")
            } else {
                var userN = {};
                userN["name"] = name;

                $.ajax({
                    type: 'POST',
                    url: "/dev/inner/editname",
                    contentType: 'application/json;',
                    data: JSON.stringify(userN),
                    success: function (result) {
                        alert("имя изменено");
                        return window.location.href = '/dev/inner/pr';
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("сожалеем что-то пошло не так");
                    }
                });
            }

        };
        var changeLast = function (last) {

            if (last.length < 3) {
                alert("Слишком маленькая фамилия")
            } else {
                var userN = {};
                userN["last_name"] = last;

                $.ajax({
                    type: 'POST',
                    url: "/dev/inner/editlast",
                    contentType: 'application/json;',
                    data: JSON.stringify(userN),
                    success: function (result) {
                        alert("имя изменено");
                        return window.location.href = '/dev/inner/pr';
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("сожалеем что-то пошло не так");
                    }
                });
            }

        };
        var changePwd = function (newPwd, old, old2) {
            if (newPwd.length < 8) {
                alert("Короткий пароль от 8 символов")
            } else if (newPwd.length < 1) {
                alert("Пустой пароль")
            } else if (old !== old2) {
                alert("Пароли не совпадают")
            } else if (old < 1) {
                alert("Старый пароль пуст")
            } else {
                var userN = {};
                userN["password"] = newPwd;
                userN["oldPwd"] = old;
                userN["oldPwd2"] = old2;

                $.ajax({
                    type: 'POST',
                    url: "/dev/inner/editpwd",
                    contentType: 'application/json;',
                    data: JSON.stringify(userN),
                    success: function (result) {
                        if (result === "yes") {
                            alert("Пароль изменен");
                            return window.location.href = '/dev/inner/pr';
                        } else {
                            alert(result);
                        }
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("Извините что-то пошло не так!!!")
                    }
                });
            }

        };
        var show = function () {

                $.ajax({

                        type: 'POST',
                        url: "/dev/inner/topu",
                        success: function (result) {
                            for (var i = 0; i < result.userList.length; i++) {
                                alert(result.level)
                            }
                        },
                        error:

                            function (jqXHR, textStatus, errorThrown) {
                                alert("Извините что-то пошло не так!!!")
                            }
                    }
                )
                ;
            }
        ;
    </script>
</head>
<body class="container">
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
        <li><a href="/dev/top">Топ-10 курсов</a></li>
        <li><a href="dev/news">Новости</a></li>
        <li><a href="/dev/about">о нас</a></li>

    </ul>
</div>
<div class="center">
    <div align="center">Личный кабинет
        <ul title="Меню" type="disc">
            <p>Функции</p>
            <li><a href="/dev/inner/addcourse">Добавить курс</a></li>
            <li>Изменить Имя: <input type="text" id="name">:<input type="button" value="Изменить"
                                                                   onclick="changeName($('#name').val())"></li>
            <li>Изменить Фамилию: <input type="text" id="last">:<input type="button" value="Изменить"
                                                                       onclick="changeLast($('#last').val())"></li>
            <li>Изменить Пароль: <input type="text" id="newpwd"></li>
            <li>Старый пароль:<input type="text" id="oldpwd"></li>
            <li>еще раз:<input type="text" id="oldpwd2">:<br>
                <input type="button" value="Изменить" onclick="changePwd($('#newpwd').val()
            ,$('#oldpwd').val()
            ,$('#oldpwd2').val())"></li>
            <li><input type="button" onclick="show()" value="Посмотреть рейтинг учащихся"></li>
            <%if(request.getAttribute("admin") != null) {%>
            <li><form action="/dev/inner/shownewcourses"><input type="submit" value="Посмотреть заявки"></form></li>
            <%}%>
        </ul>
    </div>
</div>
<div class="right-column">right</div>
<div class="basement">basement</div>
</body>
</html>
