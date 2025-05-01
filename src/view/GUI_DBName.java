

package view;


import java.awt.Font;
import javax.swing.*;
import controllers.DatabaseController;
import controllers.PersonController;
import xml.MyParser;


/**
 * GUI_DBName provides a graphical interface for creating a new database.
 * Users can input a database name, create it, and initialize tables.
 * 
 * Main Functions:
 * - `btn_Create ActionListener`: 
 *   - Validates the input database name.
 *   - Creates a database and initializes necessary tables.
 *   - Parses and saves XML data into the database.
 *   - Closes the window upon successful execution.
 * 
 * - `btn_Cancel ActionListener`: 
 *   - Closes the window without performing any actions.
 * 
 * Expected Output:
 * - If a valid name is provided:
 *   - The database is created successfully.
 *   - Tables are initialized within the database.
 *   - XML data is parsed and stored in the database.
 *   - The window closes after successful operations.
 * 
 * - If no name is entered:
 *   - A validation error message is displayed.
 *   - No changes are made.
 * 
 * - If the process is successful:
 *   - Data is transferred into the database.
 *   - The progress bar (if implemented) is activated.
 * 
 * - If the user cancels:
 *   - The window closes without changes.
 * 
 * Additional Features:
 * - Uses an application icon (`logo.png`).
 * - Custom background and foreground colors for better UI appearance.
 * - Border styling applied to buttons for visual consistency.
 */




public class GUI_DBName extends JFrame {
    private JButton             btn_Create, btn_Cancel;
    private JTextField          input_dbName;
    private JLabel              labelDBName;
    private DatabaseController  dbController;
    private PersonController    personController;


    public GUI_DBName() {


        dbController        = new DatabaseController();
        personController    = new PersonController();

        //adjust size and set layout

        setTitle("Create Database Name");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);



        //initialize components

        labelDBName     = new JLabel("(Enter a Database Name)");
        input_dbName    = new JTextField();
        btn_Create      = new JButton("Create");
        btn_Cancel      = new JButton("Cancel");



        
        //create Background Image and Icon

        ImageIcon icon = new ImageIcon("src/view/images/logo.png");
        setIconImage(icon.getImage());


        //set components properties

        labelDBName.      setBounds(80, 20, 150, 25);
        input_dbName.     setBounds(70, 60, 150, 25);
        btn_Create.          setBounds(40, 110, 80, 30);
        btn_Cancel.         setBounds(160, 110, 80, 30);


        //set component Font styles

        labelDBName.      setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        input_dbName.     setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        btn_Create.          setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        btn_Cancel.         setFont (new java.awt.Font ("Arial", Font.BOLD, 12));


        //set component Foreground colors

        labelDBName.      setForeground (new java.awt.Color(0, 0, 0));
        input_dbName.     setForeground (new java.awt.Color(255, 255, 255));
        btn_Create.         setForeground (new java.awt.Color(255, 255, 255));
        btn_Cancel.         setForeground (new java.awt.Color(255, 255, 255));



        //set component Background colors

        labelDBName.      setBackground (new java.awt.Color(60, 60, 60));
        input_dbName.     setBackground (new java.awt.Color(60, 60, 60));
        btn_Create.          setBackground (new java.awt.Color(60, 60, 60));
        btn_Cancel.         setBackground (new java.awt.Color(0, 0, 255));



        //set component Border

        btn_Create.          setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));
        btn_Cancel.         setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));

        //add components

        add(labelDBName);
        add(input_dbName);
        add(btn_Create);
        add(btn_Cancel);



        
        btn_Create.addActionListener(e -> {
            String dbName = input_dbName.getText().trim();

            if (dbName.isEmpty()) {

                JOptionPane.showMessageDialog(this, "Please enter Database Name.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;

            }


            dbController.createDatabase(dbName);
            dbController.createTables();

            new MyParser("resources/xml/", personController);

            personController.saveAllToDatabase(dbController);
            
            //Progress Bar Activate
            
            dispose();
        });

        btn_Cancel.addActionListener(e -> dispose());
    }

    public String getDatabaseName() {

        return input_dbName.getText().trim();
        
    }
}
