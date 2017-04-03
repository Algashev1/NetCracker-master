<%-- 
    Document   : registration
    Created on : 09.03.2017, 19:54:02
    Author     : 1
--%>

<%@page import="java.sql.*"%>
<%@page import="pac.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Регистрация</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <div class="cont">
            <%
                String answer = "";
                String name = "";
                String login = "";
                String password = "";

                if (request.getParameter("username") != null) {
                    name = request.getParameter("username");
                }

                if (request.getParameter("login") != null) {
                    login = request.getParameter("login");
                }

                if (request.getParameter("password") != null) {
                    password = request.getParameter("password");
                }

                if (name != "" && login != "" && password != "") {
                    answer = OperationsTasks.regClient(name, login, password);
                    out.println("<div class=\"message\">" + answer + "</div><a href=\"index.jsp\">Перейдите по ссылке на предыдущую страницу</a> ");
                } else {
                    answer = "Регистрация не прошла! Попробуйте снова!";
                    out.println("<div class=\"message\">"+ answer + "</div><a href=\"index.jsp\">Перейдите по ссылке на предыдущую страницу</a> ");
                }
            %>
        </div>
    </body>
</html>
