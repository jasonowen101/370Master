package checkers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//EndScreens is a parent class
public class EndScreen extends JPanel{
    JPanel titlePanel;
    JLabel winnerLabel;
    JPanel imgPanel;
    JLabel imgLabel;

    Image[] yellowWins = new Image[] {}


    public EndScreen() {
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBackground(Color.BLACK);

        winnerLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        winnerLabel.setForeground(Color.BLUE);
        titlePanel.add(winnerLabel);
        
        add(titlePanel);
        add(imgPanel);
    }

    public void setWinner(String winner) {
        Image img = getImg(winner);
        imgLabel.setIcon(new ImageIcon(img));
        if(winner == "blue"){
            winnerLabel.setText("Blue has won!");
        } else if (winner == "yellow") {
            winnerLabel.setText("Yellow has won!");
        } else {
            winnerLabel.setText("It's a draw");
        }
    }
    
    public Image getImg(String winner) {

    }
}


