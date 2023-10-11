package checkers;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Ctegame extends JFrame
{   
    static MenuPanel mp = new MenuPanel();
    static HelpPanel hp = new HelpPanel();
    static GamePanel gp = new GamePanel();

    static splashscreen splash = new splashscreen(); // Adding the SplashScreen

    static CardLayout cl = new CardLayout();
    static JPanel cards = new JPanel();  // to contain the panels as cards
    
    Ctegame()
    {
        cards.setLayout(cl); // setting the layout to cardlayout
        cards.add(splash, "SplashScreen"); // Addding the SplashScreen as a card
        cards.add(mp, "MenuPanel");
        cards.add(hp, "HelpPanel");
        cards.add(gp, "GamePanel");
        
        // Initially showing the SplashScreen
        cl.show(cards, "SplashScreen");
        
        add(cards); // adding the panel with cardlayout in JFrame
        
        setTitle("Checkers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 720); // frame size
        setVisible(true);   // frame visibility

        // Started a timer to transition from the SplashScreen to the MenuPanel after 3 seconds
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cl.show(cards, "MenuPanel"); // Transition to the MenuPanel
            }
        });
        timer.setRepeats(false); // Run only once
        timer.start();
    }
    

}
