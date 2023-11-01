package checkers;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//EndScreens is a parent class
abstract class EndScreens extends JFrame{

    //1 label and 2 buttons
    protected JLabel winnerLabel;
    protected JButton restartButton;
    protected JButton homeButton;

    protected JPanel endScreenPanel;

//condition is passed and can be changed to the actual condition
    public EndScreens(String condition) {

        setTitle("Game Over");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        winnerLabel = new JLabel(condition);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        restartButton = new JButton("Restart");
        homeButton = new JButton("Home");
        endScreenPanel = new JPanel();

        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(endScreenPanel);
        endScreenPanel.add(Box.createVerticalGlue());
        endScreenPanel.add(winnerLabel);
        endScreenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        endScreenPanel.add(restartButton);
        endScreenPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        endScreenPanel.add(homeButton);
        endScreenPanel.add(Box.createVerticalGlue());

        restartButton.addActionListener(new RestartButtonListener());
        homeButton.addActionListener(new HomeButtonListener());

        setVisible(true);
        }

        public class RestartButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        }

        public class HomeButtonListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        }
    }

//only changes needed to be made for each screen is the title
class BlueWinScreen extends EndScreens {

    //method for when Blue wins
    public BlueWinScreen() {
        super("Blue Wins!");
    } 
}

class YellowWinScreen extends EndScreens {

    //method for when Yellow wins
    public YellowWinScreen() {
        super("Yellow Wins!");
    }
}

class DrawScreen extends EndScreens {

    //method for draw
    public DrawScreen() {
        super("Draw");
    }
}


