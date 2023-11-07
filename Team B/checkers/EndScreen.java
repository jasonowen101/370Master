package checkers;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

//EndScreens is a parent class
public class EndScreen extends JPanel{
    private JPanel titlePanel = new JPanel();
    private static JLabel winnerLabel = new JLabel();
    private JPanel imgPanel = new JPanel();
    private static JLabel imgLabel = new JLabel();
    private static Random rand = new Random();

    private static Image[] yellowWins = new Image[] {readImg("bigolrobotfight.jpg"), readImg("yellow win1.jpeg"), readImg("yellow win2.jpeg"), readImg("yellow win3.jpeg")};
    private static Image[] blueWins = new Image[] {readImg("blue win1.jpeg"), readImg("blue win2.jpeg"), readImg("blue win3.jpeg")};
    private static Image[] draw = new Image[] {readImg("draw1.jpeg"), readImg("draw2.jpeg"), readImg("draw3.jpeg")};

    private static Image readImg(String fileName) {
        try{
            return ImageIO.read(new File("Team B/images/"+ fileName));
        } catch(IOException e) {
            System.out.println(fileName);
            return null;
        }
    }

    public EndScreen() {
        titlePanel.setBackground(Color.BLACK);

        winnerLabel.setFont(new Font("Roboto", Font.BOLD, 36));
        winnerLabel.setForeground(Color.BLUE);
        titlePanel.add(winnerLabel);
        
        imgPanel.add(imgLabel);

        add(titlePanel);
        add(imgPanel);
    }

    public static void setWinner(String winner) {
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
    
    private static Image getImg(String winner) {
        switch(winner) {
            case "blue":
                return randImg(blueWins);
            case "yellow":
                return randImg(yellowWins);
            default: 
                return randImg(draw);
        }
    }
    
    private static Image randImg(Image[] imageArray) {
        return imageArray[rand.nextInt(imageArray.length)];
    }
}


