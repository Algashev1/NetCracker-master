/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pac;

import org.apache.log4j.*;

/**
 *
 * @author 1
 */
public class Task2 implements java.io.Serializable {

    private String id;
    private String name;
    private String description;
    private String time;
    private String contacts;
    private String userId;
    private boolean index; //true если активно (не выполнено), false если не активно (выполено)

    private static final Logger log = Logger.getLogger(Task2.class);
    

    public Task2() {
        log.debug("Task()");
    }

    public Task2(String name, String desc, String time, String cont) {
        //this.id = id;
        this.name = name;
        this.description = desc;
        this.time = time;
        this.contacts = cont;
        this.index = true;
        log.debug("Task(String name, String desc, String time, String cont)");
    }

    public String getId() {
        log.debug("getId()");
        return id;
    }

    public void setId(String value) {
        id = value;
        log.debug("setId(String value)");
    }

    public String getUserId() {
        log.debug("getUserId()");
        return userId;
    }

    public void setUserId(String value) {
        userId = value;
        log.debug("setUserId(String value)");
    }

    public String getName() {
        log.debug("getName()");
        return name;
    }

    public void setName(String value) {
        name = value;
        log.debug("setName(String value)");
    }

    public String getDescription() {
        log.debug("getDescription()");
        return description;
    }

    public void setDescription(String value) {
        description = value;
        log.debug("setDescription(String value)");
    }

    public String getTime() {
        log.debug("getTime()");
        return time;
    }

    public void setTime(String value) {
        time = value;
        log.debug("setTime(String value)");
    }

    public String getContacts() {
        log.debug("getContacts()");
        return contacts;
    }

    public void setContacts(String value) {
        contacts = value;
        log.debug("setContacts(String value)");
    }

    @Override
    public String toString() {
        log.debug("toString()");
        return "Task{" + "name=" + name + ", description=" + description + ", time=" + time + ", contacts=" + contacts + '}';
    }

    public boolean getIndex() {
        log.debug("getIndex()");
        return index;
    }

    public void setIndex(boolean index) {
        log.debug("setIndex(boolean index)");
        this.index = index;
    }

}
