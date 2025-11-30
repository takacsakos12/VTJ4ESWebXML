package VTJ4ES.domparse.hu;

import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import org.w3c.dom.*;
import java.io.*;

public class VTJ4ESDomRead {

    // Main method
    public static void main(String[] args) {
        try {
            // Create DOM parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load XML file (change path if needed)
            Document document = builder.parse(new File("../VTJ4ES_XML.xml"));


            // Normalize document
            document.getDocumentElement().normalize();

            // Print XML declaration and root start tag with XSD reference
            System.out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            System.out.println("<Sport_Club_VTJ4ES xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"../VTJ4ES_XMLSchema.xsd\">\n");

            // Read and print each entity type
            readAthletes(document);
            readMemberships(document);
            readCoaches(document);
            readLocations(document);
            readTrainingGroups(document);
            readCompetitions(document);

            // Closing root tag
            System.out.println("\n</Sport_Club_VTJ4ES>");

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    // Read Athlete nodes
    private static void readAthletes(Document document) {
        NodeList athleteList = document.getElementsByTagName("Athlete");
        for (int i = 0; i < athleteList.getLength(); i++) {
            Node node = athleteList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                String athleteId = e.getAttribute("athleteID");

                Element nameElem = (Element) e.getElementsByTagName("name").item(0);
                String firstName = nameElem.getElementsByTagName("first_name").item(0).getTextContent();
                String lastName = nameElem.getElementsByTagName("last_name").item(0).getTextContent();

                String birthDate = e.getElementsByTagName("birth_date").item(0).getTextContent();

                Element contactsElem = (Element) e.getElementsByTagName("contacts").item(0);
                String email = contactsElem.getElementsByTagName("e_mail").item(0).getTextContent();
                String phone = contactsElem.getElementsByTagName("phone_number").item(0).getTextContent();

                System.out.println("    <Athlete athleteID=\"" + athleteId + "\">");
                System.out.println("        <name>");
                printElement("first_name", firstName);
                printElement("last_name", lastName);
                System.out.println("        </name>");
                printElement("birth_date", birthDate);
                System.out.println("        <contacts>");
                printElement("e_mail", email);
                printElement("phone_number", phone);
                System.out.println("        </contacts>");

                // Multivalued 'skills'
                NodeList skills = e.getElementsByTagName("skills");
                for (int j = 0; j < skills.getLength(); j++) {
                    String skill = skills.item(j).getTextContent();
                    printElement("skills", skill);
                }

                System.out.println("    </Athlete>");
            }
        }
    }

    // Read Membership nodes
    private static void readMemberships(Document document) {
        NodeList membershipList = document.getElementsByTagName("Membership");
        for (int i = 0; i < membershipList.getLength(); i++) {
            Node node = membershipList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                String membershipId = e.getAttribute("membershipID");
                String type = e.getElementsByTagName("type").item(0).getTextContent();
                String monthlyPrice = e.getElementsByTagName("monthly_price").item(0).getTextContent();

                System.out.println("    <Membership membershipID=\"" + membershipId + "\">");
                printElement("type", type);
                printElement("monthly_price", monthlyPrice);

                // Multivalued 'benefits'
                NodeList benefits = e.getElementsByTagName("benefits");
                for (int j = 0; j < benefits.getLength(); j++) {
                    String benefit = benefits.item(j).getTextContent();
                    printElement("benefits", benefit);
                }

                System.out.println("    </Membership>");
            }
        }
    }

    // Read Coach nodes
    private static void readCoaches(Document document) {
        NodeList coachList = document.getElementsByTagName("Coach");
        for (int i = 0; i < coachList.getLength(); i++) {
            Node node = coachList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                String coachId = e.getAttribute("coachID");

                Element nameElem = (Element) e.getElementsByTagName("name").item(0);
                String firstName = nameElem.getElementsByTagName("first_name").item(0).getTextContent();
                String lastName = nameElem.getElementsByTagName("last_name").item(0).getTextContent();

                String experienceYears = e.getElementsByTagName("experience_years").item(0).getTextContent();

                System.out.println("    <Coach coachID=\"" + coachId + "\">");
                System.out.println("        <name>");
                printElement("first_name", firstName);
                printElement("last_name", lastName);
                System.out.println("        </name>");
                printElement("experience_years", experienceYears);

                // Multivalued 'specializations'
                NodeList specs = e.getElementsByTagName("specializations");
                for (int j = 0; j < specs.getLength(); j++) {
                    String spec = specs.item(j).getTextContent();
                    printElement("specializations", spec);
                }

                System.out.println("    </Coach>");
            }
        }
    }

    // Read Location nodes
    private static void readLocations(Document document) {
        NodeList locationList = document.getElementsByTagName("Location");
        for (int i = 0; i < locationList.getLength(); i++) {
            Node node = locationList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                String locationId = e.getAttribute("locationID");
                String name = e.getElementsByTagName("name").item(0).getTextContent();
                String capacity = e.getElementsByTagName("capacity").item(0).getTextContent();

                Element profileElem = (Element) e.getElementsByTagName("profile").item(0);
                String surface = profileElem.getElementsByTagName("surface").item(0).getTextContent();
                String profileText = profileElem.getElementsByTagName("profile_text").item(0).getTextContent();

                System.out.println("    <Location locationID=\"" + locationId + "\">");
                printElement("name", name);
                printElement("capacity", capacity);

                System.out.println("        <profile>");
                printElement("surface", surface);
                printElement("profile_text", profileText);
                System.out.println("        </profile>");

                // Multivalued 'equipment'
                NodeList equipments = e.getElementsByTagName("equipment");
                for (int j = 0; j < equipments.getLength(); j++) {
                    String eq = equipments.item(j).getTextContent();
                    printElement("equipment", eq);
                }

                System.out.println("    </Location>");
            }
        }
    }

    // Read TrainingGroup nodes
    private static void readTrainingGroups(Document document) {
        NodeList groupList = document.getElementsByTagName("TrainingGroup");
        for (int i = 0; i < groupList.getLength(); i++) {
            Node node = groupList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                String groupId = e.getAttribute("groupID");
                String sport = e.getElementsByTagName("sport").item(0).getTextContent();
                String level = e.getElementsByTagName("level").item(0).getTextContent();
                String name = e.getElementsByTagName("name").item(0).getTextContent();

                Element scheduleElem = (Element) e.getElementsByTagName("schedule").item(0);
                String weekday = scheduleElem.getElementsByTagName("weekday").item(0).getTextContent();
                String startTime = scheduleElem.getElementsByTagName("start_time").item(0).getTextContent();
                String endTime = scheduleElem.getElementsByTagName("end_time").item(0).getTextContent();

                System.out.println("    <TrainingGroup groupID=\"" + groupId + "\">");
                printElement("sport", sport);
                printElement("level", level);
                printElement("name", name);

                System.out.println("        <schedule>");
                printElement("weekday", weekday);
                printElement("start_time", startTime);
                printElement("end_time", endTime);
                System.out.println("        </schedule>");

                System.out.println("    </TrainingGroup>");
            }
        }
    }

    // Read Competition nodes
    private static void readCompetitions(Document document) {
        NodeList compList = document.getElementsByTagName("Competition");
        for (int i = 0; i < compList.getLength(); i++) {
            Node node = compList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) node;

                String compId = e.getAttribute("compID");
                String name = e.getElementsByTagName("name").item(0).getTextContent();
                String degree = e.getElementsByTagName("degree").item(0).getTextContent();

                Element dateInfoElem = (Element) e.getElementsByTagName("date_info").item(0);
                String date = dateInfoElem.getElementsByTagName("date").item(0).getTextContent();
                String startTime = dateInfoElem.getElementsByTagName("start_time").item(0).getTextContent();

                System.out.println("    <Competition compID=\"" + compId + "\">");
                printElement("name", name);
                printElement("degree", degree);

                System.out.println("        <date_info>");
                printElement("date", date);
                printElement("start_time", startTime);
                System.out.println("        </date_info>");

                System.out.println("    </Competition>");
            }
        }
    }

    // Helper method to print a simple element on one line
    private static void printElement(String elementName, String content) {
        System.out.println("        <" + elementName + ">" + content + "</" + elementName + ">");
    }
}
