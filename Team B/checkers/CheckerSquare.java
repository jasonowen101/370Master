package checkers;

import javax.swing.*;
import java.awt.*;

public class CheckerSquare extends JPanel {
    public static final Color TEAM1 = Color.YELLOW;
    public static final Color TEAM2 = Color.BLUE;
    private Color checkerColor = null;
    private boolean isKing;
    public boolean isKing() {
        return isKing;
    }

    private int row, col;
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private boolean selected;

    public void setSelected(boolean s){
        this.selected = s;
        repaint(); //highlights or dehighlights the selected checker
    }

    CheckerSquare(int row, int col) {
        this.row = row;
        this.col = col;
        isKing = false;
        setPreferredSize(new Dimension(50, 50));
    }

    public void toggleChecker(Color color) {
        if (checkerColor == null) {
            checkerColor = color;
        } else {
            checkerColor = null;
        }
        repaint(); // Repaint the square to show or hide the checker
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the square
        g.setColor((row + col) % 2 == 0 ? Color.WHITE : Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the checker if it exists
        if (checkerColor != null) {
            g.setColor(checkerColor);
            g.fillOval(5, 5, getWidth() - 10, getHeight() - 10);
            if(isKing) {
                // TODO Implement graphics for a piece that is a king
            }
            if(selected) {      //check if selected and highlight if so
                g.setColor(Color.RED);
                g.drawOval(5, 5, getWidth() - 10, getHeight() - 10);
            }
        }
    }
    
    public Color getCheckerColor() {
        return checkerColor;
    }

    //Names are only place orders, Team1 is the placeholder for the team who's pieces start on the bottom of the board with the top left being (0,0). Team2 is the opposite
    //Feel free to adjust as needed. Also, this method should be called at the end of the movement and jump for the piece that is at the new location.
    public void promote() {
        if (checkerColor == TEAM1 && row == 0){
            isKing = true;
            //Call method that changes a piece to appear to be a king or however that is handled
            repaint(); // Repaint the square to show change in piece
        }
        if (checkerColor == TEAM2 && row == 8){
            isKing = true;
            //Call method that changes a piece to appear to be a king or however that is handled
            repaint(); // Repaint the square to show change in piece
        }
    }
    /*
    public void setIsKing(boolean isKing) {
        this.isKing = isKing;
    }
    */
}
