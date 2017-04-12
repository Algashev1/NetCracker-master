package pac;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class OperationsTasks {

    public static String regClient(String name, String login, String password) {
        Connection conn;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "INSERT INTO Client VALUE (NULL, ?,  ?, ?)";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, name);
            pStatement.setString(2, login);
            pStatement.setString(3, password);
            pStatement.execute();
            pStatement.close();
            ctx.close();
            conn.close();
            return "Регистрация прошла успешно!";
        } catch (SQLException | NamingException e) {
            return "Регистрация не прошла! Попробуйте снова!";
        }
    }

    //метод проверяет какие задачи оказались просроченными
    public static String overdueTask(String s_id) {
        Connection conn;
        String message;
        Date d = new Date();
        ArrayList<String> list = new ArrayList<>();
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT t_name FROM Task WHERE t_index = 1 AND t_data < ? AND u_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            String s = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(d);
            pStatement.setString(1, s);
            pStatement.setString(2, s_id);
            ResultSet result = pStatement.executeQuery();
            while (result.next()) {
                list.add(result.getObject(1).toString());
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();

            if ((list != null) && (0 < list.size())) {
                message = "Просрочены следующие задачи:";
                for (int i = 0; i < list.size(); i++) {
                    if (i == 0) {
                        message += " " + list.get(i);
                    } else {
                        message += " ," + list.get(i);
                    }
                }
            } else {
                message = "Нет просроченных задач";
            }
            return message;
        } catch (SQLException | NamingException e) {
            return e.getMessage();
        }
    }

    public static ArrayList<String> returnTask(String id) {
        Connection conn;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            String sql = "SELECT t_id, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i'), t_contacts, u_id FROM Task WHERE t_id = ?";
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setString(1, id);
            ResultSet result = pStatement.executeQuery();
            ArrayList<String> list = new ArrayList<>();
            if (result.next()) {
                list.add(result.getString(1));
                list.add(result.getString(2));
                list.add(result.getString(3));
                list.add(result.getString(4));
                list.add(result.getString(5));
                list.add(result.getString(6));
            }
            result.close();
            pStatement.close();
            ctx.close();
            conn.close();
            return list;
        } catch (SQLException | NamingException e) {
            return null;
        }
    }

    public static void getTaskList() {
        //Создание объекта Document 
        Document mapDoc = null;
        //Определение нового объекта Document 
        Document dataDoc = null;
        //Создание нового Document
        Document newDoc = null;
        Connection conn;
        try {
            //Создание DocumentBuilderFactory
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            // Создание DocumentBuilder
            DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
            //Разбор файла для создания Document
            mapDoc = docbuilder.parse("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\mapping.xml");
            // Создание нового экземпляра Document 
            dataDoc = docbuilder.newDocument();
            // Создание нового объекта Document
            newDoc = docbuilder.newDocument();

        } catch (Exception e) {
            System.out.println("Problem creating document: " + e.getMessage());
        }
        //Выборка корневого элемента
        Element mapRoot = mapDoc.getDocumentElement();
        //Выборка (только) элемента данных и преобразование его в Element
        Node dataNode = mapRoot.getElementsByTagName("data").item(0);
        Element dataElement = (Element) dataNode;
        //Выборка оператора sql 
        String sql = dataElement.getAttribute("sql");
        //Вывод SQL-оператора
        System.out.println(sql);

        ResultSetMetaData resultmetadata = null;
        // Создание нового элемента с именем "data"
        Element dataRoot = dataDoc.createElement("data");
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();  
            PreparedStatement pStatement = conn.prepareStatement(sql);
            ResultSet result = pStatement.executeQuery();
            resultmetadata = result.getMetaData();
            int numCols = resultmetadata.getColumnCount();
            while (result.next()) {
                //Для каждой строки данных
                // Создание нового элемента с именем "row"
                Element rowEl = dataDoc.createElement("row");
                for (int i = 1; i <= numCols; i++) {
                    // Для каждого столбца, выборка имени и данных
                    String colName = resultmetadata.getColumnName(i);
                    String colVal = result.getString(i);
                    //Если нет данных, добавить "null"
                    if (result.wasNull()) {
                        colVal = "null";
                    }
                    // Создание нового элемента с тем же именем, что и у столбца
                    Element dataEl = dataDoc.createElement(colName);
                    //Добавление данных к новому элементу
                    dataEl.appendChild(dataDoc.createTextNode(colVal));
                    // Добавление нового элемента к строке
                    rowEl.appendChild(dataEl);
                }
                //Добавление строки к корневому элементу
                dataRoot.appendChild(rowEl);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Closing connections...");
        }
        //Добавление корневого элемента к документу
        dataDoc.appendChild(dataRoot);      
        //Выборка корневого элемента (называвемого "root")
        Element newRootInfo = (Element) mapRoot.getElementsByTagName("root").item(0);
        // Выборка информации о корне и строке
        String newRootName = newRootInfo.getAttribute("name");
        String newRowName = newRootInfo.getAttribute("rowName");
        // Выборка информации об элементе, встраиваемом в новый документ
        NodeList newNodesMap = mapRoot.getElementsByTagName("element");
        //Создание конечного корневого элемента с именем из файла отображения
        Element newRootElement = newDoc.createElement(newRootName);
        //Выборка всех строк в старом документе
        NodeList oldRows = dataRoot.getElementsByTagName("row");
        for (int i = 0; i < oldRows.getLength(); i++) {
            //Выборка каждой строки
            Element thisRow = (Element) oldRows.item(i);
            //Создание новой строки
            Element newRow = newDoc.createElement(newRowName);
            for (int j = 0; j < newNodesMap.getLength(); j++) {
                //Для каждого узла в новом отображении, выборка информации
                //Сначала новая информация...
                Element thisElement = (Element) newNodesMap.item(j);
                String newElementName = thisElement.getAttribute("name");
                //Затем старая информация
                Element oldElement = (Element) thisElement.getElementsByTagName("content").item(0);
                String oldField = oldElement.getFirstChild().getNodeValue();
                //Получение исходных значений на основе информации отображения
                Element oldValueElement = (Element) thisRow.getElementsByTagName(oldField).item(0);
                String oldValue = oldValueElement.getFirstChild().getNodeValue();
                //Создание нового элемента
                Element newElement = newDoc.createElement(newElementName);
                newElement.appendChild(newDoc.createTextNode(oldValue));
                //Выборка списка элементов
                NodeList newAttributes = thisElement.getElementsByTagName("attribute");
//                for (int k = 0; k < newAttributes.getLength(); k++) {
//                    //Для каждого нового атрибута
//                    //Получение информации отображения
//                    Element thisAttribute = (Element) newAttributes.item(k);
//                    String oldAttributeField = thisAttribute.getFirstChild().getNodeValue();
//                    String newAttributeName = thisAttribute.getAttribute("name");
//                    //Получение исходного значения
//                    oldValueElement = (Element) thisRow.getElementsByTagName(oldAttributeField).item(0);
//                    String oldAttributeValue = oldValueElement.getFirstChild().getNodeValue();
//                    //Создание нового атрибута
//                    newElement.setAttribute(newAttributeName, oldAttributeValue);
//                }
                //Добавление нового элемента к новой строке
                newRow.appendChild(newElement);
            }
            //Добавление новой строки к корню
            newRootElement.appendChild(newRow);
        }
        //Добавление нового корня к документу
        newDoc.appendChild(newRootElement);

        Transformer trf = null;
        DOMSource src = null;
        FileOutputStream fos = null;
        try {
            trf = TransformerFactory.newInstance().newTransformer();
            src = new DOMSource(newDoc);
            fos = new FileOutputStream("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\test.xml");

            StreamResult result = new StreamResult(fos);
            trf.transform(src, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }

        int a = 0;
    }
}
