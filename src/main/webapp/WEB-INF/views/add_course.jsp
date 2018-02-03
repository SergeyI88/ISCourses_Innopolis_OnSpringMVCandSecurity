<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 29.01.2018
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление нового курса!</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/task.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/forms.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/resources/js/jquery.min.js" type="text/javascript"></script>
    <script type="text/javascript">
        var tasks = [];
        var resAfterAnswer;

        function checkAnswerTask(taskT) {
            $.ajax({
                type: 'POST',
                url: "/dev/inner/checktask",
                contentType: 'application/json;',
                data: JSON.stringify(taskT),
                async: false,
                success: function (result) {
                    alert(result.result);
                    resAfterAnswer = result.result;
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        }

        var acceptT = function () {
            var task = {};
            var desc = $('#descT').val();
            var name = $('#nameT').val();
            var needD = $('#need').val();

            if (desc.length < 50) {
                alert("Маленькое описание, от 50 символов");
                return
            }
            task["desc"] = desc;
            if (name.length < 5) {
                alert("Маленькое имя, от 5 символов");
                return
            }
            task["name"] = name;
            if (needD != 1 && needD != 2 && needD != 3 && needD != 4) {
                alert("Неправильно указан ответ на вопрос");
                return
            }
            task["task"] = needD;
            task["answer"] = $('#taskT').val();
            checkAnswerTask(task);
            if (!resAfterAnswer) {
                alert("Ответы не прошли тестирование!");
                return;
            }
            checkOnUniqueTask(task);
        };

        var checkOnUniqueTask = function (outTask) {
            for (var i = 0 ; i < tasks.length; i++) {
                if (outTask["name"] === tasks[i]["name"] || outTask["desc"] === tasks[i]["desc"] || outTask["answer"] === tasks[i]["answer"]) {
                    alert("Задание не уникально");
                    return;
                }
            }
            tasks.push(outTask);
            (document.getElementById('count')).removeAttribute("hidden");
            (document.getElementById('count')).setAttribute("value", tasks.length);
            if (tasks.length >= 5) {
                (document.getElementById("final")).removeAttribute("hidden");
            }
        };

        var send = function () {
            if ($('#nameC').val() < 5) {
                alert("Имя курса пустое");
            }
            if ($('#descC').val() < 10) {
                alert("Описание минимум 10 символов");
            }
            var course = {};
            course["taskA"] = tasks;
            course["name"] = $('#nameC').val();
            course["desc"] = $('#descC').val();
            $.ajax({
                type: 'POST',
                url: "/dev/inner/newcourse",
                contentType: 'application/json;',
                data: JSON.stringify(course),
                async: false,
                success: function (result) {
                    if (result.result) {
                        alert("курс добавлен на рассмотрении")
                    }
                    window.location.href='/dev/inner/pr';
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    $('#response').html(JSON.stringify(jqXHR))
                }
            });
        };





        var changeDisabledOn = function () {
            var inp = document.getElementsByName('c');
            for (var i = 0; i < inp.length; i++) {
                inp[i].removeAttribute("disabled");
            }
        };
        var changeHiddenOn = function () {
            var inp = document.getElementsByName('h');
            for (var i = 0; i < inp.length; i++) {
                inp[i].removeAttribute("hidden");
            }
        };
        var changeDisabledOff = function () {
            var inp = document.getElementsByName('c');
            for (var i = 0; i < inp.length; i++) {
                inp[i].removeAttribute("disabled");
            }
        };
        var changeHiddenOff = function () {
            var inp = document.getElementsByName('h');
            for (var i = 0; i < inp.length; i++) {
                inp[i].removeAttribute("hidden");
            }
        };

    </script>
</head>
<body id="container">
<h3>Чтобы добавить курс вам надо придерживаться строгих правил:</h3>
<ol>
    <li>Название курса должно быть уникальным.</li>
    <li>Курс должен содержать описание.</li>
    <li>Курс должен иметь как минимум 5 глав.</li>
    <li>К каждой главе должно быть задание для усвоения материала.</li>
    <li>Каждое задание должно содержать ответ и 4 варианта на выбор .</li>
</ol>
<p>Вы согласны с правилами? </p><input type="button" value="согласен" onclick="changeDisabledOff()">
<div id="course">
    Количество заданий готовых всего:
    <input id="count" hidden type="button" value="">
    <br>

    Название курса:<input disabled="disabled" type="text" name="c" id="nameC"><br>

    Описание<input disabled="disabled" type="text" name="c" id="descC"><br>


    Добавить задание:<input disabled type="button" name="c" onclick="changeHiddenOff()"><br>


    <div hidden name="h">
        Имя задания:<input hidden="hidden" type="text" name="h" id="nameT"><br>
    </div>

    <div hidden name="h">
        Описание:<textarea class="bigText" hidden="hidden" type="text" name="h" id="descT"
                           placeholder="Здесь должна быть размещена вся учебная информация и задание в конце"></textarea><br>
    </div>

    <div hidden name="h">
        Ответ:<input  hidden="hidden" type="text" name="h" id="need"
                          placeholder="правильный ответ указать в цифрах от 1 до 4"/><br>
    </div>
    <div hidden name="h">
        Варианты ответов:<textarea class="bigText" hidden="hidden" type="text" name="h" id="taskT"
                                   placeholder="Варианты ответов должны быть разделены точкой с запятой
а ответ который правильный, должен быть помечен '@'"></textarea><br>
    </div>
    <form id="final" hidden>
        <input type="button" value="Отправить курс модератору" onclick="send()">
    </form>


    <input hidden="hidden" type="button" name="h" id="accept" onclick="acceptT()" value="Принять"><br>

</div>
</body>
</html>
