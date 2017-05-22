package pac.DAO.Impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import pac.DAO.TaskDAO;
import pac.logic.Client;
import pac.logic.Task;
import pac.util.HibernateUtil;

public class TaskDAOImpl implements TaskDAO {

    private static final Logger log = Logger.getLogger(TaskDAOImpl.class);

    @Override
    public ArrayList<Task> taskList(int id) {
        Session session = null;
        ArrayList<Task> list = new ArrayList<>();
        Client cl;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            cl = (Client) session.get(Client.class, Integer.toString(id));
            Criteria criteria = session.createCriteria(Task.class);
            criteria.add(Restrictions.eq("client", cl));
            criteria.add(Restrictions.isNull("parent"));
            list = (ArrayList<Task>) criteria.list();
            int a = 0;
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return list;
        }
    }

    @Override
    public ArrayList<Task> parentTask(int id) {
        Session session = null;
        List<Task> tasks = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria criteria = session.createCriteria(Task.class);
            tasks = criteria.add(Restrictions.eq("parent", Integer.toString(id))).list();
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return (ArrayList<Task>) tasks;
        }
    }

    @Override
    public String updateTask(int id, String name, String desc, String time, String cont) {
        Session session = null;
        Task t;
        String msg = "Изменение задачи в БД прошло успешно!";
        try {
            if (!name.equals("") && !time.equals("")) {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                t = (Task) session.get(Task.class, Integer.toString(id));
                t.setName(name);
                t.setDesc(desc);
                t.setData(time);
                t.setCont(cont);
                session.update(t);
                session.getTransaction().commit();
            } else {
                msg = "Ошибка! Проверьте введенные данные.";
            }
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
            msg = "Ошибка! Проверьте введенные данные.";

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return msg;
        }
    }

    @Override
    public String addTask(String name, String desc, String time, String cont, String uId) {
        Session session = null;
        Task t = new Task();
        String msg = "Добавление задачи в БД прошло успешно!";
        try {
            if (!name.equals("") && !time.equals("")) {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Client cl = (Client) session.get(Client.class, uId);
                t.setName(name);
                t.setDesc(desc);
                t.setData(time);
                t.setCont(cont);
                t.setClient(cl);
                t.setIndex("1");
                session.save(t);
                session.getTransaction().commit();
            } else {
                msg = "Ошибка! Проверьте введенные данные.";
            }
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
            msg = "Ошибка! Проверьте введенные данные.";

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return msg;
        }
    }

    @Override
    public String addTaskChild(String id, String name, String desc, String time, String cont, String uId) {
        Session session = null;
        Task t = new Task();
        String msg = "Добавление подзадачи в БД прошло успешно!";
        try {
            if (!name.equals("") && !time.equals("")) {
                session = HibernateUtil.getSessionFactory().openSession();
                session.beginTransaction();
                Client cl = (Client) session.get(Client.class, uId);
                t.setName(name);
                t.setDesc(desc);
                t.setData(time);
                t.setCont(cont);
                t.setClient(cl);
                t.setIndex("1");
                t.setParent(id);
                session.save(t);
                session.getTransaction().commit();
            } else {
                msg = "Ошибка! Проверьте введенные данные.";
            }
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
            msg = "Ошибка! Проверьте введенные данные.";

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return msg;
        }
    }

    @Override
    public String delTask(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Task task = new Task();
            task = (Task) session.get(Task.class, Integer.toString(id));
            task.setClient(null);
            session.delete(task);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return "Удаление задачи из БД прошло успешно!";
        }
    }

    @Override
    public String countSubtask(int id) {
        Session session = null;
        String msg = "0";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Query query = session.createQuery("FROM Task WHERE parent = ?");
            query.setParameter(0, Integer.toString(id));
            List list = query.list();
            msg = Integer.toString(list.size());

        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return msg;
        }
    }

    @Override
    public String setIndex(int id) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Task task = new Task();
            task = (Task) session.get(Task.class, Integer.toString(id));
            if (task.getIndex().equals("1")) {
                task.setIndex("2");
            } else {
                task.setIndex("1");
            }
            session.update(task);
            session.getTransaction().commit();
        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return "Статус задачи успешно изменен!";
        }
    }

    @Override
    public String overdueTask(int id) {
        Session session = null;
        String msg = "";
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Client cl = (Client) session.get(Client.class, Integer.toString(id));
            Criteria clientCriteria = session.createCriteria(Task.class);
            clientCriteria.add(Restrictions.eq("index", "1"));
            clientCriteria.add(Restrictions.eq("client", cl));
            String s = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
            clientCriteria.add(Restrictions.le("data", s));
            List<Task> list = (ArrayList<Task>) clientCriteria.list();

            if (list.size() > 0) {
                msg = "Просрочены следующие задачи: ";
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        msg += " " + list.get(i).getName();
                    } else {
                        msg += " ," + list.get(i).getName();
                    }
                }
            } else {
                msg = "Нет просроченных задач";
            }

        } catch (HibernateException ex) {
            log.error("Exception: ", ex);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
            return msg;
        }
    }

    @Override
    public ArrayList<String> returnTask(String id) {
        Session session = null;
        ArrayList<String> result = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Task task = new Task();
            task = (Task) session.get(Task.class, id);
            result.add(task.getId());
            result.add(task.getName());
            result.add(task.getDesc());
            result.add(task.getData());
            result.add(task.getCont());
            result.add(task.getClient().getId());
            session.getTransaction().commit();
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
