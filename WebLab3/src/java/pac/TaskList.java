package pac;

import java.util.ArrayList;
import java.util.List;
import pac.logic.Task;

import org.apache.log4j.*;

public class TaskList {

    private ArrayList<Task> list = new ArrayList<>();
    StringBuilder tasksId = new StringBuilder();
    private int userId;

    private static final Logger log = Logger.getLogger(TaskList.class);

    public TaskList(int id) {
        userId = id;
        list = (ArrayList<Task>) Factory.getInstance().getTaskDAO().taskList(id);
    }

    public List<Task> parentTask(int id) {
        return Factory.getInstance().getTaskDAO().parentTask(id);
    }

    public ArrayList<Task> getTaskList() {
        log.debug("getTaskList()");
        return list;
    }

    public void setTaskList(ArrayList<Task> list) {
        this.list = list;
        log.debug("setTaskList(ArrayList<Task> list)");
    }

    //поиск задачи
    public Task findTask(int number) {
        log.debug("findTask(int number)");
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
        log.debug("findTask(String name)");
        if (l.size() > 0) {
            return l;
        } else {
            return null;
        }
    }

    public String updateTask(int id, String name, String desc, String time, String cont) {
        return Factory.getInstance().getTaskDAO().updateTask(id, name, desc, time, cont);
    }

    //добавление задачи
    public String addTask(String name, String desc, String time, String cont) {
        return Factory.getInstance().getTaskDAO().addTask(name, desc, time, cont, Integer.toString(userId));
    }

    public String addTaskChild(String id, String name, String desc, String time, String cont) {
        return Factory.getInstance().getTaskDAO().addTaskChild(id, name, desc, time, cont, Integer.toString(userId));
    }

    //удаление задачи
    public String delTask(int id) {
        return Factory.getInstance().getTaskDAO().delTask(id);
    }

    public String countSubtask(String id) {
        return Factory.getInstance().getTaskDAO().countSubtask(Integer.parseInt(id));
    }
    //ввод индекса задачи
    public String setIndex(int id) {
        return Factory.getInstance().getTaskDAO().setIndex(id);
    }

    public void setUserId(int id) {
        this.userId = id;
        log.debug("setUserId(int id)");
    }

    public int getUserId() {
        log.debug("getUserId()");
        return this.userId;
    }

}
