package ro.ase.semdam_1085;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ExtractXML extends AsyncTask<URL, Void, InputStream> {
    InputStream ist=null;
    public List<Student> studentList;
    @Override
    protected InputStream doInBackground(URL... urls) {
        HttpsURLConnection connection=null;
        try {
            connection = (HttpsURLConnection) urls[0].openConnection();
            connection.setRequestMethod("GET");
            ist = connection.getInputStream();

            // 1) varianta 1: cand se parseaza -> parsare xml
            studentList = parsareXML(ist);

            // 2) varianta 2: conversie stream
            // (se poate prelucra o singura data)
//            InputStreamReader isr = new InputStreamReader(ist);
//            BufferedReader br = new BufferedReader(isr);
//            String linie = null;
//            while((linie = br.readLine())!=null)
//            {
//                rezultat += linie;
//            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ist;
    }

    public static Node getNodeByName(String nodeName, Node parentNode) throws Exception {

        if (parentNode.getNodeName().equals(nodeName)) {
            return parentNode;
        }

        NodeList list = parentNode.getChildNodes();
        for (int i = 0; i < list.getLength(); i++)
        {
            Node node = getNodeByName(nodeName, list.item(i));
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    public static String getAttributeValue(Node node, String attrName) {
        try {
            return ((Element)node).getAttribute(attrName);
        }
        catch (Exception e) {
            return "";
        }
    }
    public List<Student> parsareXML(InputStream ist)
    {
        List<Student> list = new ArrayList<>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document domDoc = db.parse(ist);
            domDoc.getDocumentElement().normalize();

            Node cube = getNodeByName("Studenti", domDoc.getDocumentElement());
            if(cube!=null)
            {
                NodeList listaCopii = cube.getChildNodes();

                for (int j = 0; j < listaCopii.getLength(); j++)
                {
                    Node copil = listaCopii.item(j); // ne pozitionam pe un nod din ala student
                    if (copil!=null && copil.getNodeName().equals("Student"))
                    {
                        Student student = new Student();

                        NodeList taguri = copil.getChildNodes();
                        for (int i = 0; i < taguri.getLength(); i++)
                        {
                            Node node = taguri.item(i);

                            // etragerm pt nodul tag atributul
                            String atribut = getAttributeValue(node, "atribut");

                            if (atribut.equals("Nume"))
                            {
                                student.setNume(node.getTextContent());
                            } else if (atribut.equals("DataNasterii"))
                            {
                                student.setDataNasterii(new Date(node.getTextContent()));
                            } else if (atribut.equals("Medie"))
                            {
                                student.setMedie(Float.parseFloat(node.getTextContent()));
                            } else if (atribut.equals("Facultate"))
                            {
                                student.setFacultate(node.getTextContent());
                            }
                            else if (atribut.equals("AreBursa"))
                            {
                                student.setAreBursa(Boolean.parseBoolean(node.getTextContent()));
                            }
                        }
                        list.add(student);
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
