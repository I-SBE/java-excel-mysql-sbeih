

package view;


import javax.swing.*;
import controllers.DatabaseController;
import controllers.PersonController;
import xml.MyParser;
import java.awt.*;


/**
 * GUI_ConvertData provides a graphical interface for converting XML data into a database.
 * Users can select XML folders, specify database names, and initiate the conversion process.
 * 
 * Main Functions:
 * - `openItem ActionListener`: Allows selecting an XML folder.
 * - `exitItem ActionListener`: Exits the application.
 * - `calculatorItem ActionListener`: Launches the system calculator.
 * - `notepadItem ActionListener`: Launches the system notepad.
 * - `btnOpenFile ActionListener`: Displays the selected folder path.
 * - `btn_ConvertandTransfer ActionListener`: 
 *      - Prompts the user for a database name.
 *      - Checks if the database already exists.
 *      - Creates a new database and its tables if not present.
 *      - Parses XML data and transfers it to the database.
 *      - Updates the list of databases in the main UI.
 * - `btn_back ActionListener`: Returns to the main menu and clears the path field.
 * 
 * Expected Output:
 * - Displays the selected XML folder path.
 * - Opens system utilities like Notepad and Calculator.
 * - Prompts for a database name and creates it if it does not exist.
 * - Converts XML data and transfers it to the database.
 * - Updates the database list in the main UI.
 * - Displays logs in the console for tracking operations.
 * - Provides error messages for missing selections or failed operations.
 */




public class GUI_ConvertData extends JPanel {

    // Components
    private JMenuBar        toolBar;
    private JMenu           toolsMenu;
    private JMenuItem       calculatorItem;
    private JMenuItem       notepadItem;
    private JMenu           helpMenu;
    private JMenuItem       contentsItem;
    private JMenuItem       contact_us_Item;
    private JMenuItem       aboutItem;
    private JTextField      showPathFile;
    // private JTextField      inputDBName;
    private JButton         btnOpenFile;
    private JButton         btn_ConvertandTransfer;
    private JButton         btn_back;
    private JMenu           fileMenu ;
    private JMenuItem       openItem;
    private JMenuItem       exitItem;

    // Constant Labels
    // private JLabel          label_ItemCount;
    // private JLabel          label_FolderName;
    // private JLabel          label_DBName;

    // Display Labels
    // private JLabel          label_DisplayFolerName;
    // private JLabel          label_DisplayItemCount;

    // Variables
    private String          xmlFolderPath;
    // private String          folderName;
    // private int             itemCount = 0;
    private Main_Frame      mainFrame;


    public GUI_ConvertData(Main_Frame mainFrame) {



        //adjust layout
        this.mainFrame = mainFrame;
        setLayout(null);
        setOpaque(false);


        // //set Look & Feel
        // Locale.setDefault(Locale.ENGLISH);

        PersonController    personController    = this.mainFrame.getPersonController();
        DatabaseController  dbController        = this.mainFrame.getDbController();
        

        //initialize components

        toolBar                     = new JMenuBar();
        fileMenu                    = new JMenu("File");
        openItem                    = new JMenuItem("Open");
        exitItem                    = new JMenuItem("Exit");
        toolsMenu                   = new JMenu("Tools");
        calculatorItem              = new JMenuItem("Calculator");
        notepadItem                 = new JMenuItem("Notepad");
        helpMenu                    = new JMenu("Help");
        contentsItem                = new JMenuItem("Contents");
        contact_us_Item             = new JMenuItem("Contact us!");
        aboutItem                   = new JMenuItem("About");
        showPathFile                = new JTextField(1);
        // inputDBName                 = new JTextField(1);
        btnOpenFile                 = new JButton("Open File");
        btn_ConvertandTransfer      = new JButton("Convert & Transfer");
        btn_back                    = new JButton("Back");
        // label_FolderName            = new JLabel("Folder Name:");
        // label_ItemCount             = new JLabel("Total items:");
        
        // label_DisplayFolerName      = new JLabel(folderName);
        // label_DisplayItemCount      = new JLabel(itemCount + ".items");
        // label_DBName                = new JLabel("Database Create Name:"); // "Enter the name of the Database you will create."


        //set components properties

        toolBar.                    setBounds(0, 0, 950, 20);
        btn_ConvertandTransfer.     setBounds(300, 300, 140, 30);

        // inputDBName.                setBounds(200, 50, 100, 25);
        showPathFile.               setBounds(120, 370, 500, 25);
        btnOpenFile.                setBounds(630, 370, 100, 25);
        btn_back.                   setBounds(630, 400, 100, 25);

        // label_DBName.               setBounds(50, 50, 150, 25);
        // label_FolderName.           setBounds(500, 50, 120, 25);
        // label_ItemCount.            setBounds(500, 150, 100, 25);
        // label_DisplayFolerName.     setBounds(650, 50, 100, 25);
        // label_DisplayItemCount.     setBounds(650, 150, 100, 25);


        //set component Font styles

        fileMenu.                   setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        toolsMenu.                  setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        helpMenu.                   setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        showPathFile.               setFont(new Font("Arial", Font.BOLD, 12));
        btnOpenFile.                setFont(new Font("Arial", Font.BOLD, 12));
        btn_ConvertandTransfer.     setFont(new Font("Arial", Font.BOLD, 12));
        btn_back.                   setFont(new Font("Arial", Font.BOLD, 12));
        // inputDBName.                setFont(new Font("Arial", Font.BOLD, 12));

        // label_FolderName.           setFont(new Font("Arial", Font.BOLD, 12));
        // label_ItemCount.            setFont(new Font("Arial", Font.BOLD, 12));
        // label_DisplayFolerName.     setFont(new Font("Arial", Font.BOLD, 12));
        // label_DisplayItemCount.     setFont(new Font("Arial", Font.BOLD, 12));
        // label_DBName.               setFont(new Font("Arial", Font.BOLD, 12));



        //set component Foreground colors

        fileMenu.                   setForeground (new java.awt.Color(255, 255, 255));
        toolsMenu.                  setForeground (new java.awt.Color(255, 255, 255));
        helpMenu.                   setForeground (new java.awt.Color(255, 255, 255));
        showPathFile.               setForeground(new java.awt.Color(255, 255, 255));
        btnOpenFile.                setForeground(new java.awt.Color(255, 255, 255));
        btn_ConvertandTransfer.     setForeground(new java.awt.Color(255, 255, 255));
        btn_back.                   setForeground(new java.awt.Color(255, 255, 255));
        // inputDBName.                setForeground(new java.awt.Color(255, 255, 255));

        // label_FolderName.           setForeground(new java.awt.Color(255, 255, 255));
        // label_ItemCount.            setForeground(new java.awt.Color(255, 255, 255));
        // label_DisplayFolerName.     setForeground(new java.awt.Color(255, 255, 255));
        // label_DisplayItemCount.     setForeground(new java.awt.Color(255, 255, 255));
        // label_DBName.               setForeground(new java.awt.Color(255, 255, 255));




        //set component Background colors

        showPathFile.               setBackground(new java.awt.Color(60, 60, 60));
        // inputDBName.                setBackground(new java.awt.Color(60, 60, 60));
        btnOpenFile.                setBackground(new java.awt.Color(60, 60, 60));
        btn_ConvertandTransfer.     setBackground(new java.awt.Color(60, 60, 60));
        btn_back.                   setBackground(new java.awt.Color(0, 0, 255));

        // label_FolderName.           setBackground(new java.awt.Color(60, 60, 60));
        // label_ItemCount.            setBackground(new java.awt.Color(60, 60, 60));
        // label_DisplayFolerName.     setBackground(new java.awt.Color(60, 60, 60));
        // label_DisplayItemCount.     setBackground(new java.awt.Color(60, 60, 60));
        // label_DBName.               setBackground(new java.awt.Color(60, 60, 60));
        toolBar.                    setBackground (new java.awt.Color(60, 60, 60));



        //set component Border

        btnOpenFile.                setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));
        btn_ConvertandTransfer.     setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));
        btn_back.                   setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));




        //add components

        fileMenu.add(openItem);
        fileMenu.add(exitItem);
        toolsMenu.add(calculatorItem);
        toolsMenu.add(notepadItem);
        helpMenu.add(contentsItem);
        helpMenu.add(contact_us_Item);
        helpMenu.add(aboutItem);
        
        toolBar.add(fileMenu);
        toolBar.add(toolsMenu);
        toolBar.add(helpMenu);

        

        //set components properties

        showPathFile.   setEnabled(false);



        //add components

        add(toolBar);
        add(showPathFile);
        // add(inputDBName);
        add(btnOpenFile);
        add(btn_ConvertandTransfer);
        add(btn_back);
        // add(label_DBName);
        // add(label_ItemCount);
        // add(label_FolderName);
        // add(label_DisplayFolerName);
        // add(label_DisplayItemCount);



        //Button action listeners

        openItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                xmlFolderPath = fileChooser.getSelectedFile().getAbsolutePath();
                showPathFile.setText(xmlFolderPath);
            }
        });


        exitItem.addActionListener(e -> System.exit(0));

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

        btnOpenFile.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                xmlFolderPath = fileChooser.getSelectedFile().getAbsolutePath();
                showPathFile.setText(xmlFolderPath);
            }
        });

        btn_ConvertandTransfer.addActionListener(e -> {
            if (xmlFolderPath != null && !xmlFolderPath.isEmpty()) {

                // 1
                String dbName = JOptionPane.showInputDialog(null, "Enter Database Name:", "Database Creation", JOptionPane.QUESTION_MESSAGE);
                if (dbName == null || dbName.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Operation cancelled. No database was created.", "Cancelled", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                System.out.println("GUI-1");

                // 2
                dbController.setshowDialog(false);
                if(dbController.isDatabaseExists(dbName)){
                    JOptionPane.showMessageDialog(null, "Database (" + dbName + ") is already exist!", "Warning!", JOptionPane.WARNING_MESSAGE);
                    return;
                    
                }
                dbController.createDatabase(dbName);

                System.out.println("GUI-2");
                
                // 3
                dbController.createTables();
                
                System.out.println("GUI-3");

                // 4
                new MyParser("resources/xml/", personController);
                
                System.out.println("GUI-4");
                // 5
                personController.saveAllToDatabase(dbController);
                
                System.out.println("GUI-5");
                
                JOptionPane.showMessageDialog(null, "All Files Converted and Transferred Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                
                GUI_Main.setDatabases(dbController.getDatabaseList());
              
            } else {
                JOptionPane.showMessageDialog(this, "Please Choose a XML Folder!", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        btn_back.addActionListener(e -> {

            this.mainFrame.showPanel("gui_Main");
            showPathFile.setText("");
        });
    }

}
