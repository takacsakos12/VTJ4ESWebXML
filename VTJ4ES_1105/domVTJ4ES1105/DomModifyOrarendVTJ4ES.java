package domVTJ4ES1105;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyOrarendVTJ4ES {

	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		try {
			File inputFile = new File("VTJ4ES_orarend.xml");

			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			Document doc = docBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();

			Node oraNode = doc.getElementsByTagName("ora").item(0);

			if (oraNode != null && oraNode.getNodeType() == Node.ELEMENT_NODE) {
				Element oraElem = (Element) oraNode;
				NodeList letezoOraado = oraElem.getElementsByTagName("oraado");
				if (letezoOraado == null || letezoOraado.getLength() == 0) {
					Element ujOraado = doc.createElement("oraado");
					ujOraado.setTextContent("Dr. Kiss Béla");
					oraElem.appendChild(ujOraado);
				}
			}
			System.out.println("\n--- Strukturált kiírás ---");
			NodeList orak = doc.getElementsByTagName("ora");
			for (int i = 0; i < orak.getLength(); i++) {
				Element e = (Element) orak.item(i);
				String tantargy = getText(e, "tantargy");
				String tipus = getText(e, "tipus");
				String oraado = getText(e, "oraado");
				String idopont = getText(e, "idopont");
				String helyszin = getText(e, "helyszin");

				System.out.println("Óra #" + (i + 1));
				System.out.println("  Tantárgy : " + tantargy);
				System.out.println("  Típus    : " + tipus);
				System.out.println("  Óraadó   : " + (oraado.isEmpty() ? "-" : oraado));
				System.out.println("  Időpont  : " + idopont);
				System.out.println("  Helyszín : " + helyszin);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}



	private static String getText(Element parent, String tag) {
		NodeList nl = parent.getElementsByTagName(tag);
		if (nl == null || nl.getLength() == 0)
			return "";
		return nl.item(0).getTextContent().trim();
	}
}
