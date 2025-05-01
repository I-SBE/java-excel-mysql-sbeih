

package models;


/**
 * The `Firma` class represents a company in the system.
 * It contains an ID and a name to uniquely identify and describe the company.
 *
 * Main Functions:
 * - `getId()`: Retrieves the company ID.
 * - `setId(int id)`: Sets the company ID.
 * - `getName()`: Retrieves the company name.
 * - `setName(String name)`: Sets the company name.
 */



 
public class Firma {

    private int id;
    private String name;

    public Firma(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
