

package models;


/**
 * The `Dienstleistung` class represents a service in the system.
 * It contains an ID and a name to uniquely identify and describe the service.
 *
 * Main Functions:
 * - `getId()`: Retrieves the service ID.
 * - `setId(int id)`: Sets the service ID.
 * - `getName()`: Retrieves the service name.
 * - `setName(String name)`: Sets the service name.
 */



 
public class Dienstleistung {
    private int id;
    private String name;

    public Dienstleistung(int id, String name) {
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

