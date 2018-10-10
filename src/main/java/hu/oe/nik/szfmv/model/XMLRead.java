package hu.oe.nik.szfmv.model;

import hu.oe.nik.szfmv.environment.WorldObject;
import hu.oe.nik.szfmv.model.Classes.NPC;
import hu.oe.nik.szfmv.model.Classes.Statikus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class XMLRead
{
    private static String imageFileName;
    private static float x;
    private static float y;
    private static String m11;
    private static String m12;
    private static String m21;
    private static String m22;

    private static NodeList aktualElements;


    public static List<WorldObject> Read()
    {
        List<WorldObject> wordElements = new ArrayList<WorldObject>();

        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/main/resources/test_world.xml");
            NodeList objectList = doc.getElementsByTagName("Object");
            for (int i = 0; i < objectList.getLength(); i++)
            {
                Node o = objectList.item(i);
                if (o.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) o;
                    imageFileName = element.getAttribute("type");
                    if (imageFileName.contains("woman") || imageFileName.contains("bicycle")
                    || imageFileName.contains("man") || imageFileName.contains("car"))
                    {
                        wordElements.add(npcBuilder(element));
                    }
                    else
                    {
                        wordElements.add(staticObjBuilder(element));
                    }
                }
            }
            return  wordElements;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return null;
        }
    }

    private static hu.oe.nik.szfmv.model.Classes.Statikus staticObjBuilder(Element element) throws Exception
    {
        builderComponents(element);
        return new Statikus((int)x,(int)y,imageFileName,m11,m12,m21,m22);
    }

    private static hu.oe.nik.szfmv.model.Classes.NPC npcBuilder(Element element) throws Exception
    {
        builderComponents(element);
        return new NPC((int)x,(int)y,imageFileName,m11,m12,m21,m22);
    }

    private static void builderComponents(Element element)
    {
        aktualElements = element.getElementsByTagName("Position");
        for (int i = 0; i < aktualElements.getLength(); i++) {
            Node n = aktualElements.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                x = Float.parseFloat(name.getAttribute("x"));
                y = Float.parseFloat(name.getAttribute("y"));
            }
        }
        aktualElements = element.getElementsByTagName("Transform");
        for (int j = 0; j < aktualElements.getLength(); j++) {
            Node n = aktualElements.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                m11 = name.getAttribute("m11");
                m12 = name.getAttribute("m12");
                m21 = name.getAttribute("m21");
                m22 = name.getAttribute("m22");
            }
        }
    }
}
