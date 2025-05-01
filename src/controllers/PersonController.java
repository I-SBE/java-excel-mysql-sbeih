

package controllers;


import java.util.ArrayList;
import java.util.List;
import models.Dienstleistung;
import models.ExterneMitarbeiter;
import models.Firma;
import models.Kunde;
import models.Mitarbeiter;


/**
 * `PersonController` manages lists of different person types (`Mitarbeiter`, `ExterneMitarbeiter`, `Kunde`, `Firma`, `Dienstleistung`).
 * It provides functions to store, retrieve, and clear person data, and allows saving them to a database.
 *
 * Main Functions:
 * - `addMitarbeiter(Mitarbeiter mitarbeiter)`: Adds a new employee to the list.
 * - `addExterneMitarbeiter(ExterneMitarbeiter externeMitarbeiter)`: Adds a new external employee to the list.
 * - `addKunden(Kunde kunde)`: Adds a new customer to the list.
 * - `addFirmen(Firma firma)`: Adds a new company to the list.
 * - `addDienstleistung(Dienstleistung dienstleistung)`: Adds a new service to the list.
 * - `getMitarbeiterList()`, `getExterneMitarbeiterList()`, `getKundenList()`: Retrieves lists of stored data.
 * - `saveAllToDatabase(DatabaseController dbController)`: Transfers stored data to the database.
 * - `clearAllLists()`: Empties all stored lists.
 *
 * Expected Output:
 * - New persons, companies, and services can be dynamically added to their respective lists.
 * - Data is successfully inserted into the database when `saveAllToDatabase()` is called.
 * - Lists can be cleared when no longer needed.
 */



public class PersonController {

    private List<Mitarbeiter>           mitarbeiterList;
    private List<ExterneMitarbeiter>    externeMitarbeiterList;
    private List<Kunde>                 kundenList;
    private List<Firma>                 firmenList;
    private List<Dienstleistung>        dienstList;


    public PersonController() {

        mitarbeiterList         = new ArrayList<>();
        externeMitarbeiterList  = new ArrayList<>();
        kundenList              = new ArrayList<>();
        firmenList              = new ArrayList<>();
        dienstList              = new ArrayList<>();
    }
    

    public void saveAllToDatabase(DatabaseController dbController) {

        
        for (Mitarbeiter m : mitarbeiterList) {
            String[] values = {
                m.getVorname(), 
                m.getNachname(), 
                m.getStrasse(),
                String.valueOf(m.getHausNr()), 
                String.valueOf(m.getPlz()), 
                m.getOrt(), 
                m.getTelefon(), 
                m.getEmail()
            };

            dbController.insertData("mitarbeiter", values);
            
        }
    
        
        for (ExterneMitarbeiter em : externeMitarbeiterList) {
            
            int firmenId = dbController.getFirmaId(em.getFirma());
            
            if (firmenId == -1) { 

                String[] firmaValues = {em.getFirma()};
                dbController.insertData("firmen", firmaValues);
                firmenId = dbController.getFirmaId(em.getFirma());
            }
            
            
            String[] values = {
                em.getVorname(), 
                em.getNachname(), 
                em.getStrasse(),
                String.valueOf(em.getHausNr()), 
                String.valueOf(em.getPlz()), 
                em.getOrt(), 
                em.getTelefon(), 
                em.getEmail(), 
                String.valueOf(firmenId)
            };

            dbController.insertData("externemitarbeiter", values);

        }
    
        
        for (Kunde k : kundenList) {
            int dienstId = dbController.getDienstleistungId(k.getBranche());
        
            if (dienstId == -1) { 

                String[] dienstValues = {k.getBranche()};
                dbController.insertData("dienstleistungen", dienstValues);
                dienstId = dbController.getDienstleistungId(k.getBranche());
            }
        
            
            String[] values = { 
                k.getVorname(), 
                k.getNachname(), 
                k.getStrasse(),
                String.valueOf(k.getHausNr()), 
                String.valueOf(k.getPlz()), 
                k.getOrt(), 
                k.getTelefon(), 
                k.getEmail(), 
                k.getBranche()
            };

            dbController.insertData("kunden", values);
        
            
            int kundenId = dbController.getKundenId(k.getVorname(), k.getNachname());
            
            String[] kundenDienstValues = {
                String.valueOf(kundenId), 
                String.valueOf(dienstId)
            };
            dbController.insertData("kunden_dienstleistungen", kundenDienstValues);
        }
        
    
        
        for (ExterneMitarbeiter em : externeMitarbeiterList) {

            int firmenId = dbController.getFirmaId(em.getFirma());
            
            for (Kunde k : kundenList) {
                int dienstId = dbController.getDienstleistungId(k.getBranche());
    
                if (firmenId != -1 && dienstId != -1) {
                    String[] firmenDienstValues = {
                        String.valueOf(firmenId), 
                        String.valueOf(dienstId)
                    };
                    dbController.insertData("firmen_dienstleistungen", firmenDienstValues);
                }
            }
        }
    
        
        for (Mitarbeiter m : mitarbeiterList) {

            for (Kunde k : kundenList) {

                int dienstId = dbController.getDienstleistungId(k.getBranche());
                int mitarbeiterId = dbController.getMitarbeiterId(m.getVorname(), m.getNachname());

                
                if (dienstId != -1) {
                    String[] mitarbeiterDienstValues = {
                        String.valueOf(mitarbeiterId),
                        String.valueOf(dienstId)
                    };

                    dbController.insertData("mitarbeiter_dienstleistungen", mitarbeiterDienstValues);

                }
            }
        }
    
        for (ExterneMitarbeiter em : externeMitarbeiterList) {


            int firmenId = dbController.getFirmaId(em.getFirma());
            int exmID = dbController.getExterneMitarbeiterId(String.valueOf(firmenId));
            for (Kunde k : kundenList) {

                int dienstId = dbController.getDienstleistungId(k.getBranche());
                
                if (exmID != -1 && dienstId != -1) {
                    String[] exMitDienstValues = {
                        String.valueOf(exmID), 
                        String.valueOf(dienstId)
                    };
                    dbController.insertData("externemitarbeiter_dienstleistungen", exMitDienstValues);
                }
            }
        }
    }
    
    

    // Methode zum Hinzufügen eines Mitarbeiters zur Liste

    public void addMitarbeiter(Mitarbeiter mitarbeiter) {

        mitarbeiterList.add(mitarbeiter);

    }

    public void addExterneMitarbeiter(ExterneMitarbeiter externeMitarbeiter) {

        externeMitarbeiterList.add(externeMitarbeiter);

    }

    public void addKunden(Kunde kunde) {
        
        kundenList.add(kunde);

    }

    public void addFirmen(Firma firma) {

        firmenList.add(firma);

    }

    public void addDienstleistung(Dienstleistung dienstleistung) {

        dienstList.add(dienstleistung);

    }

    // Methode zum Abrufen der Listen

    public List<Mitarbeiter> getMitarbeiterList() {

        return mitarbeiterList;

    }

    public List<ExterneMitarbeiter> getExterneMitarbeiterList() {

        return externeMitarbeiterList;

    }

    public List<Kunde> getKundenList() {

        return kundenList;

    }

    // Methode zum Leeren der Listen

    public void clearAllLists() {

        mitarbeiterList.clear();
        externeMitarbeiterList.clear();
        kundenList.clear();

    }
}
