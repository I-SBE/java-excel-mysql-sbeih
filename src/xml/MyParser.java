

package xml;


import java.io.File;
import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.XMLReader;
import controllers.PersonController;


/**
 * MyParser is a SAX-based XML parser designed for efficient processing of XML files.
 * It supports DTD validation and extracts structured data for further processing.
 * 
 * Main Functions:
 * - `MyParser(String directoryPath, PersonController personController)`: 
 *   - Scans the specified directory for XML files and processes each one.
 *   - Displays an error dialog if no XML files are found.
 * - `ParseFile(String xmlFilePath)`: 
 *   - Initializes and configures a SAX parser.
 *   - Assigns handlers for content extraction, error logging, and DTD resolution.
 *   - Parses the XML file and outputs success/failure messages.
 * 
 * Expected Output:
 * - If XML files exist, they are parsed successfully, and "Analyse XML Success" is printed.
 * - If no XML files are found, an error message is displayed in a **JOptionPane**.
 * - If a parsing error occurs, the stack trace is printed to `System.err`.
 * 
 * Handlers Used:
 * - `MyContentHandler`: Extracts and processes XML content.
 * - `MyErrorHandler`: Captures and logs XML parsing warnings/errors.
 * - `MyDTDHandler`: Handles DTD-related events.
 * - `MyEntityResolver`: Resolves external DTD files.
 * 
 * Additional Features:
 * - Uses a **validating SAX parser** to enforce XML structure consistency.
 * - Supports **namespace-aware parsing**.
 */




public class MyParser {

    private static  PersonController    personController;

    // Constructor
    public MyParser(String directoryPath, PersonController personController) {
        
        MyParser.personController       = personController;
        File    folder                  = new File(directoryPath);
        File[]  xmFiles                 = folder.listFiles((dir, name)-> name.toLowerCase().endsWith(".xml"));
        if(xmFiles == null || xmFiles.length == 0){
            JOptionPane.showMessageDialog(null, "No XML files found in directory: " + directoryPath, "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        for(File xmlFile : xmFiles){
            ParseFile(xmlFile.getAbsolutePath());
        }
        
    }
    
    // Parse a single XML file
    public void ParseFile(String xmlFilePath){

        try {

            // Create a new SAXParserFactory

            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);


            // Create a new SAXParser

            SAXParser saxParser = factory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();


            // Set the ContentHandler, ErrorHandler, DTDHandler and EntityResolver

            xmlReader.setDTDHandler(new MyDTDHandler());
            xmlReader.setContentHandler(new MyContentHandler(MyParser.personController));
            xmlReader.setErrorHandler(new MyErrorHandler());
            xmlReader.setEntityResolver(new MyEntityResolver());


            // Parse the XML file
            System.out.println("\n\n Parser Started \n\n");
            xmlReader.parse(xmlFilePath);

            System.out.println("\n\n Analyse XML Success\n\n");

        } catch (Exception e) {
            System.err.println(" Error Reading XML:");
            e.printStackTrace();
        }
    }
}
