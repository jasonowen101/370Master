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
            g.setColor(Color.BLACK);   
            g.drawOval(9, 9, getWidth() - 18, getHeight() - 18);
            if(isKing) {
                // TODO Implement graphics for a piece that is a king (its a 7 point crown)
                g.setColor(Color.BLACK);
                g.fillPolygon(new int[]{getWidth() - 26 - 9, getWidth() - 24 - 9, getWidth() - 8 - 9, getWidth() - 6 - 9, getWidth() - 12 - 9, getWidth() - 16 - 9, getWidth() - 20 - 9}, 
                new int[]{getHeight() - 20 - 9, getHeight() - 10 - 9,getHeight() - 10 - 9, getHeight() - 20 - 9, getHeight() - 16 - 9, getHeight() - 24 - 9, getHeight() - 16 - 9}, 7);
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

    public void setIsKing(boolean isKing) {
        this.isKing = isKing;
    }
}
