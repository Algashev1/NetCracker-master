<%@page import="java.util.List"%>
<%@page import="pac.logic.Task"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pac.Factory"%>
<%@page import="org.hibernate.mapping.Collection"%>
<%@page import="java.util.logging.FileHandler"%>
<%@page import="org.apache.log4j.Logger" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Logger log = Logger.getLogger("index.jsp");
    //List<Task> list = new ArrayList<>();
    //list = Factory.getInstance().getTaskDAO().parentTask(15);
    //Factory.getInstance().getTaskDAO().returnTask("5");
    log.info("загрузка index.jsp");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Авторизация</title>

        <link rel="stylesheet" href="style.css">

        <script>
            function display(id) {
                var el = document.getElementById(id);
                if (el.style.display === "none") {
                    el.style.display = "block";
                } else {
                    el.style.display = "none";
                }
            }
        </script>

    </head>
    <body>
        <div class="cont">
            <table class="btns">
                <tr>
                    <td><a class="btn" name="" onclick="display('auth'); display('reg'); return true;">Авторизоваться </a></td>
                    <td><a class="btn" name="" onclick="display('reg'); display('auth'); return true;"> Зарегистрироваться</a></td>
                </tr>
            </table>
            <div id="auth" style="display: block;">
                <h3>Авторизуйтесь пожалуйста, для входа в систему!</h3>
                <form action="auth.jsp" method="post" >
                    <table>
                        <tr>
                            <td>Логин: </td>
                            <td><input type="text" name="login" placeholder="Введите логин"/></td>
                        </tr>
                        <tr>
                            <td>Пароль: </td>
                            <td><input type="password" name="password" placeholder="Введите пароль"/></td>
                        </tr>
                    </table>
                    <input type="submit" class="sub-btn" value="Вход" />
                </form>
            </div>
            <div id="reg" style="display: none;">
                <h3>Для регистрации заполните форму ниже и нажмите на кнопку "Зарегистрироваться"</h3>
                <form action="registration.jsp" method="post">
                    <table>
                        <tr>
                            <td>Имя:</td>
                            <td><input type="text" name="username" placeholder="Введите имя"/></td>
                        </tr>
                        <tr>
                            <td>Логин:</td>
                            <td><input type="text" name="login" placeholder="Введите логин"/></td>
                        </tr>
                        <tr>
                            <td>Пароль:</td>
                            <td><input type="password" name="password" placeholder="Введите пароль"/></td>
                        </tr>
                    </table>
                    <input type="submit" class="sub-btn" value="Регистрация"/>
                </form>
            </div>
        </div>
    </body>
</html>
