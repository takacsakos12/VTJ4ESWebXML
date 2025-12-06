package domneptunkod1015;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class DOMRead {

    public static void main(String[] args) {
        String fileName = "orarendNeptunkod.xml";

        try {
            // DOM parser létrehozása
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document doc = builder.parse(new File(fileName));
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            System.out.println("Gyökérelem: " + root.getNodeName());
            System.out.println("=====================================");
            NodeList oraList = doc.getElementsByTagName("ora");

            for (int i = 0; i < oraList.getLength(); i++) {
                Element oraElem = (Element) oraList.item(i);
                String id = oraElem.getAttribute("id");
                String tipus = oraElem.getAttribute("tipus");

                String targy = getTextContentOfTag(oraElem, "targy");
                String helyszin = getTextContentOfTag(oraElem, "helyszin");
                String oktato = getTextContentOfTag(oraElem, "oktato");
                String szak = getTextContentOfTag(oraElem, "szak");

                Element idopontElem = (Element) oraElem.getElementsByTagName("idopont").item(0);
                String nap = getTextContentOfTag(idopontElem, "nap");
                String tol = getTextContentOfTag(idopontElem, "tol");
                String ig = getTextContentOfTag(idopontElem, "ig");

                System.out.println("Óra blokk:");
                System.out.println("  ID: " + id);
                System.out.println("  Típus: " + tipus);
                System.out.println("  Tárgy: " + targy.trim());
                System.out.println("  Időpont:");
                System.out.println("    Nap: " + nap.trim());
                System.out.println("    Tól: " + tol.trim());
                System.out.println("    Ig:  " + ig.trim());
                System.out.println("  Helyszín: " + helyszin.trim());
                System.out.println("  Oktató: " + oktato.trim());
                System.out.println("  Szak: " + szak.trim());
                System.out.println("-------------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Hiba történt a DOM feldolgozás során:");
            e.printStackTrace();
        }
    }

    private static String getTextContentOfTag(Element parent, String tagName) {
        if (parent == null) {
            return "";
        }
        NodeList list = parent.getElementsByTagName(tagName);
        if (list.getLength() == 0) {
            return "";
        }
        return list.item(0).getTextContent();
    }
}
