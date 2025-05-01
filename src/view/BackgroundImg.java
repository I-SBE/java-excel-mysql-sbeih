
package view;



import javax.swing.*;
import java.awt.*;


/**
 * The `BackgroundImg` class extends `JPanel` to display a background image.
 * It overrides the `paintComponent` method to dynamically adjust the image size
 * based on the panel dimensions.
 *
 * Main Functions:
 * - `BackgroundImg(String imagePath)`: Loads an image from the specified file path.
 * - `paintComponent(Graphics g)`: Draws the image to fill the entire panel area.
 *
 * Expected Output:
 * - The panel displays the provided image as its background.
 * - If the image path is invalid, no background will be displayed.
 * - The image scales automatically to fit panel resizing.
 */




public class BackgroundImg extends JPanel {

    private Image backgroundImage;

    //Constructor
    public BackgroundImg(String imagePath) {

        backgroundImage = new ImageIcon(imagePath).getImage();
        setLayout(null);
        setPreferredSize(new Dimension(720,480));
    }

    //Override the paintComponent method to paint the background image
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
