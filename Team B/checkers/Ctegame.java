import java.awt.CardLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Ctegame extends JFrame {   
    static final int FRAME_WIDTH = 720;
    public static int getFrameWidth() {
        return FRAME_WIDTH;
    }

    static final int FRAME_HEIGHT = 640;
    public static int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    static MenuPanel mp = new MenuPanel();
    static HelpPanel hp = new HelpPanel();
    static GamePanel gp = new GamePanel();

    static splashscreen splash = new splashscreen(); //this is the splashscreen panel that displays on game launch

    static EndScreen endScreen = new EndScreen();

    static CardLayout cl = new CardLayout();        //this layout contains the different panels (menu, game, etc.)
    static BackgroundPanel cards;                   //to contain the panels as cards
    
    Ctegame()
    {
        //This creates an image that will be used as the background to the game
        Image img = null;
        try{
            img = ImageIO.read(new File("Team B/images/METAL_BG.jpg")); //"https://drive.google.com/uc?id=17X4N_GoW9BAyFgYueQHDUFTvz5GwweyC"
        } catch(IOException e) {
            e.printStackTrace();
        }  
        
        cards =  new BackgroundPanel(img, BackgroundPanel.SCALED);  //This is the main panel that is displayed by the frame. 
                                            //It holds other panels which can be displayed 1 at a time using cl.show()

        cards.setLayout(cl); // setting the layout to cardlayout
        cards.add(splash, "SplashScreen"); // Addding the SplashScreen as a card
        cards.add(mp, "MenuPanel");
        cards.add(hp, "HelpPanel");
        cards.add(gp, "GamePanel");
        cards.add(endScreen, "EndScreen");
        
        add(cards); // adding the panel with cardlayout in JFrame
        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);  //frame size
        setLocationRelativeTo(null);   //frame loads in center of screen
        setVisible(true);   // frame visibility

        // Initially showing the SplashScreen
        cl.show(cards, "SplashScreen");

        // Started a timer to transition from the splashscreen to the MenuPanel after 3 seconds
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "MenuPanel"); // Transition to the MenuPanel
            }
        });
        timer.setRepeats(false); // Run only once
        timer.start();
    }
    

}
