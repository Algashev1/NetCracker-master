<%@page import="pac.logic.Task"%>
<%@page import="javax.servlet.annotation.WebServlet"%>
<%@page import="java.lang.String"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<%@page import="org.apache.tomcat.dbcp.dbcp2.BasicDataSource"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="javax.naming.*"%>
<%@page import="org.apache.log4j.Logger" %>

<%@page import="pac.*"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%!
    Logger log = Logger.getLogger("tasks.jsp");
    String message = "";
%>
<% log.info("загрузка tasks.jsp"); %>
<!DOCTYPE html>
<html>

    <head>
        <title>Список задач</title> 
        <link rel="stylesheet" href="style.css">

        <script src="//code.jquery.com/jquery-1.11.3.min.js"></script>

        <script>
            function fresh() {
                location.reload();
            <% message = OperationsTasks.overdueTask(session.getAttribute("id").toString());%>
            }
            setInterval("fresh()", 30000);
        </script>

        <script>
            $(document).ready(function () {
                $('[data-toggle="toggle"]').change(function () {
                    $(this).parents().next('.hide').toggle();
                });
            });
        </script>

        <script>
            function func(t_id) {
                id = document.getElementById(t_id);
                if (id.checked)
                    document.location.href = "?complete=" + t_id;
                else
                    document.location.href = "?complete=" + t_id;
            }
        </script>

    </head>
    <body>
        <div class="cont2">
            <a class="btn" href="index.jsp?exit=true">Выход</a>
            <h2>Список задач пользователя: <b id="user-name"><%out.println(session.getAttribute("name"));%></b></h2>
            <table  class="taskTable">
                <tr>
                    <td onclick="show();">
                        Поиск задач:
                    </td>
                    <td>
                        <form action="tasks.jsp" meth
                              od="get">
                            <table>
                                <tr>
                                    <td>
                                        <input type="text" name="findTaskName" placeholder="Введите название задачи"/>
                                    </td>
                                    <td>
                                        <input type="submit" value="Найти" style="cursor: pointer;">
                                    </td>
                                </tr>
                            </table>
                        </form>
                    </td>
                </tr>
            </table>

            <%
                if (session.getAttribute("id") == "null") {
                    response.sendRedirect("http://localhost:8084/WebLab3/");
                } else {
                    TaskList taskList = new TaskList(Integer.parseInt(session.getAttribute("id").toString()));

                    int actTaskNum = -1;
                    String buf = "";
                    String url = request.getQueryString();
                    ArrayList<String> l = new ArrayList<>();
                    if (url != null) {
                        if (url.contains("=")) {
                            String perArr[] = url.split("=");
                            if (perArr.length == 2) {
                                if (perArr[0].contains("delete")) {
                                    message = taskList.delTask(Integer.parseInt(perArr[1]));
                                    response.sendRedirect("http://localhost:8084/WebLab3/tasks.jsp");
                                }
                                if (perArr[0].contains("complete")) {
                                    message = taskList.setIndex(Integer.parseInt(perArr[1]));
                                    response.sendRedirect("http://localhost:8084/WebLab3/tasks.jsp");
                                }

                                if (perArr[0].contains("taskid")) {
                                    session.setAttribute("taskId", "null");
                                }

                                if (perArr[0].contains("findTaskName")) {
                                    l = taskList.findTask(new String(perArr[1].replace("+", " ")));
                                    if (l != null) {

                                    }

                                }
                            }

                        } else if (url.toString().equals("toExcel")) {
                            OperationsTasks.toExcel(taskList.getTaskList());
                        }
                    }
            %>
            <div class="message"><%= message%></div>
            <table class="taskTable" > 
                <thead>
                    <tr class="bg">
                        <th class= "th2">Подзадачи</th>
                        <th class= "th2">Завершено</th>
                        <th class= "th2">Задача</th>
                        <th class= "th2">Описание</th>
                        <th class= "th2">Дата-Время</th>
                        <th class= "th2">Контакты</th>
                        <th class= "th2">Изменить</th>
                        <th class= "th2">Завершить/активировать</th>
                        <th class= "th2">Добавить подзадачу</th>
                        <th class= "th2">Удалить</th>
                    </tr>
                </thead>
                <%
                    for (int i = 0; i < taskList.getTaskList().size(); i++) {
                        if (session.getAttribute("id").toString().contains(taskList.getTaskList().get(i).getClient().getId())) {
                            if (l != null) {
                                for (int k = 0; k < l.size(); k++) {
                                    if (l.get(k).equals(taskList.getTaskList().get(i).getId())) {
                                        buf = "active";
                                        break;
                                    }
                                }
                            }
                %>
                <tbody>
                <tbody>
                    <tr class=<%=buf%>>
                        <td class= "td2"> 
                            <label for="a<%=taskList.getTaskList().get(i).getId()%>"> <%=taskList.countSubtask(taskList.getTaskList().get(i).getId().toString())%> свернуть/развернуть</label>
                            <input type="checkbox" name="a<%=taskList.getTaskList().get(i).getId()%>" id="a<%=taskList.getTaskList().get(i).getId()%>" data-toggle="toggle"> 
                        </td>
                        <td class= "td2"> 
                            <%
                                boolean flag = true;
                                if (taskList.getTaskList().get(i).getIndex().equals("2")) {
                                    flag = false;
                                }
                                if (flag) {
                            %>
                            <input type="checkbox" id='<%=taskList.getTaskList().get(i).getId()%>'>

                            <%} else {
                            %>
                            <input type="checkbox" id='<%=taskList.getTaskList().get(i).getId()%>' checked="checked">
                            <%}%>
                        </td>
                        <td class= "td2"> <%=taskList.getTaskList().get(i).getName()%> </td>
                        <td class= "td2"> <%=taskList.getTaskList().get(i).getDesc()%> </td>
                        <td class= "td2"> <%=taskList.getTaskList().get(i).getData()%> </td>
                        <td class= "td2"> <%=taskList.getTaskList().get(i).getCont()%></td>
                        <td class= "td2"> <a href="change.jsp?id=<%=taskList.getTaskList().get(i).getId()%>">Изменить</a></td>
                        <td class= "td2"> <a href="?complete=<%=taskList.getTaskList().get(i).getId()%>">Завершить/активировать</a> </td>
                        <td class= "td2"> <a href="add.jsp?id=<%=taskList.getTaskList().get(i).getId()%>">Добавить подзадачу</a></td>
                        <td class= "td2"> <a href="?delete=<%=taskList.getTaskList().get(i).getId()%>">Удалить</a> </td>
                    </tr>
                </tbody>

                <%
                    List<Task> list = taskList.parentTask(Integer.parseInt(taskList.getTaskList().get(i).getId()));
                    for (int j = 0; j < list.size(); j++) {
                %>

                <tr class=<%=buf%>>
                    <td class= "td2"></td>
                    <td class= "td2">
                        <%
                            boolean flag2 = true;
                            if (list.get(j).getIndex().equals("2")) {
                                flag2 = false;
                            }

                            if (flag2) {
                        %>
                        <input type="checkbox" id='<%=list.get(j).getId()%>'>
                        <%} else {%>
                        <input type="checkbox" id='<%=list.get(j).getId()%>' checked="checked">
                        <% }%>
                    </td>
                    <td class= "td2"><%=list.get(j).getName()%></td>
                    <td class= "td2"><%=list.get(j).getDesc()%></td>
                    <td class= "td2"><%=list.get(j).getData()%></td>
                    <td class= "td2"><%=list.get(j).getCont()%></td>
                    <td class= "td2"> <a href="change.jsp?id=<%=list.get(j).getId()%>">Изменить</a></td>
                    <td class= "td2"> <a href="?complete=<%=list.get(j).getId()%>">Завершить/активировать</a> </td>
                    <td class= "td2"> </td>
                    <td class= "td2"> <a href="?delete=<%=list.get(j).getId()%>">Удалить</a> </td>
                </tr>
                <% } %>

                </tbody>

                <%buf = "";
                        }
                    }%>
            </table>
            <a class="btn" href="add.jsp">Добавить задачу</a>
            <a class="btn" a href="?toExcel">Экспорт в Excel</a>
            <%}
            %>
        </div>
    </body>
</html>
