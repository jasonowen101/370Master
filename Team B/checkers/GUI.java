package checkers;

import javax.swing.*;

public class GUI {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                new Ctegame(); // Created an instance of Ctegame
            }
        });
    }
}