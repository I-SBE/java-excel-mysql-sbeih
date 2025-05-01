

package view;


import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Arrays;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import controllers.DatabaseController;


/**
 * GUI_Database provides a graphical interface for managing database tables.
 * Users can fetch, insert, update, delete records, and perform searches dynamically.
 *
 * Main Functions:
 * - `fetchData()`: Loads and displays table data from the selected database.
 * - `insertData()`: Opens a dialog for inserting new records into the selected table.
 * - `updateData()`: Modifies selected records using a user-input dialog.
 * - `deleteData()`: Deletes selected rows after confirmation.
 * - `setDatabaseLabel()`: Updates the displayed database name dynamically.
 * - `setTablesList()`: Populates the table dropdown with available tables.
 * - `setColumnsList()`: Updates the column dropdown based on the selected table.
 * - `searchInTable()`: Searches within a selected column based on user input.
 * - `btn_Back ActionListener`: Resets the table and search field when returning to the main UI.
 * - `exitItem ActionListener`: Closes the application.
 * - `calculatorItem ActionListener`: Launches the system calculator.
 * - `notepadItem ActionListener`: Opens the system notepad.
 *
 * Expected Output:
 * - A table displaying records from the selected database.
 * - Success/failure messages for database operations.
 * - Dynamic UI updates upon data modifications.
 * - Search results filtered based on input (including numerical ranges).
 * - Error messages for invalid actions (e.g., missing database/table selection).
 * - Smooth user experience with real-time UI adjustments.
 */




public class GUI_Database extends JPanel {

    // Class attributes
    private         JMenuBar            toolBar;
    private         JMenu               fileMenu;
    private         JMenuItem           exitItem;
    private         JMenu               toolsMenu;
    private         JMenuItem           calculatorItem;
    private         JMenuItem           notepadItem;
    private static  JLabel              labelDBName;
    private         JLabel              labelTableName, labelColumnName;
    private         JLabel              labelBG;
    private         JTextField          searchField;
    private         JComboBox<String>   tableDropdown, columnDropdown;

    private         JButton             btnFetch, btnInsert, btnSearch, btnUpdate, btnDelete, btn_Back;
    private         JTable              table;
    private         JScrollPane         scrollPane;
    private         DefaultTableModel   tableModel;
    // private         JProgressBar        progressBar;
    private static  DatabaseController  dbController;
    private         Main_Frame          mainFrame;
    private static  String              selectedDatabase = "";


    
    // Constructor
    
    public GUI_Database(Main_Frame mainFrame) {


        //adjust layout

        this.mainFrame = mainFrame;
        setLayout(null);
        setOpaque(false);


        
        //initialize components

        dbController        = this.mainFrame.getDbController();

        toolBar             = new JMenuBar();
        tableDropdown       = new JComboBox<>();
        columnDropdown      = new JComboBox<>();
        toolsMenu           = new JMenu         ("Tools");
        calculatorItem      = new JMenuItem     ("Calculator");
        notepadItem         = new JMenuItem     ("Notepad");
        fileMenu            = new JMenu         ("File");
        exitItem            = new JMenuItem     ("Exit");
        labelDBName         = new JLabel        ();
        labelTableName      = new JLabel        ("Table    :");
        labelColumnName     = new JLabel        ("Column:");
        labelBG             = new JLabel        ();
        searchField         = new JTextField       ();
        btnFetch            = new JButton       ("List Data");
        tableModel          = new DefaultTableModel();
        table               = new JTable        (tableModel);
        scrollPane          = new JScrollPane   (table);
        btnInsert           = new JButton       ("Insert");
        btnSearch           = new JButton       ("Search");
        btnUpdate           = new JButton       ("Update");
        btnDelete           = new JButton       ("Delete");
        btn_Back            = new JButton       ("Back");
        // progressBar         = new JProgressBar(0, 100);


        

        //set components properties

        labelBG.            setOpaque(true);

        toolBar.            setBounds(0, 0, 950, 20);
        labelBG.            setBounds(20,30,300,50);
        labelDBName.        setBounds(30, 40, 200, 30);

        labelTableName.     setBounds(250, 30, 100, 20);
        labelColumnName.    setBounds(250, 60, 100, 20);
        searchField.        setBounds(530, 60, 100, 20);
        tableDropdown.      setBounds(320, 30, 200, 20);
        columnDropdown.     setBounds(320, 60, 200, 20);

        btnFetch.           setBounds(640, 30, 120, 20);
        btnSearch.          setBounds(640, 60, 120, 20);

        scrollPane.         setBounds(20, 100, 740, 300);
        btnInsert.          setBounds(20, 420, 120, 25);
        btnUpdate.          setBounds(160, 420, 120, 25);
        btnDelete.          setBounds(300, 420, 120, 25);
        btn_Back.           setBounds(640, 420, 120, 25);
        // progressBar.        setBounds(20, 390, 740, 25);



        //set component Font styles

        labelDBName.        setFont(new Font("Arial", Font.BOLD, 14));
        labelTableName.     setFont(new Font("Arial", Font.BOLD, 14));
        labelColumnName.    setFont(new Font("Arial", Font.BOLD, 14));
        searchField.        setFont(new Font("Arial", Font.BOLD, 14));
        tableDropdown.      setFont(new Font ("Arial", Font.BOLD, 12));
        columnDropdown.     setFont(new Font ("Arial", Font.BOLD, 12));
        btnFetch.           setFont(new Font("Arial", Font.BOLD, 12));
        btnSearch.          setFont(new Font("Arial", Font.BOLD, 12));
        scrollPane.         setFont(new Font("Arial", Font.BOLD, 12));
        btnInsert.          setFont(new Font("Arial", Font.BOLD, 12));
        btnUpdate.          setFont(new Font("Arial", Font.BOLD, 12));
        btnDelete.          setFont(new Font("Arial", Font.BOLD, 12));
        btn_Back.           setFont(new Font("Arial", Font.BOLD, 12));
        // progressBar.        setFont(new Font("Arial", Font.BOLD, 12));



        //set component Foreground colors

        toolsMenu.          setForeground(new Color(255, 255, 255));
        fileMenu.           setForeground(new Color(255, 255, 255));
        labelDBName.        setForeground(new Color(255, 255, 255));
        labelTableName.     setForeground(new Color(255, 255, 255));
        labelColumnName.    setForeground(new Color(255, 255, 255));
        searchField.        setForeground(new Color(255, 255, 255));
        tableDropdown.      setForeground(new Color(255, 255, 255));
        columnDropdown.     setForeground(new Color(255, 255, 255));
        btnFetch.           setForeground(new Color(255, 255, 255));
        btnSearch.          setForeground(new Color(255, 255, 255));
        scrollPane.         setForeground(new Color(255, 255, 255));
        btnInsert.          setForeground(new Color(255, 255, 255));
        btnUpdate.          setForeground(new Color(255, 255, 255));
        btnDelete.          setForeground(new Color(255, 255, 255));
        btn_Back.           setForeground(new Color(255, 255, 255));
        // progressBar.        setForeground(new Color(255, 255, 255));



        //set component Background colors

        labelDBName.        setBackground(new Color(60, 60, 60));
        labelTableName.     setBackground(new Color(60, 60, 60));
        labelColumnName.    setBackground(new Color(60, 60, 60));
        labelBG.            setBackground(new Color(60, 60, 60));
        searchField.        setBackground(new Color(60, 60, 60));
        tableDropdown.      setBackground(new Color(60, 60, 60));
        columnDropdown.     setBackground(new Color(60, 60, 60));
        calculatorItem.     setBackground(new Color(60, 60, 60));
        btnFetch.           setBackground(new Color(60, 60, 60));
        btnSearch.          setBackground(new Color(60, 60, 60));
        scrollPane.         setBackground(new Color(60, 60, 60));
        btnInsert.          setBackground(new Color(60, 60, 60));
        btnUpdate.          setBackground(new Color(60, 60, 60));
        btnDelete.          setBackground(new Color(60, 60, 60));
        btn_Back.           setBackground(new Color(0, 0, 255));
        // progressBar.        setBackground(new Color(60, 60, 60));
        toolBar.            setBackground (new Color(60, 60, 60));


        //set component Border

        btnFetch.       setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));
        btnSearch.      setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));
        btnInsert.      setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));
        btnUpdate.      setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));
        btnDelete.      setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));
        btn_Back.       setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));
        // progressBar.    setBorder(BorderFactory.createLineBorder(new Color(120, 120, 120), 3));


        //Table Style

        table.                  setBackground(new Color(40, 40, 40));
        table.                  setForeground(new Color(255, 255, 255));
        table.                  setGridColor(new Color(120, 120, 120));
        scrollPane.getViewport().setBackground(new Color(60, 60, 60));


        //add components

        fileMenu.   add(exitItem);
        toolsMenu.  add(calculatorItem);
        toolsMenu.  add(notepadItem);

        toolBar.    add(fileMenu);
        toolBar.    add(toolsMenu);

        add(toolBar);
        add(labelDBName);
        add(labelTableName);
        add(labelColumnName);
        add(labelBG);
        add(searchField);
        add(tableDropdown);
        add(columnDropdown);
        add(btnFetch);
        add(btnSearch);
        add(scrollPane);
        add(btnInsert);
        add(btnUpdate);
        add(btnDelete);
        add(btn_Back);
        // add(progressBar);
        



        //Button action listeners
        searchField.setText("Search...");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Search...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Search...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });



        exitItem.   addActionListener(e -> System.exit(0));
        btnFetch.   addActionListener(e -> fetchData());
        btnSearch.  addActionListener(e -> searchInTable());
        btnInsert.  addActionListener(e -> insertData());
        btnUpdate.  addActionListener(e -> updateData());
        btnDelete.  addActionListener(e -> deleteData());

        btn_Back.   addActionListener(e -> {
            this.mainFrame.showPanel("gui_Main");
            tableModel.setColumnCount(0);
            tableModel.setRowCount(0);
            searchField.setText("Search...");
            searchField.setForeground(Color.GRAY);
            tableDropdown.removeAllItems();
        });

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
          

        tableDropdown.addActionListener(e-> {
            if(tableDropdown.getSelectedItem()!=null){
                setColumnsList();
            }
        });

    }

    public static void setDatabaseLabel(){
        
        if(!dbController.getDB_Name().isEmpty()){
            labelDBName.setText("Database: " + dbController.getDB_Name());
        }else{
            labelDBName.setText("No Database Selected");
        }
    }


    public void setTablesList() {
        if (dbController == null || !dbController.isConnected()) {
            JOptionPane.showMessageDialog(this, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        selectedDatabase = dbController.getDB_Name();
        if (selectedDatabase.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No database selected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        dbController.setDB_Name(selectedDatabase);
        String[] tables = dbController.getTablesList();
    
        tableDropdown.removeAllItems();
        if (tables != null && tables.length > 0) {
            for (String table : tables) {
                tableDropdown.addItem(table);
            }
        } else {
            tableDropdown.addItem("No Tables Available");
        }
        setColumnsList();
    }
    
    public void setColumnsList() {

        String table = tableDropdown.getSelectedItem().toString().trim();
        String[] columns;

        if (dbController == null || !dbController.isConnected()) {
            JOptionPane.showMessageDialog(this, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(table != null){
            columns = dbController.getColumnsList(table);
        }else{
            return;
        }
    
        columnDropdown.removeAllItems();
        if (columns != null && columns.length > 0) {
            for (String col : columns) {
                columnDropdown.addItem(col);
            }
        } else {
            columnDropdown.addItem("No Tables Available");
        }
    }


    private void fetchData() {
        // String tableName = inputTableName.getText().trim();
        String tableName = (String) tableDropdown.getSelectedItem();
        if (tableName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a table name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        dbController.fetchData(tableName, tableModel);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

    }

    
    private void insertData() {
        // String tableName = inputTableName.getText().trim();
        String tableName = (String) tableDropdown.getSelectedItem();

        if (tableName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a table name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Map<String, Boolean> columnInfo = dbController.getTableColumnsInfo(tableName);

        if (columnInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No columns found for this table. Fetch data first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] columnNames = columnInfo.keySet().toArray(new String[0]);
        boolean[] requiredFields = new boolean[columnInfo.size()];

        int i = 0;
        for (boolean isRequired : columnInfo.values()) {
            requiredFields[i++] = isRequired;
        }

        GUI_Insert_Data_Dialog dialog = new GUI_Insert_Data_Dialog(mainFrame, columnNames, requiredFields);
        dialog.setVisible(true);

        
        if (dialog.isConfirmed()) {
            
            String[] inputData = dialog.getInputData();
            inputData = Arrays.copyOfRange(inputData, 1, inputData.length);
            dbController.insertData(tableName, inputData);

            fetchData();
        }
    }


    private void updateData() {

        // String  tableName       = inputTableName.getText().trim();
        String  tableName       = (String) tableDropdown.getSelectedItem();
        int     selectedRow     = table.getSelectedRow();
    
        if (tableName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a table name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to update.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Map<String, Boolean> columnInfo = dbController.getTableColumnsInfo(tableName);
        if (columnInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No columns found. Fetch data first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String[] columnNames = columnInfo.keySet().toArray(new String[0]);
        boolean[] requiredFields = new boolean[columnInfo.size()];
        String[] currentValues = new String[columnNames.length];
    
        for (int i = 0; i < columnNames.length; i++) {
            requiredFields[i] = columnInfo.get(columnNames[i]);
            Object value = tableModel.getValueAt(selectedRow, i);
            currentValues[i] = (value != null) ? value.toString() : "";
        }
    
        
        Update_Data_Dialog dialog = new Update_Data_Dialog(mainFrame, columnNames, requiredFields, currentValues);
        dialog.setVisible(true);
    
        
        if (dialog.isConfirmed()) {
            String[] newValues = dialog.getUpdatedData();
    
            
            String primaryKeyColumn = columnNames[0];
            String primaryKeyValue = currentValues[0];
    
            dbController.updateData(tableName, columnNames, newValues, primaryKeyColumn, primaryKeyValue);
    
            fetchData();
        }
    }
    

    private void deleteData() {
        
        String tableName = (String) tableDropdown.getSelectedItem();
        int selectedRow = table.getSelectedRow();
    
        if (tableName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a table name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row to delete.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        Map<String, Boolean> columnInfo = dbController.getTableColumnsInfo(tableName);
        if (columnInfo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No columns found. Fetch data first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        String[] columnNames = columnInfo.keySet().toArray(new String[0]);
    
        String primaryKeyColumn = columnNames[0];
        String primaryKeyValue = tableModel.getValueAt(selectedRow, 0).toString();
    
        
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this record?",
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION);
    
        if (confirm == JOptionPane.YES_OPTION) {
            dbController.deleteData(tableName, primaryKeyColumn, primaryKeyValue);
    
            fetchData();
        }
        
    }


    private void searchInTable() {
        String tableName = (String) tableDropdown.getSelectedItem();
        String columnName = (String) columnDropdown.getSelectedItem();
        String searchValue = searchField.getText().trim();

        if (tableName == null || tableName.isEmpty() || columnName == null || columnName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a table and a column.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (searchValue.matches("\\d+-\\d+")) {
            String[] range = searchValue.split("-");
            dbController.searchDataInRange(tableName, columnName, range[0], range[1], tableModel);
        } else {
            dbController.searchData(tableName, columnName, searchValue, tableModel);
        }
    }


}
