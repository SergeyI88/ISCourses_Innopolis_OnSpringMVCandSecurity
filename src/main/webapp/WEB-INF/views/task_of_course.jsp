<%@ page import="java.util.*" %>
<%@ page import="db.pojo.TaskCourse" %>
<%@ page import="db.pojo.Comment" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/task.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/forms.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var check = function () {

            $('<a>', {href: 'http://google.com', text: 'Гугли!'}).appendTo('#wrapper');
            var count = 0;
            var inp = document.getElementsByName('a');
            for (var i = 0; i < inp.length; i++) {
                if (inp[i].type == "radio" && inp[i].checked) {
                    count++;
                    sendAnswer(inp[i].value);
                }
            }
            if (count === 0) {
                alert("нет ответа")
            }
        };
        var sendAnswer = function (a) {
            var response = {};
            response["him"] = a;
            response["answer"] = $('#answer').val();
            response["id_course"] = $('#id_course').val();

            $.ajax({
                type: 'POST',
                url: "/dev/inner/answer",
                contentType: 'application/json;',
                data: JSON.stringify(response),
                success: function (result) {
                    if (result.right) {
                        if(result.rank !== null) {
                            alert("Поздравляем вы ответили на 50 вопросов, у вас новый ранг теперь вы: " + result.rank)
                        }
                        (document.getElementById("form_ok")).removeAttribute("hidden");
                        (document.getElementById("all")).setAttribute("hidden", "hidden")
                    } else {
                        (document.getElementById("no")).removeAttribute("hidden")
                    }
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        };
        var addComment = function (id_task, id_course, comment) {
            if (comment.length < 3) {
                alert("Слишком маленький коммент")
            } else {
                var res = {};
                res["id_task"] = id_task;
                res["id_course"] = id_course;
                res["comment"] = comment;
                $.ajax({
                    type: 'POST',
                    url: "/dev/inner/comment",
                    contentType: 'application/json;',
                    data: JSON.stringify(res),
                    success: function (result) {
                        createComment(result.date, result.comment)
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        alert("сожалеем что-то пошло не так")
                    }
                });
            }
        };
        var createComment = function (date, comment) {
            $('<div/>', {
                append: $('<h5>')
                    .add($('<span>',
                        {
                            text: 'Вы только что добавили '
                        }))
                    .add($('<span>', {
                        text: date
                    }))
                    .add($('<br>'))
                    .add($('<span>', {
                        text: comment
                    }))
                    .add($('<hr>'))
            })
                .prependTo('#comments');
        };
    </script>
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
        <li><a href="/dev/top">Топ-10 курсов</a></li>
        <li><a href="dev/news">Новости</a></li>
        <li><a href="/dev/about">о нас</a></li>
    </ul>
</div>
<div class="center">
    <%
        TaskCourse taskCourse = (TaskCourse) request.getAttribute("task_course");
        String name = taskCourse.getName();
        String desc = taskCourse.getDescription();
        String answer = taskCourse.getAnswer();
        String[] task = taskCourse.getTask().split("\n");
    %>
    <h3><%=name%>
    </h3>
    <p><%=desc%>
    </p>
    <h6>Задание</h6>
    </p>
    <form name="f" method="post">
        <input type="hidden" value="<%=request.getAttribute("id_course")%>" id="id_course">
        <input type="hidden" value="<%=answer%>" id="answer">
        <%=task[0]%><input type="radio" value="1" id="1" name="a">
        <%=task[1]%><input type="radio" value="2" id="2" name="a">
        <%=task[2]%><input type="radio" value="3" id="3" name="a">
        <%=task[3]%><input type="radio" value="4" id="4" name="a">
        <input type="button" value="send" onclick="check()" id="all"/>
    </form>
    <div id="response">
        <div hidden id="form_ok">
            <h3>Верно!!!</h3>
            <form action="/dev/inner/task" method="post">
                <input type="hidden" name="id_course" value="<%=request.getAttribute("id_course")%>">
                <input type="submit" value="next task">
            </form>
        </div>
        <span hidden id="no"><h3>Не верно!!!</h3></span>
    </div>

    <h4>"Комментарии:"</h4>
    <div id="comments">
        <%
            List<Comment> list = (List<Comment>) request.getAttribute("comments");
            for (Comment comment : list) {%>
        <div>
            <h5><span><%= comment.getLogin()  %></span><span>
                <%=comment.getDate()%> </span>
            </h5>

            <br/><%= comment.getComment()%>
        </div>
        <hr/>
    </div>
    <%
        }
    %>
    <br/>
    <form method="post">
        <input type="hidden" value="<%=request.getAttribute("id_task")%>" id="id_taskC">
        <input type="hidden" value="<%=request.getAttribute("id_course")%>" id="id_courseC">
        <input type="text" placeholder="comment" id="comment">
        <input type="button" value="оставить комментарий" onclick="addComment($('#id_taskC').val(),
    $('#id_courseC').val(), $('#comment').val())">
    </form>
</div>

<div class="basement">basement</div>
</body>
</html>
