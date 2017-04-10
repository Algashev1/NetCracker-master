package pac;

import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OperationsTasks {

    public static String regClient(String name, String login, String password) {
        Connection conn;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "INSERT INTO Client VALUE (NULL, ?,  ?, ?)";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, name);
            pStatement.setString(2, login);
            pStatement.setString(3, password);
            pStatement.execute();
            pStatement.close();
            ctx.close();
            conn.close();
            return "Регистрация прошла успешно!";
        } catch (SQLException | NamingException e) {
            return "Регистрация не прошла! Попробуйте снова!";
        }
    }

    //метод проверяет какие задачи оказались просроченными
    public static String overdueTask(String s_id) {
        Connection conn;
        String message;
        Date d = new Date();
        ArrayList<String> list = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT t_name FROM Task WHERE t_index = 1 AND t_data < ? AND u_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            String s = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d);
            pStatement.setString(1, s);
            pStatement.setString(2, s_id);
            ResultSet result = pStatement.executeQuery();
            while (result.next()) {
                list.add(result.getObject(1).toString());
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();

            if ((list != null) && (0 < list.size())) {
                message = "Просрочены следующие задачи:";
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        message += " " + list.get(i);
                    } else {
                        message += " ," + list.get(i);
                    }
                }
            } else {
                message = "Нет просроченных задач";
            }
            return message;
        } catch (SQLException | NamingException e) {
            return e.getMessage();
        }
    }

    public static ArrayList<String> returnTask(String id) {
        Connection conn;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT t_id, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id FROM Task WHERE t_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, id);
            ResultSet result = pStatement.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            if (result.next()) {
                list.add(result.getString(1));
                list.add(result.getString(2));
                list.add(result.getString(3));
                list.add(result.getString(4));
                list.add(result.getString(5));
                list.add(result.getString(6));
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();
            return list;
        } catch (SQLException | NamingException e) {
            return null;
        }
    }
}
