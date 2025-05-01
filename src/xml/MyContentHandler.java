

package xml;


import java.util.HashSet;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import controllers.PersonController;
import models.*;


/**
 * MyContentHandler is a SAX-based XML parser that extracts and processes person-related data.
 * It dynamically creates `Kunde`, `Mitarbeiter`, and `ExMitarbeiter` objects and associates
 * them with `Dienstleistung` and `Firma` entities.
 * 
 * Main Functions:
 * - `startElement()`: Detects XML element start, resets variables, and identifies entity type.
 * - `characters()`: Captures text content inside XML elements.
 * - `endElement()`: Assigns parsed values, creates objects, and adds them to `PersonController`.
 * - `endDocument()`: Ensures all parsed `Firma` and `Dienstleistung` objects are stored.
 * - `resetVariables()`: Clears temporary data between elements to prevent carryover.
 * 
 * Expected Output:
 * - Extracted data is converted into Java objects and added to `PersonController`.
 * - Handles missing or empty values by assigning defaults where necessary (`N/A`).
 * - Prints parsed content for debugging.
 * - Ensures unique `Firma` and `Dienstleistung` objects using `Set`.
 */



public class MyContentHandler extends DefaultHandler {

    private PersonController    personController;
    private StringBuilder       currentValue        = new StringBuilder();
    private String              currentType;
    private Set<Firma>          firmenSet           = new HashSet<>();
    private Set<Dienstleistung> dienstSet           = new HashSet<>();

    
    private int                 id, hausNr, plz;
    private String              vorname, nachname, strasse, ort, telefon, email, firma, branche;


    // Constructor
    public MyContentHandler(PersonController personController) {
        this.personController = personController;
    }


    @Override
    // This method is called at the start of the document

    public void startDocument() throws SAXException {
        // System.out.println(" Start Read XML...\n");
    }


    @Override
    // This method is called at the end of the document

    public void endDocument() throws SAXException {
        // System.out.println(" Readed the XML Successfully.");
        for (Firma f : firmenSet) {
            personController.addFirmen(f);
        }
    
        for (Dienstleistung d : dienstSet) {
            personController.addDienstleistung(d);
        }
    }


    @Override
    // This method is called at the start of an element

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        currentValue.setLength(0);
        if (qName.equalsIgnoreCase("Kunde") || 
            qName.equalsIgnoreCase("Mitarbeiter") || 
            qName.equalsIgnoreCase("ExMitarbeiter")) {

            resetVariables();
            currentType = qName;
        }
        System.out.print("\n"+localName);

    }


    @Override
    // This method is called at the end of an element

    public void endElement(String uri, String localName, String qName) throws SAXException {
        String value = currentValue.toString().trim();
        if (!value.isEmpty()) {
            switch (qName) {
                case "ID":
                    id = Integer.parseInt(value);
                    break;
                case "Vorname":
                    vorname = value;
                    break;
                case "Nachname":
                    nachname = value;
                    break;
                case "Strasse":
                    strasse = value;
                    break;
                case "HausNr":
                    hausNr = Integer.parseInt(value);;
                    break;
                case "PLZ":
                    plz = Integer.parseInt(value);;
                    break;
                case "Ort":
                    ort = value;
                    break;
                case "Telefon":
                    telefon = value != null ? value : "N/A";
                    break;
                case "eMail":
                    email = value != null ? value : "N/A";
                    break;
                case "Branche":
                    if(currentType.equalsIgnoreCase("Kunde")){
                        branche = value;
                        dienstSet.add(new Dienstleistung(id, branche));
                    }
                    break;
                case "Firma":
                    if(currentType.equalsIgnoreCase("ExMitarbeiter")){
                            firma = value;
                            firmenSet.add(new Firma(0, firma));
                    }
                    break;
            }
        }

        
         if (qName.equalsIgnoreCase("Kunde")) {
            
            Kunde kunde = new Kunde(id, vorname, nachname, strasse, hausNr, plz, ort, 
                                    telefon, 
                                    email, 
                                    branche);
            personController.addKunden(kunde);

        } else if (qName.equalsIgnoreCase("Mitarbeiter")) {

            Mitarbeiter mitarbeiter = new Mitarbeiter(  id, vorname, nachname, strasse, hausNr, plz, ort, 
                                                        telefon != null ? telefon : "N/A", 
                                                        email != null ? email : "N/A");
            personController.addMitarbeiter(mitarbeiter);

        } else if (qName.equalsIgnoreCase("ExMitarbeiter")) {

            ExterneMitarbeiter externeMitarbeiter = new ExterneMitarbeiter( id, vorname, nachname, strasse, hausNr, plz, ort, 
                                                                            telefon != null ? telefon : "N/A", 
                                                                            email != null ? email : "N/A",
                                                                            firma);
            personController.addExterneMitarbeiter(externeMitarbeiter);

        }

    }


    @Override
    // This method is called when the parser encounters character data

    public void characters(char[] ch, int start, int length) throws SAXException {

        currentValue.append(ch, start, length);
        String data = new String(ch, start, length).trim();
        if (!data.isEmpty()) {
            System.out.println("    Content: " + data);
        }

    }

    // This method is called when the parser encounters the end of an element
    private void resetVariables() {

        id = hausNr = plz = 0;
        telefon = email = "N/A";
        vorname = nachname = strasse = ort = firma = branche = "";

    }
    
}
