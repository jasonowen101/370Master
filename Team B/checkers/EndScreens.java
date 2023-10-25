import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

abstract class EndScreens extends JFrame{
    protected JLabel winnerLabel;
    protected JButton restartButton;
    protected JButton homeButton;

    public EndScreens(String condition) {

        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        winnerLabel = new JLabel(condition);
        restartButton = new JButton("Restart");
        homeButton = new JButton("Home");

        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());
        add(winnerLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(restartButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(homeButton);
        add(Box.createVerticalGlue());

        setVisible(true);
        }
    }
}

class BlueWinScreen extends EndScreens {

    public BlueWinScreen() {
        super("Blue Wins");
    } 
}

class YellowWinScreen extends EndScreens {

    public YellowWinScreen() {
        super("Yellow Wins");

    }
}

class DrawScreen extends EndScreens {

    public DrawScreen() {
        super("Draw");

    }
}
