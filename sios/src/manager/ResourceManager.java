package manager;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * User: parshin
 * Date: 04.10.11
 * Time: 13:56
 */
public class ResourceManager {

    private static HashMap<String, String> resources = new HashMap<String, String>();

    public static void init(File file) {
        try {
            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            f.setValidating(false);
            DocumentBuilder builder = f.newDocumentBuilder();
            Document doc = builder.parse(file);
            NodeList nParams = doc.getElementsByTagName("param");
            for (int i = 0; i < nParams.getLength(); i++) {
                Node n = nParams.item(i);
                try {
                    resources.put(n.getAttributes().getNamedItem("id").getTextContent(), n.getTextContent().trim());
                } catch (Exception e) {
                    //пропуск
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getParam(String param) {
        return resources.get(param);
    }

    public static void main(String[] args) {
        ResourceManager.init(new File("settings.xml"));
        for (Map.Entry<String, String> e : resources.entrySet()) {
            System.out.print(e.getKey() + " - ");
            System.out.println(e.getValue());
        }
    }

}
