package VTJ4ES.domparse.hu;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

import java.io.File;

public class VTJ4ESDomModify {

    // Main method
    public static void main(String[] argv) {
        try {
            // Input XML file (place VTJ4ES_XML.xml in the project root)
            File inputFile = new File("..//VTJ4ES_XML.xml");

            // Build DOM document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            docFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Perform modifications
            modifyAthletes(doc);
            modifyMemberships(doc);
            modifyLocations(doc);

            // Print modified DOM to console as formatted XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Modify Athlete elements: prefix athleteID with "ATH_"
    private static void modifyAthletes(Document doc) {
        NodeList athleteList = doc.getElementsByTagName("Athlete");

        for (int i = 0; i < athleteList.getLength(); i++) {
            Node node = athleteList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element athlete = (Element) node;
                String id = athlete.getAttribute("athleteID");

                // Avoid double prefixing if run multiple times
                if (id != null && !id.startsWith("ATH_")) {
                    athlete.setAttribute("athleteID", "ATH_" + id);
                }
            }
        }
    }

    // Modify Membership elements: increase monthly_price by 1000
    private static void modifyMemberships(Document doc) {
        NodeList membershipList = doc.getElementsByTagName("Membership");

        for (int i = 0; i < membershipList.getLength(); i++) {
            Node node = membershipList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element membership = (Element) node;

                Node priceNode = membership.getElementsByTagName("monthly_price").item(0);
                if (priceNode != null && priceNode.getNodeType() == Node.ELEMENT_NODE) {
                    String priceText = priceNode.getTextContent().trim();
                    try {
                        int price = Integer.parseInt(priceText);
                        price += 1000; // increase by 1000
                        priceNode.setTextContent(String.valueOf(price));
                    } catch (NumberFormatException e) {
                        // If the value is not a valid integer, leave it unchanged
                    }
                }
            }
        }
    }

    // Modify Location elements:
    // - ensure capacity is at least 100
    // - append " (updated)" to profile_text
    private static void modifyLocations(Document doc) {
        NodeList locationList = doc.getElementsByTagName("Location");

        for (int i = 0; i < locationList.getLength(); i++) {
            Node node = locationList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element location = (Element) node;

                // Adjust capacity to be at least 100
                Node capacityNode = location.getElementsByTagName("capacity").item(0);
                if (capacityNode != null && capacityNode.getNodeType() == Node.ELEMENT_NODE) {
                    String capacityText = capacityNode.getTextContent().trim();
                    try {
                        int capacity = Integer.parseInt(capacityText);
                        if (capacity < 100) {
                            capacity = 100;
                        }
                        capacityNode.setTextContent(String.valueOf(capacity));
                    } catch (NumberFormatException e) {
                        // If the value is not a valid integer, do not change it
                    }
                }

                // Append " (updated)" to profile_text inside profile
                NodeList profileList = location.getElementsByTagName("profile");
                if (profileList.getLength() > 0) {
                    Element profile = (Element) profileList.item(0);
                    Node profileTextNode = profile.getElementsByTagName("profile_text").item(0);
                    if (profileTextNode != null && profileTextNode.getNodeType() == Node.ELEMENT_NODE) {
                        String oldText = profileTextNode.getTextContent();
                        if (!oldText.endsWith(" (updated)")) {
                            profileTextNode.setTextContent(oldText + " (updated)");
                        }
                    }
                }
            }
        }
    }
}
