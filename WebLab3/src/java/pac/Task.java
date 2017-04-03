/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

/**
 *
 * @author 1
 */
public class Task implements java.io.Serializable {
    
    private String id;
    private String name;
    private String description;
    private String time;
    private String contacts;
    private String userId;
    private boolean index; //true если активно (не выполнено), false если не активно (выполено)
    
    public Task(){}

    public Task(String name, String desc, String time, String cont) {
//        this.id = id;
        this.name = name;
        this.description = desc;
        this.time = time;
        this.contacts = cont;
        this.index = true;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String value) {
        id = value;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String value) {
        userId = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        name = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        description = value;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String value) {
        time = value;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String value) {
        contacts = value;
    }

    @Override
    public String toString() {
        return "Task{" + "name=" + name + ", description=" + description + ", time=" + time + ", contacts=" + contacts + '}';
    }
    
    public boolean getIndex() {
        return index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

}

