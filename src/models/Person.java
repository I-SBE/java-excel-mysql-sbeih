

package models;


/**
 * `Person` is a base class representing a general person entity.
 * It stores personal details such as name, address, and contact information.
 * 
 * Main Functions:
 * - Constructor: Initializes all personal attributes.
 * - Getters and Setters: Provides access and modification methods for attributes.
 * 
 * Expected Output:
 * - When a `Person` object is created, it contains essential personal details.
 * - This class serves as a parent for specific person types (e.g., `Kunde`, `Mitarbeiter`).
 */


public class Person {
    
    protected int       id;
    protected String    vorname;
    protected String    nachname;
    protected String    strasse;
    protected int       hausNr;
    protected int       plz;
    protected String    ort;
    protected String    telefon;
    protected String    email;
    
    public Person(  int     id, 
                    String  vorname, 
                    String  nachname, 
                    String  strasse, 
                    int     hausNr, 
                    int     plz, 
                    String  ort, 
                    String  telefon, 
                    String  email   ) {

        this.id         = id;
        this.vorname    = vorname;
        this.nachname   = nachname;
        this.strasse    = strasse;
        this.hausNr     = hausNr;
        this.plz        = plz;
        this.ort        = ort;
        this.telefon    = telefon;
        this.email      = email;
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVorname() {
        return vorname;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public String getNachname() {
        return nachname;
    }

    public void setNachname(String nachname) {
        this.nachname = nachname;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHausNr() {
        return hausNr;
    }

    public void setHausNr(int hausNr) {
        this.hausNr = hausNr;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
