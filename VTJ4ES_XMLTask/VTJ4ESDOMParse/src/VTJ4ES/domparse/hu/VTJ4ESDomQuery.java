package VTJ4ES.domparse.hu;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class VTJ4ESDomQuery {

    // Main method
    public static void main(String[] argv) throws SAXException, IOException, ParserConfigurationException {

        File xmlFile = new File("../VTJ4ES_XML.xml");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder dBuilder = factory.newDocumentBuilder();

        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();

        StringBuilder outputBuilder = new StringBuilder();

        // Query 1: Athletes with skill = "speed"
        NodeList athleteList = doc.getElementsByTagName("Athlete");
        outputBuilder.append("<AthletesWithSpeed>\n");
        for (int i = 0; i < athleteList.getLength(); i++) {
            Node node = athleteList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element athlete = (Element) node;

                // Check if this athlete has a "skills" element with value "speed"
                NodeList skills = athlete.getElementsByTagName("skills");
                boolean hasSpeed = false;
                for (int j = 0; j < skills.getLength(); j++) {
                    String skillValue = skills.item(j).getTextContent();
                    if ("speed".equalsIgnoreCase(skillValue)) {
                        hasSpeed = true;
                        break;
                    }
                }

                if (hasSpeed) {
                    String athleteId = athlete.getAttribute("athleteID");
                    Element nameElem = (Element) athlete.getElementsByTagName("name").item(0);
                    String firstName = nameElem.getElementsByTagName("first_name").item(0).getTextContent();
                    String lastName = nameElem.getElementsByTagName("last_name").item(0).getTextContent();
                    String birthDate = athlete.getElementsByTagName("birth_date").item(0).getTextContent();

                    outputBuilder.append(String.format("  <Athlete athleteID=\"%s\">\n", athleteId));
                    outputBuilder.append(String.format("    <first_name>%s</first_name>\n", firstName));
                    outputBuilder.append(String.format("    <last_name>%s</last_name>\n", lastName));
                    outputBuilder.append(String.format("    <birth_date>%s</birth_date>\n", birthDate));
                    outputBuilder.append("    <skills>speed</skills>\n");
                    outputBuilder.append("  </Athlete>\n");
                }
            }
        }
        outputBuilder.append("</AthletesWithSpeed>\n");

        // Query 2: Training groups with sport = "running"
        NodeList groupList = doc.getElementsByTagName("TrainingGroup");
        outputBuilder.append("\n<RunningGroups>\n");
        for (int i = 0; i < groupList.getLength(); i++) {
            Node node = groupList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element group = (Element) node;
                String sport = group.getElementsByTagName("sport").item(0).getTextContent();

                if ("running".equalsIgnoreCase(sport)) {
                    String groupId = group.getAttribute("groupID");
                    String level = group.getElementsByTagName("level").item(0).getTextContent();
                    String name = group.getElementsByTagName("name").item(0).getTextContent();

                    Element schedule = (Element) group.getElementsByTagName("schedule").item(0);
                    String weekday = schedule.getElementsByTagName("weekday").item(0).getTextContent();
                    String startTime = schedule.getElementsByTagName("start_time").item(0).getTextContent();
                    String endTime = schedule.getElementsByTagName("end_time").item(0).getTextContent();

                    outputBuilder.append(String.format("  <TrainingGroup groupID=\"%s\">\n", groupId));
                    outputBuilder.append(String.format("    <name>%s</name>\n", name));
                    outputBuilder.append(String.format("    <sport>%s</sport>\n", sport));
                    outputBuilder.append(String.format("    <level>%s</level>\n", level));
                    outputBuilder.append("    <schedule>\n");
                    outputBuilder.append(String.format("      <weekday>%s</weekday>\n", weekday));
                    outputBuilder.append(String.format("      <start_time>%s</start_time>\n", startTime));
                    outputBuilder.append(String.format("      <end_time>%s</end_time>\n", endTime));
                    outputBuilder.append("    </schedule>\n");
                    outputBuilder.append("  </TrainingGroup>\n");
                }
            }
        }
        outputBuilder.append("</RunningGroups>\n");
        
        // Query 3: Competitions with degree = "national"
        NodeList competitionList = doc.getElementsByTagName("Competition");
        outputBuilder.append("\n<NationalCompetitions>\n");
        for (int i = 0; i < competitionList.getLength(); i++) {
            Node node = competitionList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element comp = (Element) node;
                String degree = comp.getElementsByTagName("degree").item(0).getTextContent();

                if ("national".equalsIgnoreCase(degree)) {
                    String compId = comp.getAttribute("compID");
                    String name = comp.getElementsByTagName("name").item(0).getTextContent();

                    Element dateInfo = (Element) comp.getElementsByTagName("date_info").item(0);
                    String date = dateInfo.getElementsByTagName("date").item(0).getTextContent();
                    String startTime = dateInfo.getElementsByTagName("start_time").item(0).getTextContent();

                    outputBuilder.append(String.format("  <Competition compID=\"%s\">\n", compId));
                    outputBuilder.append(String.format("    <name>%s</name>\n", name));
                    outputBuilder.append(String.format("    <degree>%s</degree>\n", degree));
                    outputBuilder.append("    <date_info>\n");
                    outputBuilder.append(String.format("      <date>%s</date>\n", date));
                    outputBuilder.append(String.format("      <start_time>%s</start_time>\n", startTime));
                    outputBuilder.append("    </date_info>\n");
                    outputBuilder.append("  </Competition>\n");
                }
            }
        }
        outputBuilder.append("</NationalCompetitions>\n");

        // Query 4: Location with the largest capacity
        NodeList locationList = doc.getElementsByTagName("Location");
        int maxCapacity = -1;
        Element maxLocation = null;

        for (int i = 0; i < locationList.getLength(); i++) {
            Node node = locationList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element location = (Element) node;
                String capacityText = location.getElementsByTagName("capacity").item(0).getTextContent();
                int capacity = 0;
                try {
                    capacity = Integer.parseInt(capacityText.trim());
                } catch (NumberFormatException e) {
                    // Ignore invalid capacity values
                }

                if (capacity > maxCapacity) {
                    maxCapacity = capacity;
                    maxLocation = location;
                }
            }
        }

        if (maxLocation != null) {
            String locationId = maxLocation.getAttribute("locationID");
            String name = maxLocation.getElementsByTagName("name").item(0).getTextContent();
            String capacityStr = maxLocation.getElementsByTagName("capacity").item(0).getTextContent();

            Element profile = (Element) maxLocation.getElementsByTagName("profile").item(0);
            String surface = profile.getElementsByTagName("surface").item(0).getTextContent();
            String profileText = profile.getElementsByTagName("profile_text").item(0).getTextContent();

            outputBuilder.append("\n<LargestLocation>\n");
            outputBuilder.append(String.format("  <Location locationID=\"%s\">\n", locationId));
            outputBuilder.append(String.format("    <name>%s</name>\n", name));
            outputBuilder.append(String.format("    <capacity>%s</capacity>\n", capacityStr));
            outputBuilder.append("    <profile>\n");
            outputBuilder.append(String.format("      <surface>%s</surface>\n", surface));
            outputBuilder.append(String.format("      <profile_text>%s</profile_text>\n", profileText));
            outputBuilder.append("    </profile>\n");
            outputBuilder.append("  </Location>\n");
            outputBuilder.append("</LargestLocation>\n");
        }

        // Query 5: Athletes born after year 2000
        outputBuilder.append("\n<AthletesBornAfter2000>\n");
        for (int i = 0; i < athleteList.getLength(); i++) {
            Node node = athleteList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element athlete = (Element) node;
                String birthDate = athlete.getElementsByTagName("birth_date").item(0).getTextContent();

                // Expecting format YYYY-MM-DD
                int birthYear = 0;
                if (birthDate != null && birthDate.length() >= 4) {
                    try {
                        birthYear = Integer.parseInt(birthDate.substring(0, 4));
                    } catch (NumberFormatException e) {
                        birthYear = 0;
                    }
                }

                if (birthYear > 2000) {
                    String athleteId = athlete.getAttribute("athleteID");
                    Element nameElem = (Element) athlete.getElementsByTagName("name").item(0);
                    String firstName = nameElem.getElementsByTagName("first_name").item(0).getTextContent();
                    String lastName = nameElem.getElementsByTagName("last_name").item(0).getTextContent();

                    outputBuilder.append(String.format("  <Athlete athleteID=\"%s\">\n", athleteId));
                    outputBuilder.append(String.format("    <first_name>%s</first_name>\n", firstName));
                    outputBuilder.append(String.format("    <last_name>%s</last_name>\n", lastName));
                    outputBuilder.append(String.format("    <birth_date>%s</birth_date>\n", birthDate));
                    outputBuilder.append("  </Athlete>\n");
                }
            }
        }
        outputBuilder.append("</AthletesBornAfter2000>\n");

        // Print result XML-like text to console
        System.out.println(outputBuilder.toString());
    }
}
