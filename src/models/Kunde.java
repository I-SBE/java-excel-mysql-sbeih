

package models;


/**
 * `Kunde` is a subclass of `Person` representing a customer.
 * It extends the base class by adding a `branche` field to store the industry sector.
 * 
 * Main Functions:
 * - Constructor: Initializes all attributes, including `branche`, using the parent class `Person`.
 * - `getBranche()`: Returns the industry sector associated with the customer.
 * - `setBranche(String branche)`: Updates the industry sector.
 * 
 * Expected Output:
 * - When a `Kunde` object is created, it inherits all `Person` attributes.
 * - The industry sector (`branche`) can be retrieved or modified using the getter and setter methods.
 */



public class Kunde extends Person {

    private String branche;
    
    public Kunde(  int id,
                    String vorname, 
                    String nachname, 
                    String strasse, 
                    int hausNr, 
                    int plz, 
                    String ort, 
                    String telefon, 
                    String email, 
                    String branche  ) {

        super(  id, 
                vorname, 
                nachname, 
                strasse, 
                hausNr, 
                plz, 
                ort, 
                telefon, 
                email   );

        this.branche = branche;
    }

    public String getBranche() {

        return branche;
    }

    public void setBranche(String branche) {

        this.branche = branche;

    }
}
