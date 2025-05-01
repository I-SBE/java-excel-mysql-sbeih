

package view;


import javax.swing.*;
import controllers.DatabaseController;
import java.awt.Font;
import java.util.Locale;


/**
 * GUI_Main serves as the main control panel for database management in ShareEmp Ltd.
 * It provides navigation to login, database editing, and data conversion functionalities.
 *
 * Main Functions:
 * - `GUI_Main()`: Initializes the main UI, components, and sets default connection status.
 * - `setConnectionStatus()`: Updates and displays the current database connection status.
 * - `setDatabases(String[] databases)`: Populates the dropdown list with available databases.
 * - `getSelectedDatabase()`: Returns the currently selected database name.
 * - `btn_Login ActionListener`: Opens the login window for database authentication.
 * - `btn_EditeDB ActionListener`: Allows editing database tables if a connection is established.
 * - `btn_ConvertData ActionListener`: Navigates to the data conversion panel if logged in.
 * - `dbDropdown ActionListener`: Switches the active database and updates the UI.
 * - `calculatorItem & notepadItem ActionListener`: Launches system utilities.
 *
 * Expected Output:
 * - Displays connection status (Connected/Not Connected).
 * - Updates available databases in the dropdown after login.
 * - Prevents unauthorized access to database features.
 * - Enables navigation between database and data conversion functionalities.
 */



 

public class GUI_Main extends JPanel {

    // Components

    private static  GUI_Main            instance;
    private         JMenuBar            toolBar;
    private         JMenu               toolsMenu, helpMenu;
    private         JMenuItem           calculatorItem, notepadItem;
    private         JMenuItem           contentsItem, contact_us_Item, aboutItem;
    private         JButton             btn_Login, btn_EditeDB, btn_ConvertData;
    private static  JLabel              labelConnection;
    private static  JComboBox<String>   dbDropdown = new JComboBox<>();
    private static  DatabaseController  dbController;
    // private static  String[]            databases;
    private static  boolean             isdbDropdownInitialized = false;
    private         Main_Frame          mainFrame;
    private static  String              dropDefault;

    public GUI_Main(Main_Frame mainFrame) {
        
        this.mainFrame = mainFrame;
        GUI_Main.instance = this;

        setLayout(null);
        setOpaque(false);

        
        dbController    = this.mainFrame.getDbController();
        Locale.setDefault(Locale.ENGLISH);


        

        //initialize components

        // dbDropdown             = new JComboBox<>();
        toolBar             = new JMenuBar();
        toolsMenu           = new JMenu("Tools");
        calculatorItem      = new JMenuItem("Calculator");
        notepadItem         = new JMenuItem("Notepad");
        helpMenu            = new JMenu("Help");
        contentsItem        = new JMenuItem("Contents");
        contact_us_Item     = new JMenuItem("Contact us!");
        aboutItem           = new JMenuItem("About");
        btn_Login           = new JButton("Login to Databases");
        btn_EditeDB         = new JButton("Edite Data Base");
        btn_ConvertData     = new JButton("Convert Excel to Data Bases");
        labelConnection     = new JLabel();
        dropDefault         = "No Database Selected!";
        

        //set components properties

        labelConnection.    setOpaque(true);

        toolBar.            setBounds(0, 0, 950, 20);
        dbDropdown.         setBounds(10, 80, 250, 20);
        labelConnection.    setBounds(10, 35, 150, 15);
        btn_Login.          setBounds(230, 120, 300, 30);
        btn_EditeDB.        setBounds(230, 200, 300, 30);
        btn_ConvertData.    setBounds(230, 280, 300, 30);



        
        //set component Font styles

        btn_Login.          setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        btn_EditeDB.        setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        btn_ConvertData.    setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        labelConnection.    setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        dbDropdown.         setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        toolsMenu.          setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        helpMenu.           setFont (new java.awt.Font ("Arial", Font.BOLD, 12));



        //set component Foreground colors

        btn_Login.          setForeground (new java.awt.Color(255, 255, 255));
        btn_EditeDB.        setForeground (new java.awt.Color(255, 255, 255));
        btn_ConvertData.    setForeground (new java.awt.Color(255, 255, 255));
        dbDropdown.         setForeground (new java.awt.Color(255, 255, 255));
        toolsMenu.          setForeground (new java.awt.Color(255, 255, 255));
        helpMenu.           setForeground (new java.awt.Color(255, 255, 255));

        
        //set component Background colors

        btn_Login.          setBackground (new java.awt.Color(60, 60, 60));
        btn_EditeDB.        setBackground (new java.awt.Color(60, 60, 60));
        btn_ConvertData.    setBackground (new java.awt.Color(60, 60, 60));
        labelConnection.    setBackground (new java.awt.Color(60, 60, 60));
        dbDropdown.         setBackground (new java.awt.Color(60, 60, 60));
        toolBar.            setBackground (new java.awt.Color(60, 60, 60));


        //set component Border
        btn_Login.          setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));
        btn_EditeDB.        setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));
        btn_ConvertData.    setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));



        

        //add components
        
        toolsMenu.  add(calculatorItem);
        toolsMenu.  add(notepadItem);
        helpMenu.   add(contentsItem);
        helpMenu.   add(contact_us_Item);
        helpMenu.   add(aboutItem);

        toolBar.    add(toolsMenu);
        toolBar.    add(helpMenu);

        add(toolBar);
        add(btn_Login);
        add(btn_EditeDB);
        add(btn_ConvertData);
        add(labelConnection);
        add(dbDropdown);
        dbDropdown.addItem("Login & Choose a Database!");


        


        //Button action listeners

        calculatorItem.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("calc.exe");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        notepadItem.addActionListener(e -> {
            try {
                Runtime.getRuntime().exec("notepad.exe");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        btn_Login.addActionListener(e -> new GUI_Login().setVisible(true));

        btn_EditeDB.addActionListener(e -> {
            if (!dbController.isConnected()) {
                JOptionPane.showMessageDialog(this, "Please Login to Databases!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (dbDropdown.getSelectedItem().equals(dropDefault)) {
                JOptionPane.showMessageDialog(this, "Please Choose a Database!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            GUI_Database.setDatabaseLabel();
            GUI_Database guiDatabase = this.mainFrame.getGui_Database();
            guiDatabase.setTablesList();
            this.mainFrame.showPanel("Database");
        });

        btn_ConvertData.addActionListener(e -> {
            if (!dbController.isConnected()) {
                JOptionPane.showMessageDialog(this, "Please Login to Databases!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            this.mainFrame.showPanel("ConvertData");
        });


        dbDropdown.addActionListener(e -> {
            if (!isdbDropdownInitialized) {
                return;
            }
        
            String dbName = getSelectedDatabase();
            if (dbName == null || dbName.equals(dropDefault)) {
                System.out.println("No database selected, exiting.");
                return;
            }
        
            System.out.println("Switching to database: " + dbName);
            dbController.setshowDialog(true);
            dbController.changeDatabase(dbName);
        
            GUI_Database guiDatabase = this.mainFrame.getGui_Database();
            guiDatabase.setTablesList();
        });
        
    
        if (dbController.isConnected()) {
            setDatabases(dbController.getDatabaseList());
        }
        setConnectionStatus();
    }

    public static void setConnectionStatus() {
        if (dbController.isConnected()) {
            labelConnection.setText("Database: Connected");
            labelConnection.setForeground(new java.awt.Color(0, 255, 0));
        } else {
            labelConnection.setText("Database: Not Connected");
            labelConnection.setForeground(new java.awt.Color(255, 0, 0));
        }
    }

    public static void setDatabases(String[] databases) {
        isdbDropdownInitialized=false;
        dbDropdown.removeAllItems();
        dbDropdown.addItem(dropDefault);
        if(databases != null){
            for (String db : databases) {
                dbDropdown.addItem(db);
            }
        }
        isdbDropdownInitialized=true;
    }

    //Getter

    public String getSelectedDatabase() {
        return (String) dbDropdown.getSelectedItem();
    }

    public static GUI_Main getInstance() {
        if (instance == null) {
            throw new IllegalStateException("GUI_Main has not been initialized yet! Ensure it's created in Main_Frame.");
        }
        return instance;
    }
    

}
