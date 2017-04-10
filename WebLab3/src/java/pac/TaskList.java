package pac;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class TaskList {

    private ArrayList<Task> list = new ArrayList<>();
    StringBuilder tasksId = new StringBuilder();
    private int userId;

    public TaskList() throws SQLException, ClassNotFoundException, NamingException {
        Connection conn;
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
        conn = ds.getConnection();
        String sql = "SELECT t_id, t_index, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id FROM Task WHERE t_parent is NULL";
        PreparedStatement pStatement = conn.prepareStatement(sql);
        ResultSet result = pStatement.executeQuery();
        boolean index = false;
        while (result.next()) {
            Task task = new Task();
            task.setId(result.getString(1));
            task.setName(result.getString(3));
            task.setDescription(result.getString(4));
            task.setTime(result.getString(5));
            task.setContacts(result.getString(6));
            task.setUserId(result.getString(7));
            if ("1".equals(result.getString(2))) {
                index = true;
            }
            if ("2".equals(result.getString(2))) {
                index = false;
            }
            task.setIndex(index);
            list.add(task);
        }
        pStatement.close();
        ctx.close();
        conn.close();
    }

    public ArrayList<Task> parentTask(int id) throws SQLException, ClassNotFoundException, NamingException {
        Connection conn;
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
        conn = ds.getConnection();
        String sql = "SELECT t_id, t_index, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id FROM Task WHERE t_parent = ?";
        PreparedStatement pStatement = conn.prepareStatement(sql);
        pStatement.setInt(1, id);
        ResultSet result = pStatement.executeQuery();
        ArrayList<Task> list2 = new ArrayList<>();
        boolean index = false;
        while (result.next()) {
            Task task = new Task();
            task.setId(result.getString(1));
            task.setName(result.getString(3));
            task.setDescription(result.getString(4));
            task.setTime(result.getString(5));
            task.setContacts(result.getString(6));
            task.setUserId(result.getString(7));
            if ("1".equals(result.getString(2))) {
                index = true;
            }
            if ("2".equals(result.getString(2))) {
                index = false;
            }
            task.setIndex(index);
            list2.add(task);
        }
        result.close();
        pStatement.close();
        ctx.close();
        conn.close();
        return list2;
    }

    public ArrayList<Task> getTaskList() {
        return list;
    }

    public void setTaskList(ArrayList<Task> list) {
        this.list = list;
    }

    //поиск задачи
    public Task findTask(int number) {
        return (Task) list.get(number);
    }

    //поиск задачи по имени
    public ArrayList<String> findTask(String name) {
        ArrayList<String> l = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                l.add(list.get(i).getId());
            }
        }
        if (l.size() > 0) {
            return l;
        } else {
            return null;
        }
    }

    public String updateTask(int id, String name, String desc, String time, String cont) {
        if ((!name.equals("")) && (!time.equals(""))) {
            Connection conn;
            try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
                conn = ds.getConnection();
                String sql = "UPDATE Task SET t_name = ?, t_description = ?, t_data = ?, t_contacts = ? WHERE t_id = ?";
                PreparedStatement pStatement = conn.prepareStatement(sql);
                pStatement.setString(1, name);
                pStatement.setString(2, desc);
                pStatement.setString(3, time);
                pStatement.setString(4, cont);
                pStatement.setInt(5, id);
                pStatement.execute();
                pStatement.close();
                ctx.close();
                conn.close();
                return "Изменение задачи в БД прошло успешно!";
            } catch (SQLException | NamingException e) {
                if (e.getMessage().contains("Incorrect datetime value")) {
                    return "Неверный формат даты и времени!";
                } else {
                    return e.getMessage();
                }
            }
        } else {
            String str = "";
            if (name.equals("")) {
                str += "\"Имя\"";
            }
            if (time.equals("")) {
                str += "\"Дата/Время\"";
            }
            return "Заполните поле " + str;
        }
    }

    //добавление задачи
    public String addTask(String name, String desc, String time, String cont) {
        if ((!name.equals("")) && (!time.equals(""))) {
            Connection conn;
            try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
                conn = ds.getConnection();
                String sql = "INSERT INTO Task VALUES(NULL, DEFAULT, ?, ?, ?, ?, NULL, ?)";
                PreparedStatement pStatement = conn.prepareStatement(sql);
                pStatement.setString(1, name);
                pStatement.setString(2, desc);
                pStatement.setString(3, time);
                pStatement.setString(4, cont);
                pStatement.setInt(5, userId);
                pStatement.execute();
                pStatement.close();
                ctx.close();
                conn.close();
                return "Добавление задачи в БД прошло успешно!";
            } catch (SQLException | NamingException e) {
                if (e.getMessage().contains("Incorrect datetime value")) {
                    return "Неверный формат даты и времени!";
                } else {
                    return e.getMessage();
                }
            }
        } else {
            String str = "";
            if (name.equals("")) {
                str += "\"Имя\"";
            }
            if (time.equals("")) {
                str += "\"Дата/Время\"";
            }
            return "Заполните поле " + str;
        }
    }

    public String addTaskChild(String id, String name, String desc, String time, String cont) {
        if ((!name.equals("")) && (!time.equals(""))) {
            Connection conn;
            try {
                Context ctx = new InitialContext();
                DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
                conn = ds.getConnection();
                String sql = "INSERT INTO Task VALUES(NULL, DEFAULT, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pStatement = conn.prepareStatement(sql);
                pStatement.setString(1, name);
                pStatement.setString(2, desc);
                pStatement.setString(3, time);
                pStatement.setString(4, cont);
                pStatement.setString(5, id);
                pStatement.setInt(6, userId);
                pStatement.execute();
                pStatement.close();
                ctx.close();
                conn.close();
                return "Добавление подзадачи в БД прошло успешно!";
            } catch (SQLException | NamingException e) {
                if (e.getMessage().contains("Incorrect datetime value")) {
                    return "Неверный формат даты и времени!";
                } else {
                    return e.getMessage();
                }
            }
        } else {
            String str = "";
            if (name.equals("")) {
                str += "\"Имя\"";
            }
            if (time.equals("")) {
                str += "\"Дата/Время\"";
            }
            return "Заполните поле " + str;
        }
    }

    //удаление задачи
    public String delTask(int id) {
        Connection conn;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "DELETE FROM Task WHERE t_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, id);
            pStatement.execute();
            pStatement.close();
            ctx.close();
            conn.close();
            return "Удаление задачи из БД прошло успешно!";
        } catch (SQLException | NamingException e) {
            return e.getMessage();
        }
    }

    public String countSubtask(String id) {
        Connection conn;
        String message = "0";
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT COUNT(*) FROM Task WHERE t_parent = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, id);
            ResultSet result = pStatement.executeQuery();
            if (result.next()) {
                message = result.getString(1);
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();
            return message;
        } catch (SQLException | NamingException e) {
            return message;
        }
    }

    //сортировать задачи по дате
    public void sortDate() {
        Collections.sort(list, (Task t1, Task t2) -> t1.getTime().compareTo(t2.getTime()));
    }

    //сортировать задачи по названию
    public void sortName() {
        Collections.sort(list, (Task t1, Task t2) -> t1.getName().compareTo(t2.getName()));
    }

    //ввод индекса задачи
    public String setIndex(int id) throws SQLException, ClassNotFoundException {
        Connection conn;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT t_index FROM Task WHERE t_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, id);
            ResultSet result = pStatement.executeQuery();
            int n;
            if (result.next()) {
                n = result.getInt(1);
                if (n == 1) {
                    pStatement.clearParameters();
                    pStatement = conn.prepareStatement("UPDATE Task SET t_index = '2' WHERE t_id = ?");
                    pStatement.setInt(1, id);
                    pStatement.execute();
                } else {
                    pStatement.clearParameters();
                    pStatement = conn.prepareStatement("UPDATE Task SET t_index = '1' WHERE t_id = ?");
                    pStatement.setInt(1, id);
                    pStatement.execute();
                }
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();
            return "Статус задачи успешно изменен!";
        } catch (SQLException | NamingException e) {
            return e.getMessage();
        }
    }

    public void setUserId(int id) {
        this.userId = id;
    }

    public int getUserId() {
        return this.userId;
    }

    public ArrayList<String> overdueTask(int id) {
        Connection conn;
        ArrayList<String> l = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT t_name FROM Task WHERE t_index = 1 AND u_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, id);
            ResultSet result = pStatement.executeQuery();
            while (result.next()) {
                l.add(result.getObject(1).toString());
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();
            return l;
        } catch (SQLException | NamingException e) {
            return null;
        }
    }
}
