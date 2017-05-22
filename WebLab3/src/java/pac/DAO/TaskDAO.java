package pac.DAO;

import java.util.ArrayList;
import pac.logic.Task;

public interface TaskDAO {

    public ArrayList<Task> taskList(int id);

    public ArrayList<Task> parentTask(int id);

    public String updateTask(int id, String name, String desc, String time, String cont);

    public String addTask(String name, String desc, String time, String cont, String uId);

    public String addTaskChild(String id, String name, String desc, String time, String cont, String uId);

    public String delTask(int id);

    public String countSubtask(int id);

    public String setIndex(int id);

    public String overdueTask(int id);

    public ArrayList<String> returnTask(String id);

}
