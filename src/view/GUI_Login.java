

package view;


import java.awt.Font;
import javax.swing.*;
import controllers.DatabaseController;


/**
 * GUI_Login provides a simple authentication interface for database access.
 * Users enter their credentials to establish a connection to a MySQL database.
 *
 * Main Functions:
 * - `GUI_Login()`: 
 *   - Initializes the login window with username and password fields.
 *   - Configures UI elements, including labels, input fields, and buttons.
 *   - Sets an application icon from the specified image path.
 *
 * - `btn_Login ActionListener`: 
 *   - Validates that both username and password fields are filled.
 *   - If fields are empty, displays an input error message.
 *   - Passes user credentials to `DatabaseController` and attempts connection.
 *   - Updates the main GUI with connection status and available databases.
 *   - Shows a success message if login is successful; otherwise, displays an error.
 *   - Closes the login window after attempting authentication.
 *
 * - `btn_Cancel ActionListener`: 
 *   - Closes the login window without making any changes.
 *
 * Expected Output:
 * - If login is successful:
 *   - A success message appears.
 *   - The connection status updates in the main interface.
 *   - The list of available databases refreshes.
 *
 * - If login fails:
 *   - An error message appears.
 *   - The user can retry with correct credentials.
 *
 * - If the input fields are empty:
 *   - A validation error message prompts the user to enter credentials.
 *
 * - If the user cancels:
 *   - No login attempt is made, and the window simply closes.
 *
 * Additional Features:
 * - Uses a modal login window that blocks interaction with the main application until closed.
 * - Implements a clean and modern UI with custom fonts, colors, and borders.
 * - Includes an icon for branding.
 */





public class GUI_Login extends JFrame {
    private JButton btn_Login, btn_Cancel;
    private JTextField input_Username;
    private JLabel labelUsername, labelPassword;
    private JPasswordField input_Password;
    private DatabaseController dbController;

    public GUI_Login() {


        dbController = new DatabaseController();
        

        //adjust size and set layout

        setTitle("Login to Database");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);



        //initialize components

        btn_Login = new JButton("Login");
        btn_Cancel = new JButton("Cancel");
        input_Username = new JTextField();
        labelUsername = new JLabel("User Name:");
        labelPassword = new JLabel("Password:");
        input_Password = new JPasswordField();



        
        //create Background Image and Icon

        ImageIcon icon = new ImageIcon("src\\view\\images\\logo.png");
        setIconImage(icon.getImage());


        //set components properties

        labelUsername.      setBounds(20, 20, 80, 25);
        input_Username.     setBounds(110, 20, 150, 25);
        labelPassword.      setBounds(20, 60, 80, 25);
        input_Password.     setBounds(110, 60, 150, 25);
        btn_Login.          setBounds(40, 110, 80, 30);
        btn_Cancel.         setBounds(160, 110, 80, 30);


        //set component Font styles

        labelUsername.      setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        input_Username.     setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        labelPassword.      setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        input_Password.     setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        btn_Login.          setFont (new java.awt.Font ("Arial", Font.BOLD, 12));
        btn_Cancel.         setFont (new java.awt.Font ("Arial", Font.BOLD, 12));


        //set component Foreground colors

        labelUsername.      setForeground (new java.awt.Color(0, 0, 0));
        input_Username.     setForeground (new java.awt.Color(255, 255, 255));
        labelPassword.      setForeground (new java.awt.Color(0, 0, 0));
        input_Password.     setForeground (new java.awt.Color(255, 255, 255));
        btn_Login.          setForeground (new java.awt.Color(255, 255, 255));
        btn_Cancel.         setForeground (new java.awt.Color(255, 255, 255));



        //set component Background colors

        labelUsername.      setBackground (new java.awt.Color(60, 60, 60));
        input_Username.     setBackground (new java.awt.Color(60, 60, 60));
        labelPassword.      setBackground (new java.awt.Color(60, 60, 60));
        input_Password.     setBackground (new java.awt.Color(60, 60, 60));
        btn_Login.          setBackground (new java.awt.Color(60, 60, 60));
        btn_Cancel.         setBackground (new java.awt.Color(0, 0, 255));



        //set component Border

        btn_Login.          setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));
        btn_Cancel.         setBorder(BorderFactory.createLineBorder(new java.awt.Color(120, 120, 120), 3));

        //add components

        add(labelUsername);
        add(input_Username);
        add(labelPassword);
        add(input_Password);
        add(btn_Login);
        add(btn_Cancel);



        // add action listener to button
        btn_Login.addActionListener(e -> {
            String username = input_Username.getText().trim();
            String password = new String(input_Password.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both Username and Password.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            dbController.setUser(username);
            dbController.setPassword(password);
            dbController.connect();
            GUI_Main.setConnectionStatus();
            GUI_Main.setDatabases(dbController.getDatabaseList());
            if(dbController.isConnected()){
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Failed to Login!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
        });

        btn_Cancel.addActionListener(e -> dispose());
    }
}
