package pac;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pac.logic.Task;

public class OperationsTasks {

    private static final Logger log = Logger.getLogger(OperationsTasks.class);

    public static String regClient(String name, String login, String password) {
        return Factory.getInstance().getClientDAO().regClient(name, login, password);
    }

    //метод проверяет какие задачи оказались просроченными
    public static String overdueTask(String s_id) {
        return Factory.getInstance().getTaskDAO().overdueTask(Integer.parseInt(s_id));
    }

    public static ArrayList<String> returnTask(String id) {
        return Factory.getInstance().getTaskDAO().returnTask(id);
    }

    public static void getTask(int id) {
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

        } catch (IOException | ParserConfigurationException | SAXException ex) {
            log.error("Exception: ", ex);
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
        } catch (SQLException | NamingException ex) {
            log.error("Exception: ", ex);
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
            fos = new FileOutputStream("C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\result.xml");
            StreamResult result = new StreamResult(fos);
            trf.transform(src, result);
        } catch (TransformerException | IOException ex) {
            log.error("Exception: ", ex);
        }
        log.debug("getTask(int id)");
    }

    public static void taskXSLT() {
        try {
            String s1 = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\mapping.xsl";
            String s2 = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\web\\import.html";
            String s3 = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\result.xml";
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(new StreamSource(s1));
            FileOutputStream fout = new FileOutputStream(s2);
            StreamSource ss = new StreamSource(s3);
            StreamResult sr = new StreamResult(fout);
            transformer.transform(ss, sr);
            Thread.sleep(2000);
            log.debug("taskXSLT()");
        } catch (TransformerConfigurationException ex) {
            log.error("Exception: ", ex);
        } catch (TransformerException | FileNotFoundException ex) {
            log.error("Exception: ", ex);
        } catch (IOException | InterruptedException ex) {
            log.error("Exception: ", ex);
        }
    }

    public static boolean checkXMLforXSD() {
        try {
            String xml = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\result.xml";
            String xsd = "C:\\Users\\DNS\\Desktop\\NetCracker-master\\WebLab3\\src\\java\\pac\\xmlSchema.xsd";
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(xsd));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xml));
            log.debug("checkXMLforXSD()");
            return true;
        } catch (SAXException | IOException ex) {
            log.error("Exception: ", ex);
            return false;
        }
    }

    public static void toExcel(ArrayList<Task> list) {
        try {
            HSSFWorkbook book = new HSSFWorkbook();
            HSSFSheet sheet = book.createSheet("Tasks");

            Row row1 = sheet.createRow(0);
            row1.createCell(0).setCellValue("Name");
            row1.createCell(1).setCellValue("Description");
            row1.createCell(2).setCellValue("Time");
            row1.createCell(3).setCellValue("Contacts");
            for (int i = 0; i < list.size(); i++) {
                Row row2 = sheet.createRow(i + 1);
                row2.createCell(0).setCellValue(list.get(i).getName());
                row2.createCell(1).setCellValue(list.get(i).getDesc());
                row2.createCell(2).setCellValue(list.get(i).getData());
                row2.createCell(3).setCellValue(list.get(i).getCont());
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            FileOutputStream out = new FileOutputStream(new File("D:\\Apache POI Excel File.xls"));
            book.write(out);
            book.close();
            log.debug("toExcel");
        } catch (IOException ex) {
            log.error("Exception: ", ex);
        }

    }
}
