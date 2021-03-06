package site.zido.design;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class XmlReader {
    public static Object getObject() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            URL resource = XmlReader.class.getClassLoader().getResource("config.xml");
            if (resource == null) {
                throw new FileNotFoundException("there is not config.xml in resources directory");
            }
            Document doc = builder.parse(new File(resource.getPath()));
            NodeList nl = doc.getElementsByTagName("className");
            Node classNode = nl.item(0).getFirstChild();
            String cName = XmlReader.class.getPackage().getName() + "." + classNode.getNodeValue();
            Class<?> clazz = Class.forName(cName);
            return clazz.newInstance();
        } catch (ParserConfigurationException | IOException | SAXException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
