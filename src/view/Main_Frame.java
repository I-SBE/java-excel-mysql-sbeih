

package view;


import javax.swing.JFrame;
import javax.swing.JPanel;
import controllers.DatabaseController;
import controllers.PersonController;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;



/**
 * Main_Frame serves as the main application window for ShareEmp Ltd.
 * It manages different panels using a `CardLayout` for smooth navigation between views.
 *
 * Main Functions:
 * - `Main_Frame()`: Initializes the UI, sets up the window, and loads different panels.
 * - `showPanel(String panelName)`: Switches between different views dynamically.
 * - `getGui_Main()`, `getGui_ConvertData()`, `getGui_Database()`: Provides access to GUI panels.
 * - `getDbController()`, `getPersonController()`: Retrieves system-wide controllers for database and person management.
 *
 * Expected Output:
 * - Displays `GUI_Main` as the default interface.
 * - Allows seamless navigation between:
 *   - `GUI_ConvertData`: Handles XML-to-database conversion.
 *   - `GUI_Database`: Manages database tables and records.
 * - Ensures a consistent background image behind all panels.
 * - Maintains a single instance of `DatabaseController` and `PersonController` throughout the system.
 */




public class Main_Frame extends JFrame {
    private CardLayout          cardLayout;
    private JPanel              mainPanel;
    private GUI_Main            gui_Main;
    private GUI_ConvertData     gui_ConvertData;
    private GUI_Database        gui_Database;
    private DatabaseController  dbController;
    private PersonController    pController;

    public Main_Frame() {


        //adjust size and set layout

        setTitle                    ("ShareEmp Ltd.");
        setSize                     (800, 500);
        setDefaultCloseOperation    (JFrame.EXIT_ON_CLOSE);
        setResizable                (false);
        setLocationRelativeTo       (null);




        //create Background Image and Icon

         ImageIcon icon = new ImageIcon("src\\view\\images\\logo.png");
         setIconImage(icon.getImage());
 

         BackgroundImg backgroundImg = new BackgroundImg("src\\view\\images\\BG_Database1.gif");
         backgroundImg.setLayout(new BorderLayout());



         
         //initialize components

         
        dbController        = new DatabaseController();
        pController         = new PersonController();
        cardLayout          = new CardLayout();
        mainPanel           = new JPanel(cardLayout);
        gui_Main            = new GUI_Main(this);
        gui_ConvertData     = new GUI_ConvertData(this);
        gui_Database        = new GUI_Database(this);
        

        mainPanel.add(gui_Main, "gui_Main");
        mainPanel.add(gui_ConvertData, "ConvertData");
        mainPanel.add(gui_Database, "Database");

        backgroundImg.add(mainPanel);
        setContentPane(backgroundImg);
        setVisible(true);
        mainPanel.setOpaque(false);

    }

    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
    


    public GUI_Main getGui_Main() {
        return gui_Main;
    }

    public GUI_ConvertData getGui_ConvertData() {
        return gui_ConvertData;
    }

    public GUI_Database getGui_Database() {
        return gui_Database;
    }
    
    public DatabaseController getDbController() {
        return dbController;
    }

    public PersonController getPersonController() {
        return pController;
    }

}
