package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Calendar;
import java.util.ArrayList;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import pac.*;

public final class tasks_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {


    String message = "";

    public String overdueTask(int id) {
        message = MyMethod.overdueTask(id);
        return message;
    }

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("\n");
      out.write("    <head>\n");
      out.write("        <title>Список задач</title> \n");
      out.write("        <link rel=\"stylesheet\" href=\"style.css\">\n");
      out.write("\n");
      out.write("        <script src=\"//code.jquery.com/jquery-1.11.3.min.js\"></script>\n");
      out.write("        <script src=\"app.js\"></script>\n");
      out.write("\n");
      out.write("        <script>\n");
      out.write("            function fresh() {\n");
      out.write("                location.reload();\n");
      out.write("            ");
overdueTask(Integer.parseInt(session.getAttribute("id").toString()));
      out.write("\n");
      out.write("            }\n");
      out.write("            setInterval(\"fresh()\", 5000);\n");
      out.write("        </script>\n");
      out.write("\n");
      out.write("\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <div class=\"cont2\">\n");
      out.write("            <a class=\"btn\" href=\"index.jsp?exit=true\">Выход</a>\n");
      out.write("            <h2>Список задач пользователя: <b id=\"user-name\">");
out.println(session.getAttribute("name"));
      out.write("</b></h2>\n");
      out.write("            <table  class=\"taskTable\">\n");
      out.write("                <tr>\n");
      out.write("                    <td onclick=\"show();\">\n");
      out.write("                        Поиск задач:\n");
      out.write("                    </td>\n");
      out.write("                    <td>\n");
      out.write("                        <form action=\"tasks.jsp\" meth\n");
      out.write("                              od=\"get\">\n");
      out.write("                            <table>\n");
      out.write("                                <tr>\n");
      out.write("                                    <td>\n");
      out.write("                                        <input type=\"text\" name=\"findTaskName\" placeholder=\"Введите название задачи\"/>\n");
      out.write("                                    </td>\n");
      out.write("                                    <td>\n");
      out.write("                                        <input type=\"submit\" value=\"Найти\" style=\"cursor: pointer;\">\n");
      out.write("                                    </td>\n");
      out.write("                                </tr>\n");
      out.write("                            </table>\n");
      out.write("                        </form>\n");
      out.write("                    </td>\n");
      out.write("                </tr>\n");
      out.write("            </table>\n");
      out.write("\n");
      out.write("            ");

                if (session.getAttribute("id") == "null") {
                    response.sendRedirect("http://localhost:8084/WebLab3/");
                } else {
                    TaskList taskList = new TaskList();
                    taskList.setUserId(Integer.parseInt(session.getAttribute("id").toString()));

                    int actTaskNum = -1;
                    String buf = "";
                    String url = request.getQueryString();
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
                                    actTaskNum = taskList.findTask(new String(perArr[1].replace("+", " ")));
                                }
                            }
                        }
                    }
            
      out.write("\n");
      out.write("            <div class=\"message\">");
      out.print( message);
      out.write("</div>\n");
      out.write("            <table class=\"taskTable\" >  \n");
      out.write("                <thead>\n");
      out.write("                    <tr>\n");
      out.write("                        <th class= \"th2\">ID</th>\n");
      out.write("                        <th class= \"th2\">Завершено</th>\n");
      out.write("                        <th class= \"th2\">Задача</th>\n");
      out.write("                        <th class= \"th2\">Описание</th>\n");
      out.write("                        <th class= \"th2\">Дата-Время</th>\n");
      out.write("                        <th class= \"th2\">Контакты</th>\n");
      out.write("                        <th class= \"th2\">Изменить</th>\n");
      out.write("                        <th class= \"th2\">Завершить/активировать</th>\n");
      out.write("                        <th class= \"th2\">Добавить подзадачу</th>\n");
      out.write("                        <th class= \"th2\">Удалить</th>\n");
      out.write("                    </tr>\n");
      out.write("                </thead>\n");
      out.write("                ");

                    for (int i = 0; i < taskList.getTaskList().size(); i++) {
                        if (session.getAttribute("id").toString().contains(taskList.getTaskList().get(i).getUserId())) {
                            if (actTaskNum == Integer.parseInt(taskList.getTaskList().get(i).getId())) {
                                buf = "active";
                            }
                
      out.write("\n");
      out.write("                <tbody>\n");
      out.write("                <tbody class= \"labels\">\n");
      out.write("                    <tr class= \"");
      out.print( buf);
      out.write("\">\n");
      out.write("                        <td class= \"td2\"> \n");
      out.write("                            <label for=\"a");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\"> ");
      out.print(taskList.getTaskList().get(i).getId());
      out.write(" </label>\n");
      out.write("                            <input type=\"checkbox\" name=\"a");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\" id=\"a");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\" data-toggle=\"toggle\"> \n");
      out.write("                        </td>\n");
      out.write("                        <td class= \"td2\"> \n");
      out.write("                            ");
boolean flag = taskList.getTaskList().get(i).getIndex();
                                if (flag) {
                            
      out.write("\n");
      out.write("                            <input type=\"checkbox\" id='");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("'>\n");
      out.write("\n");
      out.write("                            ");
} else {
                            
      out.write("\n");
      out.write("                            <input type=\"checkbox\" id='");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("' checked=\"checked\">\n");
      out.write("                            ");
}
      out.write("\n");
      out.write("                        </td>\n");
      out.write("                        <td class= \"td2\"> ");
      out.print(taskList.getTaskList().get(i).getName());
      out.write(" </td>\n");
      out.write("                        <td class= \"td2\"> ");
      out.print(taskList.getTaskList().get(i).getDescription());
      out.write(" </td>\n");
      out.write("                        <td class= \"td2\"> ");
      out.print(taskList.getTaskList().get(i).getTime());
      out.write(" </td>\n");
      out.write("                        <td class= \"td2\"> ");
      out.print(taskList.getTaskList().get(i).getContacts());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"change2.jsp?id=");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\">Изменить</a></td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"?complete=");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\">Завершить/активировать</a> </td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"add.jsp?id=");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\">Добавить подзадачу</a></td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"?delete=");
      out.print(taskList.getTaskList().get(i).getId());
      out.write("\">Удалить</a> </td>\n");
      out.write("                    </tr>\n");
      out.write("                </tbody>\n");
      out.write("                <tbody class=\"hide\">\n");
      out.write("                    ");
ArrayList<Task> list = taskList.parentTask(Integer.parseInt(taskList.getTaskList().get(i).getId()));
                        for (int j = 0; j < list.size(); j++) {
      out.write("\n");
      out.write("                    <tr>\n");
      out.write("                        <td class= \"td2\">");
      out.print(list.get(j).getId());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\">");
      out.print(list.get(j).getIndex());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\">");
      out.print(list.get(j).getName());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\">");
      out.print(list.get(j).getDescription());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\">");
      out.print(list.get(j).getTime());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\">");
      out.print(list.get(j).getContacts());
      out.write("</td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"change2.jsp?id=");
      out.print(list.get(j).getId());
      out.write("\">Изменить</a></td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"?complete=");
      out.print(list.get(j).getId());
      out.write("\">Завершить/активировать</a> </td>\n");
      out.write("                        <td class= \"td2\"> </td>\n");
      out.write("                        <td class= \"td2\"> <a href=\"?delete=");
      out.print(list.get(j).getId());
      out.write("\">Удалить</a> </td>\n");
      out.write("                    </tr>\n");
      out.write("                    ");
 } 
      out.write("\n");
      out.write("                </tbody> \n");
      out.write("                </tbody>\n");
      out.write("\n");
      out.write("                ");
buf = "";
                        }
                    }
      out.write("\n");
      out.write("            </table>\n");
      out.write("            <a class=\"btn\" href=\"add.jsp\">Добавить задачу</a>\n");
      out.write("            ");
}
            
      out.write("\n");
      out.write("        </div>\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
