package checkers;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

//EndScreens is a parent class
public class EndScreen extends JPanel{
    private static JPanel titlePanel = new JPanel();
    private static JLabel winnerLabel = new JLabel();
    private static JPanel imgPanel = new JPanel();
    private static JLabel imgLabel = new JLabel();
    private static Random rand = new Random();

    private static JButton playAgainButton = new JButton("Play Again");
    private static JButton menuButton = new JButton(" Menu ");

    private static Image[] yellowWins = new Image[] {readImg("bigolrobotfight.jpg"), readImg("yellow win1.jpeg"), readImg("yellow win2.jpeg"), readImg("yellow win3.jpeg")};
    private static Image[] blueWins = new Image[] {readImg("blue win1.jpeg"), readImg("blue win2.jpeg"), readImg("blue win3.jpeg")};
    private static Image[] draw = new Image[] {readImg("draw1.jpeg"), readImg("draw2.jpeg"), readImg("draw3.jpeg")};

    private static Image readImg(String fileName) {
        BufferedImage img;
        try{
            img = ImageIO.read(new File("Team B/images/"+ fileName));
            return img.getScaledInstance(Ctegame.getFrameWidth() - titlePanel.getWidth(), Ctegame.getFrameHeight() - titlePanel.getHeight(), Image.SCALE_SMOOTH);
        } catch(IOException e) {
            System.out.println(fileName);
            return null;
        }
    }

    private void configureButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centered the button horizontally
        button.setFont(new Font("Arial", Font.BOLD, 18)); // font and size
        button.setForeground(Color.WHITE); // text color to white
        button.setBackground(Color.BLACK); //background color to black
        button.setFocusPainted(false);
    }

    public EndScreen() {
        titlePanel.setBackground(new Color(0, 0, 0, 175));

        winnerLabel.setFont(new Font("Roboto", Font.BOLD, 39));
        winnerLabel.setForeground(Color.WHITE);
        titlePanel.add(winnerLabel);
        
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Ctegame.cl.show(Ctegame.cards, "GamePanel");
            }
        });
        configureButton(playAgainButton);
        titlePanel.add(playAgainButton);

        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Ctegame.cl.show(Ctegame.cards, "MenuPanel");
            }
        });
        configureButton(menuButton);
        titlePanel.add(menuButton);

        imgPanel.setBackground(new Color(0, 0, 0, 0));
        imgPanel.add(imgLabel);

        add(titlePanel);
        add(imgPanel);
    }

    public static void setWinner(String winner) {
        Image img = getImg(winner);
        imgLabel.setIcon(new ImageIcon(img));
        if(winner == "blue"){
            winnerLabel.setText("  Blue has won!  ");
        } else if (winner == "yellow") {
            winnerLabel.setText("  Yellow has won!  ");
        } else {
            winnerLabel.setText("  It's a draw!  ");
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


