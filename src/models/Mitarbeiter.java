

package models;


/**
 * `Mitarbeiter` is a subclass of `Person` representing an employee.
 * It inherits all attributes and behaviors from `Person` without adding extra fields.
 * 
 * Main Functions:
 * - Constructor: Initializes the employee's attributes using the parent class `Person`.
 * 
 * Expected Output:
 * - When a `Mitarbeiter` object is created, it holds all personal details from `Person`.
 * - No additional attributes or behaviors are introduced in this subclass.
 */



public class Mitarbeiter extends Person {
    
    
    public Mitarbeiter( int     id, 
                        String  vorname, 
                        String  nachname, 
                        String  strasse, 
                        int     hausNr, 
                        int     plz, 
                        String  ort, 
                        String  telefon, 
                        String  email ) {
        super(  id, 
                vorname, 
                nachname, 
                strasse, 
                hausNr, 
                plz, 
                ort, 
                telefon, 
                email   );
    }
}
