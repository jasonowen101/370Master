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


    public void setIsKing(boolean king){
        isKing = king;
    }

    public void setKing(boolean king){
        isKing = king;
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

    public CheckerSquare(int row, int col) {
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
            g.setColor(Color.BLACK);   
            g.drawOval(9, 9, getWidth() - 18, getHeight() - 18);
            if(isKing) {
                //graphics for a piece that is a king (its a 7 point crown)
                g.setColor(Color.BLACK);
                int offset = 18;
                g.fillPolygon(new int[]{getWidth() - 26 - offset, getWidth() - 24 - offset, getWidth() - 8 - offset, getWidth() - 6 - offset, getWidth() - 12 - offset, getWidth() - 16 - offset, getWidth() - 20 - offset}, 
                new int[]{getHeight() - 20 - offset, getHeight() - 10 - offset, getHeight() - 10 - offset, getHeight() - 20 - offset, getHeight() - 16 - offset, getHeight() - 24 - offset, getHeight() - 16 - offset}, 7);
            }
            if(selected) {      //check if selected and highlight if so
                g.setColor(Color.RED);
                g.drawOval(5, 5, getWidth() - 10, getHeight() - 10);
            }
        }
    }

    /**
     * @param checkerColor the checkerColor to set
     */
    public void setCheckerColor(Color checkerColor) {
        this.checkerColor = checkerColor;
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
}
