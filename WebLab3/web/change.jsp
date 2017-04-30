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
    if (OperationsTasks.checkXMLforXSD()) {
        OperationsTasks.taskXSLT();
    }
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Изменение задачи</title>
        <link rel="stylesheet" href="style.css">
        <link rel="import" href="import.html">
    </head>
    <body>
        <div class="cont">
            <a class="btn" href="tasks.jsp?taskid=<%=session.getAttribute("taskId")%>">Назад</a>
            <h3>Заполните форму и нажмите на кнопку "Изменить"</h3>
            <div class="message"><%=message%></div>
            <form>
                <div id="container"></div>
                <script>
                    var doc = document.querySelector('link[rel="import"]').import;
                    var template = doc.querySelector('template');
                    var clone = document.importNode(template.content, true);
                    document.querySelector("#container").appendChild(clone);
                </script>
                <input type="submit" value="Изменить" />
            </form>
        </div>
    </body>
</html>
<%
%>
