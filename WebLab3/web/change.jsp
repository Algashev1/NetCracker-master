<%-- 
    Document   : add
    Created on : 16.03.2017, 15:48:09
    Author     : 1
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="pac.Task"%>
<%@page import="pac.TaskList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="pac.*"%>

<%
    Task task = new Task();
    String id = "";
    String url = request.getQueryString();
    String message = "";

    if (url != null) {
        if (url.contains("=")) {
            String perArr[] = url.split("=");
            if (perArr.length > 1 && perArr[0].equals("id")) {
                session.setAttribute("taskId", perArr[1]);
            }
        }
    }

    id = session.getAttribute("taskId").toString();

    if (request.getParameter("name") != null && request.getParameter("time") != null) {
        TaskList list = new TaskList(Integer.parseInt(session.getAttribute("id").toString()));
        message = list.updateTask(Integer.parseInt(id), request.getParameter("name"), request.getParameter("description"), request.getParameter("time"), request.getParameter("contacts"));
    }

    OperationsTasks.getTask(Integer.parseInt(id));
    OperationsTasks.taskXSLT2();
    if (!id.equals("")) {
        ArrayList<String> list = OperationsTasks.returnTask(id);
        if (list != null) {
            task.setId(list.get(0));
            task.setName(list.get(1));
            task.setDescription(list.get(2));
            task.setTime(list.get(3));
            task.setContacts(list.get(4));
            task.setUserId(list.get(5));
        }
    } else {
        response.sendRedirect("http://localhost:8084/WebLab3/tasks.jsp");
    }

%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Изменение задачи</title>
        <link rel="stylesheet" href="style.css">
        <link rel="import" href="test2.html">
    </head>
    <body>
        <script>
            var test2 = document.querySelector('link[rel="import"]').import;
            var div = test2.querySelector('.test2');
        </script>
        <div class="cont">
            <a class="btn" href="tasks.jsp?taskid=<%=session.getAttribute("taskId")%>">Назад</a>
            <h3>Заполните форму и нажмите на кнопку "Изменить"</h3>
            <div class="message"><%=message%></div>
            <form action="change.jsp" method="get">
                <table>
                    <tr>
                        <td>Имя:</td>
                        <td><input type="text" name="name2" value="<%=task.getName()%>"/></td>
                    </tr>
                    <tr>
                        <td>Описание:</td>
                        <td><textarea name="description2"><%=task.getDescription()%></textarea></td>
                    </tr>
                    <tr>
                        <td>Дата/Время:</td>
                        <td><input type="datetime" name="time2" value="<%=task.getTime()%>"/></td>
                    </tr>
                    <tr>
                        <td>Контакты:</td>
                        <td><textarea name="contacts2"><%=task.getContacts()%></textarea></td>
                    </tr>
                </table>
                <input type="submit" value="Изменить" />
                <script>
                    document.body.appendChild(div.cloneNode(true));
                </script>
            </form>
        </div>
    </body>
</html>
