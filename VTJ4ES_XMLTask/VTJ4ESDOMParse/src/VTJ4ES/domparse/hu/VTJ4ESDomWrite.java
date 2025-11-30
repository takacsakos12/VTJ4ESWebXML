package VTJ4ES.domparse.hu;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class VTJ4ESDomWrite {

    // Main method
    public static void main(String[] args) {
        try {
            // Input XML file (place VTJ4ESXML.xml in the project root)
            File inputFile = new File("../VTJ4ES_XML.xml");

            // Create DOM parser
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse XML into DOM
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Print XML declaration
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

            // Print DOM content to console
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult consoleResult = new StreamResult(System.out);
            transformer.transform(source, consoleResult);

            // Write DOM content to a new XML file
            writeDocumentToFile(doc, "../VTJ4ES_XML1.xml");

            System.out.println("\nThe content has been written to VTJ4ESXML1.xml successfully.");

        } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // Write DOM document to output file
    private static void writeDocumentToFile(Document doc, String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }
}
