package checkers;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class splashscreen extends JPanel {
    public splashscreen() {
        setLayout(new BorderLayout()); // Set the layout for this panel

        // Load the image
        BufferedImage image = null;
        URL url = null;
        try {
            url = new URL("https://drive.google.com/uc?id=18mgucp-j7PwEAcoGkLWFhubJav_YBWTM"); //so apparently to view image from googel drive u gotta have url in a certain format
            image = ImageIO.read(url);
            System.out.println("did thing");
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL");
        } catch (IOException iox) {
            System.out.println("Can not load file");
        }
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
