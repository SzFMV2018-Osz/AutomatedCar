package hu.oe.nik.szfmv.model;

import hu.oe.nik.szfmv.environment.World;
import hu.oe.nik.szfmv.environment.WorldObject;

import javax.swing.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import hu.oe.nik.szfmv.model.Classes.Dinamikus;
import hu.oe.nik.szfmv.model.Classes.NPC;
import hu.oe.nik.szfmv.model.Classes.Statikus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLRead
{
    private static String imageFileName;
    private static int x;
    private static int y;
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
            Document doc = builder.parse("C:\\Users\\User\\IdeaProjects\\AutomatedCar-A\\src\\main\\resources\\test_world.xml");
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
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return  wordElements;
    }

    private static hu.oe.nik.szfmv.model.Classes.Statikus staticObjBuilder(Element element) throws Exception
    {
        aktualElements = element.getElementsByTagName("Postion");
        for (int i = 0; i < aktualElements.getLength(); i++) {
            Node n = aktualElements.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                x = Integer.parseInt(name.getAttribute("x"));
                y = Integer.parseInt(name.getAttribute("y"));
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

        return new Statikus(x,y,imageFileName,m11,m12,m21,m22);

    }

    private static hu.oe.nik.szfmv.model.Classes.NPC npcBuilder(Element element) throws Exception
    {
        aktualElements = element.getElementsByTagName("Postion");
        for (int i = 0; i < aktualElements.getLength(); i++) {
            Node n = aktualElements.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                x = Integer.parseInt(name.getAttribute("x"));
                y = Integer.parseInt(name.getAttribute("y"));
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

        return new NPC(x,y,imageFileName,m11,m12,m21,m22);
    }
}
