package pac;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class OperationsTasks {

    public static String regClient(String name, String login, String password) {
        String message;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement("INSERT INTO Client VALUE (NULL, ?,  ?, ?)");
            st.setString(1, name);
            st.setString(2, login);
            st.setString(3, password);
            st.execute();
            st.close();
            connection.close();
            return message = "Регистрация прошла успешно!";
        } catch (SQLException | ClassNotFoundException e) {
            return message = "Регистрация не прошла! Попробуйте снова!";
        }
    }

    //метод проверяет какие задачи оказались просроченными
    public static String overdueTask(String s_id) {
        String message = "";
        Date d = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ArrayList<String> list = new ArrayList<>();
        String s = format1.format(d);
        String overdueSQL = "SELECT t_name FROM Task WHERE t_index = 1 AND t_data < ? AND u_id = ?";
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement(overdueSQL);
            st.setString(1, s);
            st.setString(2, s_id);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                list.add(result.getObject(1).toString());
            }

            st.close();
            result.close();
            connection.close();

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
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    public static ArrayList<String> returnTask(String id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement("SELECT t_id, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id FROM Task WHERE t_id = ?");
            st.setString(1, id);
            ResultSet result = st.executeQuery();
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
            st.close();
            connection.close();
            return list;
        } catch (SQLException e) {
            return null;
        }
    }
}
