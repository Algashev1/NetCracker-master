package pac;

import java.io.File;
import java.io.FileNotFoundException;
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
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

    public static void getTaskList(int id) {
        Document mapDoc = null;
        Document dataDoc = null;
        Document newDoc = null;
        Connection conn;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
            mapDoc = docbuilder.parse("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\mapping.xml");
            dataDoc = docbuilder.newDocument();
            newDoc = docbuilder.newDocument();

        } catch (Exception e) {
            System.out.println("Problem creating document: " + e.getMessage());
        }
        Element mapRoot = mapDoc.getDocumentElement();
        String sql = "SELECT t_index, t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i') as t_data, t_contacts FROM Task WHERE u_id = ? AND t_parent is NULL";

        ResultSetMetaData resultmetadata = null;
        Element dataRoot = dataDoc.createElement("data");
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, id);
            ResultSet result = pStatement.executeQuery();
            resultmetadata = result.getMetaData();
            int numCols = resultmetadata.getColumnCount();
            while (result.next()) {
                Element rowEl = dataDoc.createElement("row");
                for (int i = 1; i <= numCols; i++) {
                    String colName = resultmetadata.getColumnName(i);
                    String colVal = result.getString(i);
                    if (result.wasNull()) {
                        colVal = "null";
                    }
                    Element dataEl = dataDoc.createElement(colName);
                    dataEl.appendChild(dataDoc.createTextNode(colVal));
                    rowEl.appendChild(dataEl);
                }
                dataRoot.appendChild(rowEl);
            }
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Closing connections...");
        }
        dataDoc.appendChild(dataRoot);
        Element newRootInfo = (Element) mapRoot.getElementsByTagName("root").item(0);
        String newRootName = newRootInfo.getAttribute("name");
        String newRowName = newRootInfo.getAttribute("rowName");
        NodeList newNodesMap = mapRoot.getElementsByTagName("element");
        Element newRootElement = newDoc.createElement(newRootName);
        NodeList oldRows = dataRoot.getElementsByTagName("row");
        for (int i = 0; i < oldRows.getLength(); i++) {
            Element thisRow = (Element) oldRows.item(i);
            Element newRow = newDoc.createElement(newRowName);
            for (int j = 0; j < newNodesMap.getLength(); j++) {
                Element thisElement = (Element) newNodesMap.item(j);
                String newElementName = thisElement.getAttribute("name");
                Element oldElement = (Element) thisElement.getElementsByTagName("content").item(0);
                String oldField = oldElement.getFirstChild().getNodeValue();
                Element oldValueElement = (Element) thisRow.getElementsByTagName(oldField).item(0);
                String oldValue = oldValueElement.getFirstChild().getNodeValue();
                Element newElement = newDoc.createElement(newElementName);
                newElement.appendChild(newDoc.createTextNode(oldValue));
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
                newRow.appendChild(newElement);
            }
            newRootElement.appendChild(newRow);
        }
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
    }

    public static void getTask(int id) {
        Document mapDoc = null;
        Document dataDoc = null;
        Document newDoc = null;
        Connection conn;
        try {
            DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docbuilder = dbfactory.newDocumentBuilder();
            mapDoc = docbuilder.parse("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\mapping2.xml");
            dataDoc = docbuilder.newDocument();
            newDoc = docbuilder.newDocument();

        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println(e.getMessage());
        }
        Element mapRoot = mapDoc.getDocumentElement();
        String sql = "SELECT t_name, t_description, date_format(t_data, '%Y-%m-%d %H:%i') AS t_data, t_contacts FROM Task WHERE t_id = ?";

        ResultSetMetaData resultmetadata = null;
        Element dataRoot = dataDoc.createElement("data");
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");
            conn = ds.getConnection();
            PreparedStatement pStatement = conn.prepareStatement(sql);
            pStatement.setInt(1, id);
            ResultSet result = pStatement.executeQuery();
            resultmetadata = result.getMetaData();
            int numCols = resultmetadata.getColumnCount();
            while (result.next()) {
                Element rowEl = dataDoc.createElement("row");
                for (int i = 1; i <= numCols; i++) {
                    String colName = resultmetadata.getColumnName(i);
                    String colVal = result.getString(i);
                    if (result.wasNull()) {
                        colVal = "null";
                    }
                    Element dataEl = dataDoc.createElement(colName);
                    dataEl.appendChild(dataDoc.createTextNode(colVal));
                    rowEl.appendChild(dataEl);
                }
                dataRoot.appendChild(rowEl);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (NamingException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Closing connections...");
        }
        dataDoc.appendChild(dataRoot);
        Element newRootInfo = (Element) mapRoot.getElementsByTagName("root").item(0);
        String newRootName = newRootInfo.getAttribute("name");
        String newRowName = newRootInfo.getAttribute("rowName");
        NodeList newNodesMap = mapRoot.getElementsByTagName("element");
        Element newRootElement = newDoc.createElement(newRootName);
        NodeList oldRows = dataRoot.getElementsByTagName("row");
        for (int i = 0; i < oldRows.getLength(); i++) {
            Element thisRow = (Element) oldRows.item(i);
            Element newRow = newDoc.createElement(newRowName);
            for (int j = 0; j < newNodesMap.getLength(); j++) {
                Element thisElement = (Element) newNodesMap.item(j);
                String newElementName = thisElement.getAttribute("name");
                Element oldElement = (Element) thisElement.getElementsByTagName("content").item(0);
                String oldField = oldElement.getFirstChild().getNodeValue();
                Element oldValueElement = (Element) thisRow.getElementsByTagName(oldField).item(0);
                String oldValue = oldValueElement.getFirstChild().getNodeValue();
                Element newElement = newDoc.createElement(newElementName);
                newElement.appendChild(newDoc.createTextNode(oldValue));
                NodeList newAttributes = thisElement.getElementsByTagName("attribute");
                newRow.appendChild(newElement);
            }
            newRootElement.appendChild(newRow);
        }
        newDoc.appendChild(newRootElement);

        Transformer trf = null;
        DOMSource src = null;
        FileOutputStream fos = null;
        try {
            trf = TransformerFactory.newInstance().newTransformer();
            src = new DOMSource(newDoc);
            fos = new FileOutputStream("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\test2.xml");

            StreamResult result = new StreamResult(fos);
            trf.transform(src, result);
        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void taskXSLT() {
        try {
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\mapping.xsl"));
            transformer.transform(new StreamSource("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\test.xml"), new StreamResult(new FileOutputStream("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\web\\test.html")));

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException | FileNotFoundException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void taskXSLT2() {
        try {
            String s1 = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\mapping2.xsl";
            String s2 = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\web\\test2.html";
            String s3 = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\test2.xml";
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(s1));
            FileOutputStream fout = new FileOutputStream(s2);
            StreamSource ss = new StreamSource(s3);
            StreamResult sr = new StreamResult(fout);
            transformer.transform(ss, sr);
            Thread.sleep(2000);
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException | FileNotFoundException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(OperationsTasks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean checkXMLforXSD() {
        try {
            String xml = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\test.xml";
            String xsd = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\xmlSchema.xsd";

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            return true;
        } catch (SAXException | IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
