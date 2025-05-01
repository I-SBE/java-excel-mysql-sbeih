

package view;


import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Font;


/**
 * GUI_Insert_Data_Dialog provides a modal dialog for inserting data into a database table.
 * Users input values based on dynamically generated fields corresponding to table columns.
 * 
 * Main Functions:
 * - `GUI_Insert_Data_Dialog(JFrame pFrame, String[] columnNames, boolean[] requiredFields)`: 
 *   - Initializes the dialog with dynamic input fields based on provided column names.
 *   - Marks required fields with an asterisk (*) in the label.
 *   - Sets the first input field as "Auto Generated" and non-editable for primary keys.
 * 
 * - `confirmButton ActionListener`: 
 *   - Validates required fields to ensure no empty inputs.
 *   - If all required fields are filled, marks `confirmed` as true and closes the dialog.
 *   - Displays an error message if any required field is left empty.
 * 
 * - `cancelButton ActionListener`: 
 *   - Closes the dialog without saving any input.
 * 
 * - `isConfirmed()`: 
 *   - Returns `true` if the user confirmed the input, `false` otherwise.
 * 
 * - `getInputData()`: 
 *   - Returns an array of strings containing user input values.
 *   - Trims any leading/trailing spaces from inputs before returning.
 * 
 * Expected Output:
 * - If all required fields are filled:
 *   - The dialog closes successfully.
 *   - The input data is stored and can be retrieved via `getInputData()`.
 * 
 * - If any required field is empty:
 *   - An error message is displayed.
 *   - The dialog remains open until valid input is provided or the user cancels.
 * 
 * - If the user cancels:
 *   - The dialog closes without saving any data.
 * 
 * Additional Features:
 * - Uses dynamic layout adjustment based on the number of fields.
 * - Implements custom UI colors for readability and distinction.
 * - Auto-generates the first field (assumed primary key) as non-editable.
 */




     
public class GUI_Insert_Data_Dialog extends JDialog {
    private JLabel label;
    private JTextField textField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTextField[] inputFields;
    private boolean confirmed;

    



    public GUI_Insert_Data_Dialog(JFrame pFrame, String[] columnNames, boolean[] requiredFields) {


        //call the parent constructor

        super(pFrame, "Insert Data", true);
        setLayout(null);
        setSize(400, columnNames.length * 40 + 120);
        setLocationRelativeTo(pFrame);



        //initialize components

        inputFields = new JTextField[columnNames.length];
        confirmButton = new JButton("OK");
        cancelButton = new JButton("Cancel");


        // Initialize yPosition for placing components vertically   
        int yPosition = 20;

        // **Creating input fields with labels**
        for (int i = 0; i < columnNames.length; i++) {
            label = new JLabel(columnNames[i] + (requiredFields[i] ? " *" : ""));
            label.setFont(new java.awt.Font("Arial", Font.BOLD, 12));
            label.setBounds(20, yPosition, 120, 25);
            add(label);

            textField = new JTextField(15);
            textField.setBounds(150, yPosition, 200, 25);
            textField.setForeground(new java.awt.Color(255, 255, 255));
            textField.setBackground(new java.awt.Color(60, 60, 60));

            if(i == 0){
                textField.setText("Auto Generated");
                textField.setEditable(false);
                textField.setForeground(Color.GRAY);
                textField.setBackground(new Color(60, 60, 60));
            }
            
            inputFields[i] = textField;
            add(textField);

            yPosition += 40; // Move to the next row
        }



        //set components properties

        confirmButton.setBounds(80, yPosition, 100, 25);
        cancelButton.setBounds(200, yPosition, 100, 25);



        //set component Font styles

        confirmButton.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));
        cancelButton.setFont(new java.awt.Font("Arial", Font.PLAIN, 12));




        //set component Foreground colors

        confirmButton.setForeground(new java.awt.Color(255, 255, 255));
        cancelButton.setForeground(new java.awt.Color(255, 255, 255));


        // set component Background colors   

        confirmButton.setBackground(new java.awt.Color(60, 60, 60));
        cancelButton.setBackground(new java.awt.Color(120, 0, 0));




        //add components

        add(confirmButton);
        add(cancelButton);



        // **Confirm button action**

        confirmButton.addActionListener( e-> {

                for (int i = 0; i < columnNames.length; i++) {
                    if (requiredFields[i] && inputFields[i].getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(GUI_Insert_Data_Dialog.this,
                                "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                confirmed = true;
                if(confirmed){
                    dispose();
                }
        });

        // **Cancel button action**
        cancelButton.addActionListener(e -> dispose());
    }



    // Method to check if input was confirmed
    public boolean isConfirmed() {
        return confirmed;
    }



    // Method to retrieve user input data
    public String[] getInputData() {
        String[] data = new String[inputFields.length];
        for (int i = 0; i < inputFields.length; i++) {
            data[i] = inputFields[i].getText().trim();
        }
        return data;
    }
}


