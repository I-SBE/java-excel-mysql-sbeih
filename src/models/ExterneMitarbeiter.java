

package models;


/**
 * The `ExterneMitarbeiter` class represents an external employee in the system.
 * It extends the `Person` class by adding a `firma` field to store the company name.
 *
 * Main Functions:
 * - Constructor: Initializes all attributes, including `firma`, using the parent class `Person`.
 * - `getFirma()`: Returns the company name associated with the external employee.
 * - `setFirma(String firma)`: Updates the company name.
 *
 * Expected Output:
 * - When an `ExterneMitarbeiter` object is created, it inherits all `Person` attributes.
 * - The company name (`firma`) can be retrieved or modified using the getter and setter methods.
 */



public class ExterneMitarbeiter extends Person {

    private String firma;

    public ExterneMitarbeiter(  int     id, 
                                String  vorname, 
                                String  nachname, 
                                String  strasse,  
                                int     hausNr, 
                                int     plz, 
                                String  ort, 
                                String  telefon, 
                                String  email, 
                                String  firma ) {
        
        super(  id, 
                vorname, 
                nachname, 
                strasse, 
                hausNr, 
                plz, 
                ort, 
                telefon, 
                email   );

        this.firma = firma;
    
    }

    public String getFirma() {

        return firma;
        
    }

    public void setFirma(String firma) {

        this.firma = firma;

    }
}
