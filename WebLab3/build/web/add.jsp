<%-- 
    Document   : add
    Created on : 16.03.2017, 15:48:09
    Author     : 1
--%>

<%@page import="pac.TaskList"%>
<%@page import="org.apache.log4j.Logger" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    Logger log = Logger.getLogger("add.jsp");
    String message = "";
    String id = "";
    String url = request.getQueryString();

    log.info("загрузка add.jsp");
    if (url != null) {
        if (url.contains("=")) {
            String perArr[] = url.split("=");
            if (perArr.length > 1) {
                id = perArr[1];
            }
        }
    }

    if ((url != null) && (id.equals("")) && (request.getParameter("name") != null) && (request.getParameter("time") != null)) {
        TaskList list = new TaskList(Integer.parseInt(session.getAttribute("id").toString()));
        message = list.addTask(request.getParameter("name"), request.getParameter("description"), request.getParameter("time"), request.getParameter("contacts"));
    } else if ((url != null) && (request.getParameter("name") != null) && (request.getParameter("time") != null)) {
        TaskList list = new TaskList(Integer.parseInt(session.getAttribute("id").toString()));
        message = list.addTaskChild(id, request.getParameter("name"), request.getParameter("description"), request.getParameter("time"), request.getParameter("contacts"));
    }
%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Добавление задачи</title>
        <link rel="stylesheet" href="style.css">
        <link rel="stylesheet" type="text/css" href="js/jquery.datetimepicker.css"/>
        <script src="js/jquery.js"></script>
        <script src="js/jquery.datetimepicker.full.js"></script>
    </head>
    <body>
        <div class="cont">
            <a class="btn" href="tasks.jsp">Назад</a>
            <h3>Заполните форму и нажмите на кнопку "Добавить"</h3>
            <div class="message"><%=message%></div>
            <form action="add.jsp?id=<%=id%>" method="post">
                <table>
                    <tr>
                        <td>Имя:</td>
                        <td><input type="text" name="name" /></td>
                    </tr>
                    <tr>
                        <td>Описание:</td>
                        <td><textarea name="description"></textarea></td>
                    </tr>
                    <tr>
                        <td>Дата/Время:</td>
                        <td>   
                            <input type="text" name="time" id="datetimepicker"/>  
                            <script>
                                $('#datetimepicker').datetimepicker();
                            </script>
                        </td>
                    </tr>
                    <tr>
                        <td>Контакты:</td>
                        <td><textarea name="contacts"></textarea></td>
                    </tr>
                </table>
                <input type="submit" value="Добавить" />
            </form>
        </div>
    </body>
</html>
