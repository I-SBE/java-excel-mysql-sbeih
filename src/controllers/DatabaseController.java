

package controllers;


import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 * DatabaseController handles all database operations via JDBC for MySQL.
 * It manages connection establishment, database switching, data retrieval, insertion,
 * updates, deletions, and database/table creation.
 *
 * Main Functions:
 * - `connect()`: Establishes a connection to the MySQL database.
 * - `changeDatabase(String newDB_Name)`: Switches the current database.
 * - `fetchData(String tableName, DefaultTableModel tableModel)`: Retrieves data from the specified table and updates the table model.
 * - `insertData(String tableName, String[] values)`: Inserts new records while checking for duplicates.
 * - `updateData(String tableName, String[] columnNames, String[] newValues, String primaryKeyColumn, String primaryKeyValue)`: Updates an existing record based on the primary key.
 * - `deleteData(String tableName, String primaryKeyColumn, String primaryKeyValue)`: Deletes a record using its primary key.
 * - `createDatabase(String dbName)`: Creates a new database if it does not exist.
 * - `createTables()`: Creates the necessary tables if they do not exist.
 * - `getTableColumnsInfo(String tableName)`: Retrieves column names and determines their required status (`NOT NULL`).
 * - `getDatabaseList()`: Retrieves a list of available databases.
 * - `getTablesList()`: Retrieves a list of tables in the current database.
 * - `getColumnsList(String tableName)`: Retrieves a list of column names for a given table.
 * - `isDatabaseExists(String databaseName)`: Checks if a specific database exists.
 * - `isDataExists(String tableName, String id)`: Checks if a record with a specific primary key exists.
 * - `closeConnection()`: Closes the database connection safely.
 *
 * Expected Outcomes:
 * - If the database connection is successful, operations like fetching, inserting, updating, and deleting work as expected.
 * - If an error occurs (e.g., connection failure, invalid query), an error message is displayed.
 * - If a required field is left empty during insertion or update, the operation is rejected with a warning.
 * - If `showDialog=true`, switching the database notifies the user.
 * - If tables do not exist, `createTables()` ensures they are created.
 */




public class DatabaseController {


    // Database connection parameters

    private static      String      url          = "jdbc:mysql://localhost:3306/";
    private static      String      user;
    private static      String      password;
    private static      Connection  connection;
    private static      String      dbName       = "";
    private static      boolean     isConnected = false;
    private static      boolean     showDialog  = true;
    private             List<String> dbList;
    private             Map<String, Boolean> columnInfo;




    // Connect to the database

    public void connect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(getUrl(), getUser(), getPassword());
            DatabaseController.isConnected = true;
        } catch (Exception e) {
            e.printStackTrace();
            DatabaseController.isConnected = false;
        }
    }


    
    //Changes the current database connection to a new database

    public void changeDatabase(String newDB_Name) {
        setDB_Name(newDB_Name);
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                DatabaseController.isConnected=false;
            }
            url = "jdbc:mysql://localhost:3306/" + getDB_Name();
            connect();

            if(showDialog){
                JOptionPane.showMessageDialog(null, "Switched to database: " + getDB_Name());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to switch database: " + e.getMessage());
        }
    }

    
    // Fetch data from the database

    public void fetchData(String tableName, DefaultTableModel tableModel) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tableModel.setRowCount(0);
    
        Map<String, Boolean> columnInfo = getTableColumnsInfo(tableName);
        if (columnInfo == null || columnInfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No columns found for this table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tableModel.setColumnIdentifiers(columnInfo.keySet().toArray(new String[0]));
        
        String query = "SELECT * FROM " + tableName;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
    
            while (rs.next()) {
                String[] rowData = new String[columnInfo.size()];
                int i = 0;
                for (String column : columnInfo.keySet()) {
                    rowData[i++] = (rs.getString(column) != null) ? rs.getString(column) : "N/A";
                }
                tableModel.addRow(rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    



    // Insert data into the database


    public void insertData(String tableName, String[] values) {
        
        if (isDataExists(tableName, values[0])) {
            JOptionPane.showMessageDialog(null, "Data already exist!" + tableName + " with ID: " + values[0], "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            Map<String, Boolean> columnInfo = getTableColumnsInfo(tableName);
            if (columnInfo.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No columns found for this table.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            String[] columnNames        = columnInfo.keySet().toArray(new String[0]);
            if (columnNames[0].equalsIgnoreCase("id")) {
                columnNames = Arrays.copyOfRange(columnNames, 1, columnNames.length);
            }
    
            StringBuilder query         = new StringBuilder("INSERT INTO " + tableName + " (");
            StringBuilder placeholders  = new StringBuilder();
    
            for (int i = 0; i < columnNames.length; i++) {
                query.          append(columnNames[i]);
                placeholders.   append("?");
                if (i < columnNames.length - 1) {
                    query.          append(", ");
                    placeholders.   append(", ");
                }
            }
            query.append(") VALUES (").append(placeholders).append(")");
    
            PreparedStatement pstmt = connection.prepareStatement(query.toString());
    
            for (int i = 0; i < columnNames.length; i++) {
    
                if (values[i] instanceof String && (values[i].trim().isEmpty() && !columnInfo.get(columnNames[i]))) {
                    pstmt.setNull(i + 1, java.sql.Types.NULL);
                } else {
                    pstmt.setObject(i + 1, values[i]);
                }
            }
    
            pstmt.executeUpdate();
            if(showDialog){
                JOptionPane.showMessageDialog(null, "Data Inserted Successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error inserting data: " + e.getMessage());
        }
    }
    


    public boolean isDataExists(String tableName, String id) {
        String checkQuery = "SELECT id FROM " + tableName + " WHERE id = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(checkQuery)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    


    // Update data in the database

    public void updateData( String      tableName, 
                            String[]    columnNames, 
                            String[]    newValues, 
                            String      primaryKeyColumn, 
                            String      primaryKeyValue) {
                                
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {

            Map<String, Boolean> columnInfo = getTableColumnsInfo(tableName);
            
            StringBuilder query = new StringBuilder("UPDATE " + tableName + " SET ");
            for (int i = 1; i < columnNames.length; i++) {
                query.append(columnNames[i]).append(" = ?");
                if (i < columnNames.length - 1) query.append(", ");
            }
            query.append(" WHERE ").append(primaryKeyColumn).append(" = ?");
    
            PreparedStatement pstmt = connection.prepareStatement(query.toString());
    
            for (int i = 1; i < columnNames.length; i++) {
                if (newValues[i] == null || newValues[i].trim().isEmpty() || 
                    newValues[i].equals("N/A") && !columnInfo.get(columnNames[i])) {

                    pstmt.setNull(i, java.sql.Types.NULL);
                } else {
                    pstmt.setObject(i, newValues[i]);
                }
            }
            
            pstmt.setObject(columnNames.length, primaryKeyValue);
    
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Data Updated Successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No rows updated. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage());
        }
    }




    // Delete data from the database

    public void deleteData(String tableName, String primaryKeyColumn, String primaryKeyValue) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        if (primaryKeyValue == null || primaryKeyValue.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Invalid primary key value!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            String query = "DELETE FROM " + tableName + " WHERE " + primaryKeyColumn + " = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setObject(1, primaryKeyValue);
    
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Record deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "No records deleted. Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting data: " + e.getMessage());
        }
    }




    // Close the database connection

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                DatabaseController.isConnected=false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Create a New DATABASE
    
    public void createDatabase(String databaseName) {
        

        if (databaseName == null || databaseName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Database name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (isDatabaseExists(databaseName)) {
            JOptionPane.showMessageDialog(null, "Database (" + databaseName + ") is already exist!", "Warning!", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        String createDBQuery = "CREATE DATABASE " + databaseName;
    
        try {

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createDBQuery);
            if(showDialog){
                JOptionPane.showMessageDialog(null, "Database '" + databaseName + "' created successfully!");
            }
    
            changeDatabase(databaseName);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error creating database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    public boolean isDatabaseExists(String databaseName) {
        String checkDBQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(checkDBQuery)) {
            pstmt.setString(1, databaseName);
            ResultSet rs = pstmt.executeQuery();
    
            return rs.next();
    
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    

    public void createTables() {

        String firmenTable = "CREATE TABLE IF NOT EXISTS firmen (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "firmenname VARCHAR(100) NOT NULL" +
        ");";


        String dienstleistungenTable = "CREATE TABLE IF NOT EXISTS dienstleistungen (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "dienstName VARCHAR(100) NOT NULL" +
        ");";

        String mitarbeiterTable = "CREATE TABLE IF NOT EXISTS mitarbeiter (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "vorname VARCHAR(50) NOT NULL, " +
            "nachname VARCHAR(50) NOT NULL, " +
            "strasse VARCHAR(100) NOT NULL, " +
            "hausnr INT NOT NULL, " +
            "plz INT NOT NULL, " +
            "ort VARCHAR(50) NOT NULL, " +
            "telefon VARCHAR(20) DEFAULT 'N/A', " +
            "email VARCHAR(100) DEFAULT 'N/A' UNIQUE" +
        ");";
    
        
        String externeMitarbeiterTable = "CREATE TABLE IF NOT EXISTS externemitarbeiter (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "vorname VARCHAR(50) NOT NULL, " +
            "nachname VARCHAR(50) NOT NULL, " +
            "strasse VARCHAR(100) NOT NULL, " +
            "hausnr INT NOT NULL, " +
            "plz INT NOT NULL, " +
            "ort VARCHAR(50) NOT NULL, " +
            "telefon VARCHAR(20) DEFAULT 'N/A', " +
            "email VARCHAR(100) DEFAULT 'N/A' UNIQUE, " +
            "FirmenID INT NOT NULL, " +
            "FOREIGN KEY (FirmenID) REFERENCES firmen(id) ON DELETE CASCADE ON UPDATE CASCADE" +
        ");";
    
        
        String kundenTable = "CREATE TABLE IF NOT EXISTS kunden (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "vorname VARCHAR(50) NOT NULL, " +
            "nachname VARCHAR(50) NOT NULL, " +
            "strasse VARCHAR(100) NOT NULL, " +
            "hausnr INT NOT NULL, " +
            "plz INT NOT NULL, " +
            "ort VARCHAR(50) NOT NULL, " +
            "telefon VARCHAR(20) DEFAULT 'N/A', " +
            "email VARCHAR(100) DEFAULT 'N/A' UNIQUE, " +
            "branche VARCHAR(100) NOT NULL" +
        ");";
    
        
        String mitToDinst = "CREATE TABLE IF NOT EXISTS mitarbeiter_dienstleistungen (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "MitarbeiterID INT NOT NULL, " +
            "DienstID INT NOT NULL, " +
            "FOREIGN KEY (MitarbeiterID) REFERENCES mitarbeiter(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (DienstID) REFERENCES dienstleistungen(id) ON DELETE CASCADE ON UPDATE CASCADE" +
        ");";
    
        
        String firmenToDinst = "CREATE TABLE IF NOT EXISTS firmen_dienstleistungen (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "FirmenID INT NOT NULL, " +
            "DienstID INT NOT NULL, " +
            "FOREIGN KEY (FirmenID) REFERENCES firmen(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (DienstID) REFERENCES dienstleistungen(id) ON DELETE CASCADE ON UPDATE CASCADE" +
        ");";
    
        
        String kundenToDinst = "CREATE TABLE IF NOT EXISTS kunden_dienstleistungen (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "KundenID INT NOT NULL, " +
            "DienstID INT NOT NULL, " +
            "FOREIGN KEY (KundenID) REFERENCES kunden(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (DienstID) REFERENCES dienstleistungen(id) ON DELETE CASCADE ON UPDATE CASCADE" +
        ");";

        String externeMitToDinst = "CREATE TABLE IF NOT EXISTS externemitarbeiter_dienstleistungen (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "ExterneMitarbeiterID INT NOT NULL, " +
            "DienstID INT NOT NULL, " +
            "FOREIGN KEY (ExterneMitarbeiterID) REFERENCES externemitarbeiter(id) ON DELETE CASCADE ON UPDATE CASCADE, " +
            "FOREIGN KEY (DienstID) REFERENCES dienstleistungen(id) ON DELETE CASCADE ON UPDATE CASCADE" +
        ");";

    
        try (Statement stmt = connection.createStatement()) {
            
            stmt.executeUpdate(firmenTable);
            stmt.executeUpdate(dienstleistungenTable);
            stmt.executeUpdate(mitarbeiterTable);
            stmt.executeUpdate(externeMitarbeiterTable);
            stmt.executeUpdate(kundenTable);
    
            stmt.executeUpdate(mitToDinst);
            stmt.executeUpdate(firmenToDinst);
            stmt.executeUpdate(kundenToDinst);
            stmt.executeUpdate(externeMitToDinst);
    
            if (showDialog) {
                JOptionPane.showMessageDialog(null, "Tables with relationships have been created successfully!", "Success!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to create tables: " + e.getMessage(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    


    public void searchData(String tableName, String columnName, String searchValue, DefaultTableModel tableModel) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        tableModel.setRowCount(0); 
    
        Map<String, Boolean> columnInfo = getTableColumnsInfo(tableName);
        if (columnInfo == null || columnInfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No columns found for this table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        tableModel.setColumnIdentifiers(columnInfo.keySet().toArray(new String[0]));
    
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " LIKE ?";
        
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchValue + "%");
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] rowData = new String[columnInfo.size()];
                    int i = 0;
                    for (String column : columnInfo.keySet()) {
                        rowData[i++] = (rs.getString(column) != null) ? rs.getString(column) : "N/A";
                    }
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    public void searchDataInRange(String tableName, String columnName, String minValue, String maxValue, DefaultTableModel tableModel) {
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        tableModel.setRowCount(0);
    
        Map<String, Boolean> columnInfo = getTableColumnsInfo(tableName);
        if (columnInfo == null || columnInfo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No columns found for this table.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        tableModel.setColumnIdentifiers(columnInfo.keySet().toArray(new String[0]));
    
        String query = "SELECT * FROM " + tableName + " WHERE " + columnName + " BETWEEN ? AND ?";
    
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, minValue);
            pstmt.setString(2, maxValue);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String[] rowData = new String[columnInfo.size()];
                    int i = 0;
                    for (String column : columnInfo.keySet()) {
                        rowData[i++] = (rs.getString(column) != null) ? rs.getString(column) : "N/A";
                    }
                    tableModel.addRow(rowData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error searching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    //setter

    public void setUrl(String url) {
        DatabaseController.url = url;
    }

    public void setUser(String user) {
        DatabaseController.user = user;
    }

    public void setDB_Name(String dbName){
        DatabaseController.dbName = dbName;
    }

    public void setPassword(String password) {
        DatabaseController.password = password;
    }
    
    public void setshowDialog(boolean showDialog){
        DatabaseController.showDialog = showDialog;
    }
    
    //getter

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isConnected() {
        return DatabaseController.isConnected;
    }

    public String getDB_Name() {
        return DatabaseController.dbName;
    }

    public String[] getDatabaseList() {
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW DATABASES")) {
            dbList = new java.util.ArrayList<>();
            while (rs.next()) {
                dbList.add(rs.getString(1));
            }
            return dbList.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"Unable to load databases"};
        }
    }

    public String[] getTablesList(){
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SHOW TABLES")) {
            List<String> tablesList = new java.util.ArrayList<>();
            while (rs.next()) {
                tablesList.add(rs.getString(1));
            }
            return tablesList.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"Unable to load tables"};
        }
    }

    public String[] getColumnsList(String tableName){
        List<String> columnsList = new java.util.ArrayList<>();
        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return new String[]{"Unable to load columns"};
        }

        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM " + tableName)) {
            while (rs.next()) {
                columnsList.add(rs.getString(1));
            }
            return columnsList.toArray(new String[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[]{"Unable to load columns"};
        }
    }

    public Map<String, Boolean> getTableColumnsInfo(String tableName) {
        columnInfo = new LinkedHashMap<>();

        if (connection == null) {
            JOptionPane.showMessageDialog(null, "Database is not connected!", "Error", JOptionPane.ERROR_MESSAGE);
            return columnInfo;
        }

        try (Statement stmt = connection.createStatement();
        
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " LIMIT 1")) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                boolean isRequired = metaData.isNullable(i) == ResultSetMetaData.columnNoNulls;
                columnInfo.put(columnName, isRequired);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error retrieving table structure: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return columnInfo;
    }


    public int getFirmaId(String firmenname) {
        
        int firmenID = -1;
        String query = "SELECT id FROM firmen WHERE firmenname = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firmenname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                firmenID = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return firmenID;
    }

    public int getDienstleistungId(String dienstName) {
        int dienstId = -1;
        String query = "SELECT id FROM dienstleistungen WHERE DienstName = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, dienstName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                dienstId = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dienstId;
    }
    
    public int getKundenId(String vorname, String nachname) {

        int kundenID = -1;
        String query = "SELECT id FROM kunden WHERE vorname = ? AND nachname = ? ORDER BY id DESC LIMIT 1";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vorname);
            stmt.setString(2, nachname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                kundenID = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return kundenID;
    }
    
    public int getMitarbeiterId(String vorname, String nachname) {
        
        int mitarbeiterID = -1;
        String query = "SELECT id FROM mitarbeiter WHERE vorname = ? AND nachname = ? ORDER BY id DESC LIMIT 1";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, vorname);
            stmt.setString(2, nachname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                mitarbeiterID = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mitarbeiterID;
    }

    public int getExterneMitarbeiterId(String firmenid) {
        
        int exMitarbeiterID = -1;
        String query = "SELECT id FROM externemitarbeiter WHERE FirmenID = ?";
    
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firmenid);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exMitarbeiterID = rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exMitarbeiterID;
    }

}
