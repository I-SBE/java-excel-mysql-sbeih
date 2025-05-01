

package xml;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;


/**
 * MyErrorHandler is a custom SAX error handler for XML parsing.
 * It captures and logs warnings, recoverable errors, and fatal errors.
 *
 * Main Functions:
 * - `warning(SAXParseException exception)`: Logs non-critical warnings but allows parsing to proceed.
 * - `error(SAXParseException exception)`: Logs recoverable errors without terminating the process.
 * - `fatalError(SAXParseException exception)`: Logs severe errors and stops parsing immediately.
 *
 * Expected Output:
 * - If a warning occurs: **"Warning: [message]"** is printed.
 * - If a recoverable error occurs: **"Error: [message]"** is printed.
 * - If a fatal error occurs: **"Fatal Error!: [message]"** is printed, and parsing stops.
 *
 * Additional Handling:
 * - Each method includes exception handling to prevent failures in error reporting itself.
 * - Uses `System.err.println()` for clear distinction of log messages.
 */



public class MyErrorHandler implements ErrorHandler {


    @Override
    // This method is called when the parser encounters a warning
    public void warning(SAXParseException exception) {
        try{
            System.err.println(" Warning: " + exception.getMessage());
        }catch(Exception e){
            System.err.println(" Warning: " + e.getMessage());
        }
    }


    @Override
    // This method is called when the parser encounters an error
    public void error(SAXParseException exception) {
        try{
            System.err.println(" Error: " + exception.getMessage());
        }catch(Exception e){
            System.err.println(" Error: " + e.getMessage());
        }
    }
    

    @Override
    // This method is called when the parser encounters a fatal error
    public void fatalError(SAXParseException exception) {
        try{
            System.err.println(" Fatal Error!: " + exception.getMessage());
        }catch(Exception e){
            System.err.println(" Fatal Error!: " + e.getMessage());
        }
    }
}
