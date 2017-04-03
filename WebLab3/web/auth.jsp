<%-- 
    Document   : auth
    Created on : 09.03.2017, 18:43:37
    Author     : 1
--%>

<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

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
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
                        PreparedStatement st = connection.prepareStatement("select * from Client");
                        ResultSet result = st.executeQuery();
                        while (result.next()) {
                            if (result.getObject(3).toString().equals(login) && result.getObject(4).toString().equals(password)) {
                                HttpSession s = request.getSession(true);
                                s.setAttribute("id", new String(result.getObject(1).toString()));
                                s.setAttribute("name", new String(result.getObject(2).toString()));
                                s.setAttribute("login", new String(result.getObject(3).toString()));
                                s.setAttribute("password", new String(result.getObject(4).toString()));
                                answer = "";
                                break;
                            } else {
                                answer = "Неправильный логин или пароль! Попробуйте снова!";
                            }
                        }
                        result.close();
                        st.close();
                        connection.close();
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
