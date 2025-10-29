package DOMVTJ4ES1029;

import java.io.File;
import java.io.IOException;

import javax.xml.*;
import org.xml.sax.SAXException;

import org.w3.dom.Document;
import org.w3.dom.NodeList;
import org.w3.dom.Node;
import org.w3.dom.Element;

public class DomReadVTJ4ES {
	public static void main(String argv[]) throw SAXException, IOException, ParserConfigurationException
	{
	
	File xmlFile = new File("VTJ4ES_hallgato.xml");

    //példányosítás a DocumentBuilderFactory osztályt a statikus newInstance() metódussal.
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    DocumentBuilder dBuilder = factory.newDocumentBuilder();
    //A DocumentBuilderFactory-ból megkapjuk a DocumentBuildert.
    //A DocumentBuilder tartalmazza az API-t a DOM-dokumentum példányok XML-dokumentumból való beszerzéséhez.
}
	//DOM fa előállítása
	Document neptunkod = dBuilder.parse(xmlFile);
	//A parse() metódus elemzi az XML fájlt a Document.

	neptunkod.getDocumentElement().normalize();
	//A dokumentum normalizálása segít a helyes eredmények elérésében.
	//eltávolítja az üres szövegcsomópontokat, és összekapcsolja a szomszédos szövegcsomópontokat.

	System.out.println("Gyökér elem: " + neptunkod.getDocumentElement().getNodeName());
	//Kiiratjuk a dokumentum gyökérelemét

	//a fa megadott névvel (hallgato) rendelkező csomópontjainak összegyűjtése listába.
	//A getElementsByTagName() metódus segítségével megkapjuk a hallgato elem NodeListjét a dokumentumban.
	NodeList nList = neptunkod.getElementsByTagName("hallgato"); //gyerekelemek mentése listába

	for (int i = 0; i < nList.getLength(); i++) {
	    //A listán for ciklussal megyünk végig.

	    //Lekérjük a lista aktuális elemét
	    Node nNode = nList.item(i);

	    System.out.println("\nAktuális elem: " + nNode.getNodeName());

	    if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	        //Elementté konvertáljuk az aktuális elemet
	        Element elem = (Element) nNode;
	    }
	}

	//Lekérjük az aktuális elem attribútumának tartalmát
	String hid = elem.getAttribute("id");
	//Az elem attribútumot a getAttribute() segítségével kapjuk meg.

	//Lekérjük az aktuális elem gyerekelemeit és annak tartalmát
	Node node1 = elem.getElementsByTagName("keresztnev").item(0);
	String kname = node1.getTextContent();

	Node node2 = elem.getElementsByTagName("vezeteknev").item(0);
	String vname = node2.getTextContent();

	Node node3 = elem.getElementsByTagName("foglalkozas").item(0);
	String fname = node3.getTextContent();
	//Megkapjuk a hallgato elem három gyerekelemének tartalmát.

	//Formázva kiíratjuk a lekért információkat az adott elemről
	System.out.println("Hallgató id: " + hid);
	System.out.println("Keresztnév: " + kname);
	System.out.println("Vezetéknév: " + vname);
	System.out.println("Foglalkozás: " + fname);
	    }
}}

}
