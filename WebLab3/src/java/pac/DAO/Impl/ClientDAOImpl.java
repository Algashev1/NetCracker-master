package pac.DAO.Impl;

import pac.DAO.ClientDAO;
import pac.logic.Client;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import pac.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class ClientDAOImpl implements ClientDAO {

    private static final Logger log = Logger.getLogger(ClientDAOImpl.class);

    @Override
    public String regClient(String name, String login, String password) {
        Session session = null;
        String msg = "Регистрация прошла успешно!";
        try {
            Client cl = new Client();
            cl.setName(name);
            cl.setLogin(login);
            cl.setPassword(password);
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(cl);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            msg = "Регистрация не прошла! Попробуйте снова!";
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return msg;
        }
    }

    @Override
    public Client authClient(String login, String password) {
        Session session = null;
        Client result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria clientCriteria = session.createCriteria(Client.class);
            clientCriteria.add(Restrictions.eq("login", login));
            clientCriteria.add(Restrictions.eq("password", password));
            result = (Client)clientCriteria.uniqueResult();
            
            /*Query query = session.createQuery("FROM Client WHERE login = ? and password = ?");
            query.setParameter(0, login);
            query.setParameter(1, password);
            List client = query.list();
            result = (Client) client.get(0);*/
            
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return result;
        }
    }

}
