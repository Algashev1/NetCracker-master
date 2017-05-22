package pac;

import pac.DAO.*;
import pac.DAO.Impl.*;
/**
 *
 * @author DNS
 */
public class Factory {
  
  private static ClientDAO clientDAO = null;
  private static TaskDAO taskDAO = null;
  private static Factory instance = null;

  public static synchronized Factory getInstance(){
    if (instance == null){
      instance = new Factory();
    }
    return instance;
  }

  public ClientDAO getClientDAO(){
    if (clientDAO == null){
      clientDAO = new ClientDAOImpl();
    }
    return clientDAO;
  }
  
   public TaskDAO getTaskDAO(){
    if (taskDAO == null){
      taskDAO = new TaskDAOImpl();
    }
    return taskDAO;
  }

}
