package pac.logic;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="u_id")
    private String id;
    
    @Column(name = "u_name")
    private String name;
    
    @Column(name = "u_login")
    private String login;
    
    @Column(name = "u_password")
    private String password;
    
    @OneToMany(mappedBy="client", fetch = FetchType.EAGER)
    private List<Task> tasks;

    public Client() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Task> getTasks () {
        return tasks;
    }
    
    public void setTasks (List<Task> tasks) {
        this.tasks = tasks;
    }

}
