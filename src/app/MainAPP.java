

package app;


import view.Main_Frame;


/**
 * MainAPP serves as the entry point for the ShareEmp Ltd. application.
 * It initializes the main application window by creating an instance of `Main_Frame`.
 *
 * Function:
 * - `main(String[] args)`: Launches the application by instantiating `Main_Frame`.
 *
 * Expected Output:
 * - The application window (`Main_Frame`) is displayed.
 * - The default panel (`GUI_Main`) is loaded upon startup.
 */


public class MainAPP {

    public static void main(String[] args) {

        new Main_Frame();
        
    }
}