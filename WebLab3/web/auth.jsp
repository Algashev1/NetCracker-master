<%-- 
    Document   : auth
    Created on : 09.03.2017, 18:43:37
    Author     : 1
--%>

<%@page import="pac.Factory"%>
<%@page import="pac.logic.Client"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.*"%>
<%@page import="org.apache.log4j.Logger" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    Logger log = Logger.getLogger("auth.jsp");
    log.info("загрузка auth.jsp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Авторизация</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
        <div class="cont">
            <%
                String login = "";
                String password = "";
                String answer = "";

                if (request.getParameter("login") != null) {
                    login = request.getParameter("login");
                }

                if (request.getParameter("password") != null) {
                    password = request.getParameter("password");
                }

                if (login != "" && password != "") {
                    try {
                        Client cl = Factory.getInstance().getClientDAO().authClient(login, password);
                        HttpSession s = request.getSession(true);
                        s.setAttribute("id", new String(cl.getId()));
                        s.setAttribute("name", new String(cl.getName()));
                        s.setAttribute("login", new String(cl.getLogin()));
                        s.setAttribute("password", new String(cl.getPassword()));
                        answer = "";
                    } catch (Exception e) {
                        answer = "Регистрация не прошла! Исключение:" + e.getMessage();
                    }
                }
                if (answer != "") {
                    out.println("<div class=\"message\">" + answer + "</div><a href=\"index.jsp\">Перейдите по ссылке на предыдущую страницу</a> ");
                } else {
                    response.sendRedirect("http://localhost:8084/WebLab3/tasks.jsp");
                }
            %>
        </div>
    </body>
</html>
