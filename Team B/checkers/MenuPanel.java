package checkers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MenuPanel extends JPanel {
    private JButton play = new JButton("Play");
    private JButton help = new JButton("Help");
    private JButton exit = new JButton("Exit");

    private Image menubkg = new ImageIcon("src/images/splash.png").getImage(); // Menu background

    MenuPanel() {
        setLayout(new GridBagLayout()); // Used GridBagLayout for centering

        // Configured buttons
        configureButton(play);
        configureButton(help);
        configureButton(exit);

        // Created a panel for button layout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setOpaque(false); // Make the panel transparent

        // Adding buttons to the button panel
        buttonPanel.add(play);
        buttonPanel.add(Box.createVerticalStrut(20)); // Add spacing between buttons
        buttonPanel.add(help);
        buttonPanel.add(Box.createVerticalStrut(20));
        buttonPanel.add(exit);

        // Adding the button panel to the center of the MenuPanel using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buttonPanel, gbc);

        // Adding action listeners to buttons
        play.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             // dropdown when play is clciked
                showPlayMenu(play);
            }
        });

        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ctegame.cl.show(Ctegame.cards, "HelpPanel");
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void configureButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centered the button horizontally
        button.setFont(new Font("Arial", Font.BOLD, 24)); // font and size
        button.setForeground(Color.WHITE); // text color to white
        button.setBackground(Color.BLACK); //background color to black
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(200, 60)); // Set button size
    }

    // Showing the dropdown menu for the "Play" button
    private void showPlayMenu(Component invoker) {
        JPopupMenu playMenu = new JPopupMenu();
        JMenuItem playerVsPlayer = new JMenuItem("Player1 vs Player2");
        JMenuItem playerVsComputer = new JMenuItem("Play vs Computer");
        JMenuItem computerVsComputer = new JMenuItem("Computer vs Computer");

        configureMenuItem(playerVsPlayer); // Configured dropdown item
        configureMenuItem(playerVsComputer); // Configured dropdown item
        configureMenuItem(computerVsComputer);

        // Added action listeners to menu items
        playerVsPlayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Ctegame.cl.show(Ctegame.cards, "GamePanel");
                EndScreen.setWinner("draw");
                Ctegame.cl.show(Ctegame.cards, "EndScreen");
                GUI.gameMode = "pvp";
            }
        }); 

        playerVsComputer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ctegame.cl.show(Ctegame.cards, "GamePanel");
                GUI.gameMode = "pvc";
            }
        });

        computerVsComputer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Ctegame.cl.show(Ctegame.cards, "GamePanel");
                GUI.gameMode = "cvc";
            }
        });
    

        playMenu.add(playerVsPlayer);
        playMenu.add(playerVsComputer);
        playMenu.add(computerVsComputer);
        playMenu.show(invoker, 0, invoker.getHeight());
    }

    // Configure dropdown menu item
    private void configureMenuItem(JMenuItem item) {
        item.setFont(new Font("Arial", Font.BOLD, 24)); // Setting the font and size
        item.setForeground(Color.WHITE); // Setting text color to white
        item.setBackground(Color.BLACK); // Setting background color to black
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        setFocusable(true);

        // Drawed the background image
        g2d.drawImage(menubkg, 0, 0, null);

        // Created a semi-transparent overlay
        Color overlayColor = new Color(0, 0, 0, 150); 
        g2d.setColor(overlayColor);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
