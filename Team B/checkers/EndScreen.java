package checkers;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

//EndScreen is a panel that says who won and displays a corresponding image.
//****CALL setWinner() WITh WINNER ARGUEMENT BEFORE SHOWING ENDSCREEN****
public class EndScreen extends JPanel{
    private static JPanel titlePanel = new JPanel();    //panel for label containing winner message and buttons to exit endscreen
    private static JLabel winnerLabel = new JLabel();   //label that displays winner message 
    private static JPanel imgPanel = new JPanel();      //panel that contains label image of winner
    private static JLabel imgLabel = new JLabel();      //label that holds image (as an icon)
    private static Random rand = new Random();          //used for generation of random image from image arrays

    private static JButton playAgainButton = new JButton("Play Again");
    private static JButton menuButton = new JButton(" Menu ");

    //These arrays hold neat images displaying the winner robot
    //to add an image to an array, add the image to the images folder in team b folder, then call readImg() with the image file name as the arguement
    private static Image[] yellowWins = new Image[] {readImg("bigolrobotfight.jpg"), readImg("yellow win1.jpeg"), readImg("yellow win2.jpeg"), readImg("yellow win3.jpeg")};
    private static Image[] blueWins = new Image[] {readImg("blue win1.jpeg"), readImg("blue win2.jpeg"), readImg("blue win3.jpeg")};
    private static Image[] draw = new Image[] {readImg("draw1.jpeg"), readImg("draw2.jpeg"), readImg("draw3.jpeg")};

    //simplifies adding images to the arrays and scales them down (or up) to fit the space they go in
    private static Image readImg(String fileName) {
        BufferedImage img;  //used buffered b/c images need to be scaled to correct size
        try{
            img = ImageIO.read(new File("Team B/images/"+ fileName));
            return img.getScaledInstance(Ctegame.getFrameWidth() - titlePanel.getWidth(), Ctegame.getFrameHeight() - titlePanel.getHeight(), Image.SCALE_SMOOTH);
        } catch(IOException e) {
            System.out.println(fileName);
            return null;
        }
    }

    //sytleizes (thats definitely a word) the buttons that go in title panel
    private void configureButton(JButton button) {
        button.setAlignmentX(Component.CENTER_ALIGNMENT); // Centered the button horizontally
        button.setFont(new Font("Arial", Font.BOLD, 18)); // font and size
        button.setForeground(Color.WHITE); // text color to white
        button.setBackground(Color.BLACK); //background color to black
        button.setFocusPainted(false);
    }

    public EndScreen() {
        titlePanel.setBackground(new Color(0, 0, 0, 175));  //semitransparent background for the title panel

        //winner lable styleizing + adding to title panel
        winnerLabel.setFont(new Font("Roboto", Font.BOLD, 39));
        winnerLabel.setForeground(Color.WHITE);
        titlePanel.add(winnerLabel);
        
        //setup and addition (to title panel) of play again button (used when wanting to play another game of same mode)
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Ctegame.cl.show(Ctegame.cards, "GamePanel");
            }
        });
        configureButton(playAgainButton);
        titlePanel.add(playAgainButton);

        //setup and addition (to title panel) of menu button (used to return to menu)
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                Ctegame.cl.show(Ctegame.cards, "MenuPanel");
            }
        });
        configureButton(menuButton);
        titlePanel.add(menuButton);

        //setup of imgPanel
        imgPanel.setBackground(new Color(0, 0, 0, 0));  //transparent background for imgpanel in case part of it shows
        imgPanel.add(imgLabel); //add the label that contains the image

        //addition of the title panel and image panel to the EndScreen Panel (tha main one)
        add(titlePanel);
        add(imgPanel);
    }

    //THIS METHOD SHOULD BE CALLED BEFORE DISPLAYING THE ENDSCREEN (with "blue", "yellow", or "draw" as the argument)
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
    
    //getImg passes the correct image array to randImg() to return an image
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
    
    //randImg takes an image array and returns a random image from the array
    private static Image randImg(Image[] imageArray) {
        return imageArray[rand.nextInt(imageArray.length)];
    }
}


