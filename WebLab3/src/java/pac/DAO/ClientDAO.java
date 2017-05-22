package pac.DAO;

import pac.logic.Client;


public interface ClientDAO {

    public String regClient(String name, String login, String password);

    public Client authClient(String login, String password);

}
