

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
 * Update_Data_Dialog provides a modal dialog for updating existing database records.
 * It pre-fills input fields with current values and allows users to edit and submit changes.
 *
 * Main Functions:
 * - `isConfirmed()`: Returns whether the update was confirmed by the user.
 * - `getUpdatedData()`: Retrieves the modified values as a `String[]`.
 * - `confirmButton ActionListener`: Validates required fields and submits changes.
 * - `cancelButton ActionListener`: Closes the dialog without applying modifications.
 *
 * Expected Output:
 * - If required fields are filled, the update is confirmed, and the dialog closes.
 * - If required fields are empty, an error message prompts the user.
 * - If canceled, no changes are applied, and the dialog closes.
 */





public class Update_Data_Dialog extends JDialog {
    private JTextField[] inputFields;
    private JButton confirmButton, cancelButton;
    private boolean confirmed;

    

    public Update_Data_Dialog(JFrame pFrame, String[] columnNames, boolean[] requiredFields, String[] currentValues) {
        super(pFrame, "Update Data", true);
        setLayout(null);
        setSize(400, columnNames.length * 40 + 120);
        setLocationRelativeTo(pFrame);

        inputFields = new JTextField[columnNames.length];
        confirmButton = new JButton("Update");
        cancelButton = new JButton("Cancel");

        int yPosition = 20;

        
        for (int i = 0; i < columnNames.length; i++) {
            JLabel label = new JLabel(columnNames[i] + (requiredFields[i] ? " *" : ""));
            label.setFont(new Font("Arial", Font.BOLD, 12));
            label.setBounds(20, yPosition, 120, 25);
            add(label);

            JTextField textField = new JTextField(15);
            textField.setBounds(150, yPosition, 200, 25);
            textField.setForeground(Color.WHITE);
            textField.setBackground(new Color(60, 60, 60));
            if(i == 0){
                textField.setText("Auto Generated");
                textField.setEditable(false);
                textField.setForeground(Color.GRAY);
                textField.setBackground(new Color(60, 60, 60));
            }
            textField.setText(currentValues[i]);
            inputFields[i] = textField;
            add(textField);

            yPosition += 40;
        }

        confirmButton.setBounds(80, yPosition, 100, 25);
        cancelButton.setBounds(200, yPosition, 100, 25);

        confirmButton.addActionListener(e -> {
            for (int i = 0; i < columnNames.length; i++) {
                if (requiredFields[i] && inputFields[i].getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            confirmed = true;
            if(confirmed){
                dispose();
            }
        });

        cancelButton.addActionListener(e -> dispose());

        add(confirmButton);
        add(cancelButton);
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public String[] getUpdatedData() {
        String[] updatedData = new String[inputFields.length];
        for (int i = 0; i < inputFields.length; i++) {
            updatedData[i] = inputFields[i].getText().trim();
        }
        return updatedData;
    }
}
