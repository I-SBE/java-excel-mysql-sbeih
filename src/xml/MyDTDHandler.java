

package xml;


import org.xml.sax.DTDHandler;
import org.xml.sax.SAXException;


/**
 * MyDTDHandler handles DTD (Document Type Definition) declarations in an XML file.
 * It processes notation and unparsed entity declarations, primarily for debugging
 * and analyzing DTD structures.
 *
 * Main Functions:
 * - `notationDecl()`: Logs notation declarations, capturing their name, public ID, and system ID.
 * - `unparsedEntityDecl()`: Logs unparsed entity declarations along with their associated notation.
 *
 * Expected Output:
 * - Prints details of encountered notations and unparsed entities in the XML's DTD.
 * - Aids in debugging by providing insight into the structure and external references of the XML.
 */




public class MyDTDHandler implements DTDHandler {

    @Override
    // Handles notation declarations, logging their name, public ID, and system ID.
    public void notationDecl(String name, String publicId, String systemId) throws SAXException {
        System.out.println("DTD Notation: " + name + " (Public ID: " + publicId + ", System ID: " + systemId + ")");
    }
    

    @Override
    // Handles unparsed entity declarations, logging the entity name and associated notation.
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
        System.out.println("Unparsed Entity: " + name + " (Public ID: " + publicId + ", System ID: " + systemId + ", Notation: " + notationName + ")");
    }
}

