package checkers;
import javax.swing.*;
import java.awt.*;

public class splashscreen extends JPanel {
    public splashscreen() {
        setLayout(new BorderLayout()); // Set the layout for this panel

        // Load the image
        ImageIcon imageIcon = new ImageIcon("https://drive.google.com/file/d/18mgucp-j7PwEAcoGkLWFhubJav_YBWTM/preview"); // Replace with the path to your image

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

        // Create a JLabel to display the image
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(imageIcon);
        add(imageLabel, BorderLayout.CENTER);

    }
}
