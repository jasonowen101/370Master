import javax.swing.*;
import java.awt.*;

//EndScreens is a parent class
abstract class EndScreens extends JFrame{

    //1 label and 2 buttons
    protected JLabel winnerLabel;
    protected JButton restartButton;
    protected JButton homeButton;

//condition is passed and can be changed to the actual condition
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

//only changes needed to be made for each screen is the title
class BlueWinScreen extends EndScreens {

    //method for when Blue wins
    public BlueWinScreen() {
        super("Blue Wins");
    } 
}

class YellowWinScreen extends EndScreens {

    //method for when Yellow wins
    public YellowWinScreen() {
        super("Yellow Wins");
    }
}

class DrawScreen extends EndScreens {

    //method for draw
    public DrawScreen() {
        super("Draw");
    }
}


