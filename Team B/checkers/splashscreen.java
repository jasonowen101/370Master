import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class splashscreen extends JPanel {
    public splashscreen() {
        setLayout(new BorderLayout()); // Set the layout for this panel

        // Load the image
        Image image = null;
        //System.out.println("pre-image");
        try {
            image = ImageIO.read(new File("Team B/images/ultimaterobotcheckers7.jpeg"));  //"https://drive.google.com/uc?id=18mgucp-j7PwEAcoGkLWFhubJav_YBWTM"
        } catch (IOException e) {
            System.out.println("Can not load file");
        }
        //System.out.println("Post-image");
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        add(imageLabel, BorderLayout.CENTER);

        // Create a JPanel for the title with a black background
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.BLACK); // Set the background color to black
        add(titlePanel, BorderLayout.NORTH);

        // Create a JLabel for the title
        JLabel titleLabel = new JLabel("Checkers");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36)); // Set the font and size
        titleLabel.setForeground(Color.WHITE); // Set the text color to white
        titleLabel.setHorizontalAlignment(JLabel.CENTER); // Center the text horizontally
        titlePanel.add(titleLabel, BorderLayout.CENTER); // Add the title label to the titlePanel

    }
}
