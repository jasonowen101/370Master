package checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class HelpPanel extends JPanel {
    private Image helpbkg = new ImageIcon("src/images/helpbkg.png").getImage(); // Help image background
    private JButton back = new JButton("Back");

    HelpPanel() {
        setLayout(new BorderLayout());

        // Created a background label with an image
        JLabel backgroundLabel = new JLabel(new ImageIcon(helpbkg));
        backgroundLabel.setLayout(new BorderLayout());

        // Created a button to go back to the main menu
        back.setAlignmentX(Component.CENTER_ALIGNMENT); // Centered the button horizontally
        back.setFont(new Font("Arial", Font.BOLD, 24)); // font and size
        back.setForeground(Color.WHITE); // text color to white
        back.setBackground(Color.BLACK); //background color to black
        back.setFocusPainted(false);
        back.setPreferredSize(new Dimension(200, 60)); // button size
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle going back to the main menu
                Ctegame.cl.show(Ctegame.cards, "MenuPanel");
            }
        });

        // Created a button panel to center the button vertically
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false); // Make it transparent
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(back, gbc);

        // Adding the button panel to the SOUTH position of the background label
        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);

        // Adding the background label to the center of the HelpPanel
        add(backgroundLabel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setFocusable(true);

        // Drawing the background image
        g2d.drawImage(helpbkg, 0, 0, null);
    }
}
