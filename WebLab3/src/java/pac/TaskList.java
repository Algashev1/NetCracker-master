package pac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskList {

    Connection connection;
    private ArrayList<Task> list = new ArrayList<>();
    StringBuilder tasksId = new StringBuilder();
    private int userId;

    public TaskList() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
        PreparedStatement st = connection.prepareStatement("select t_id, t_index, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id from Task where t_parent is NULL");
        ResultSet result = st.executeQuery();
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
        result.close();
        st.close();
        connection.close();
    }

    public ArrayList<Task> parentTask(int id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
        PreparedStatement st = connection.prepareStatement("select t_id, t_index, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id from Task where t_parent = ?");
        st.setInt(1, id);
        ResultSet result = st.executeQuery();
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
        st.close();
        connection.close();
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
    public ArrayList <String> findTask(String name) {
        ArrayList <String> l = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getName().equals(name)) {
                l.add(list.get(i).getId());
            }
        }
        if (l.size() > 0) {
            return l;
        }
        else {
            return null;
        }
    }

    public String updateTask(int id, String name, String desc, String time, String cont) {
        if ((!name.equals("")) && (!time.equals(""))) {
            String updateSQL = "UPDATE Task SET t_name = ?, t_description = ?, t_data = ?, t_contacts = ? WHERE t_id = ?";
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
                PreparedStatement st = connection.prepareStatement(updateSQL);
                st.setString(1, name);
                st.setString(2, desc);
                st.setString(3, time);
                st.setString(4, cont);
                st.setInt(5, id);
                st.execute();
                st.close();
                connection.close();
                return "Изменение задачи в БД прошло успешно!";
            } catch (SQLException e) {
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
            String insertSQL = "INSERT INTO Task VALUES(NULL, DEFAULT, ?, ?, ?, ?, NULL, ?)";
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
                PreparedStatement st = connection.prepareStatement(insertSQL);
                st.setString(1, name);
                st.setString(2, desc);
                st.setString(3, time);
                st.setString(4, cont);
                st.setInt(5, userId);
                st.execute();
                st.close();
                connection.close();
                return "Добавление задачи в БД прошло успешно!";
            } catch (SQLException e) {
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
            String insertSQL = "INSERT INTO Task VALUES(NULL, DEFAULT, ?, ?, ?, ?, ?, ?)";
            try {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
                PreparedStatement st = connection.prepareStatement(insertSQL);
                st.setString(1, name);
                st.setString(2, desc);
                st.setString(3, time);
                st.setString(4, cont);
                st.setString(5, id);
                st.setInt(6, userId);
                st.execute();
                st.close();
                connection.close();
                return "Добавление подзадачи в БД прошло успешно!";
            } catch (SQLException e) {
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
        String deleteSQL = "DELETE FROM Task WHERE t_id = ?";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement(deleteSQL);
            st.setInt(1, id);
            st.execute();
            st.close();
            connection.close();
            return "Удаление задачи из БД прошло успешно!";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    
    public String countSubtask(String id) {
        String countSQL = "SELECT COUNT(*) FROM Task WHERE t_parent = ?";
        String message = "0";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement(countSQL);
            st.setString(1, id);
            ResultSet result = st.executeQuery();
            if (result.next()) {
                message = result.getString(1);
            }
            result.close();
            st.close();
            connection.close();
            return message;
        } catch (SQLException e) {
            return message;
        }
    }

    //сортировать задачи по дате
    public void sortDate() {
        Collections.sort(list, new Comparator<Task>() {
            public int compare(Task t1, Task t2) {
                return t1.getTime().compareTo(t2.getTime());
            }
        });
    }

    //сортировать задачи по названию
    public void sortName() {
        Collections.sort(list, new Comparator<Task>() {
            public int compare(Task t1, Task t2) {
                return t1.getName().compareTo(t2.getName());
            }
        });
    }

    //ввод индекса задачи
    public String setIndex(int id) throws SQLException, ClassNotFoundException {
        String indexSQL = "SELECT t_index FROM Task WHERE t_id = ?";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement(indexSQL);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            int n;
            if (result.next()) {
                n = result.getInt(1);
                if (n == 1) {
                    st.clearParameters();
                    st = connection.prepareStatement("UPDATE Task SET t_index = '2' WHERE t_id = ?");
                    st.setInt(1, id);
                    st.execute();
                } else {
                    st.clearParameters();
                    st = connection.prepareStatement("UPDATE Task SET t_index = '1' WHERE t_id = ?");
                    st.setInt(1, id);
                    st.execute();
                }
            }
            result.close();
            st.close();
            connection.close();
            return "Статус задачи успешно изменен!";
        } catch (SQLException e) {
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
        ArrayList<String> list = new ArrayList<String>();
        String overdueSQL = "SELECT t_name FROM Task WHERE t_index = 1 AND u_id = ?";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "root", "364822984");
            PreparedStatement st = connection.prepareStatement(overdueSQL);
            st.setInt(1, id);
            ResultSet result = st.executeQuery();
            while (result.next()) {
                list.add(result.getObject(1).toString());
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
