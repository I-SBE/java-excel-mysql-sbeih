

package xml;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


/**
 * MyEntityResolver is a custom entity resolver designed for handling external DTD files.
 * It ensures that XML parsers correctly locate and load required DTDs from a predefined local directory.
 *
 * Main Functions:
 * - `resolveEntity()`: Extracts the DTD filename from the system identifier (URI), checks its existence
 *   in the "resources/dtd/" directory, and returns an `InputSource` for parsing.
 *
 * Expected Output:
 * - If the DTD file is found:
 *   - Prints the resolved file path.
 *   - Loads the file into the parser.
 * - If the DTD file is missing:
 *   - Logs an error message with the expected file path.
 *   - Throws a `SAXException` indicating the file could not be located.
 */




public class MyEntityResolver implements EntityResolver {
    
    @Override
    // Resolve the entity by checking the existence of the DTD file in the "resources/dtd/" directory
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {

        System.out.println("Resolving DTD: " + systemId);

        String dtdFileName  = new File(systemId).getName();
        File dtdFile        = new File("resources/dtd/" + dtdFileName);

        if (dtdFile.exists()) {

            System.out.println("DTD Found: " + dtdFile.getAbsolutePath());
            return new InputSource(new FileInputStream(dtdFile));

        } else {

            System.err.println("Error: DTD file not found -> " + dtdFile.getAbsolutePath());
            throw new SAXException("DTD file not found: " + dtdFile.getAbsolutePath());

        }
    }
}
