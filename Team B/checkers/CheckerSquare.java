package checkers;

import javax.swing.*;
import java.awt.*;

public class CheckerSquare extends JPanel {
    public static final Color TEAM1 = Color.YELLOW;
    public static final Color TEAM2 = Color.BLUE;
    private Color checkerColor = null;
    private boolean isKing;
    private int row, col;

    CheckerSquare(int row, int col) {
        this.row = row;
        this.col = col;
        isKing = false;
        setPreferredSize(new Dimension(50, 50));
    }

    public void toggleChecker(Color color) {
        //Maybe want to get rid of null check because you need to pass in something anyways
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

        // TODO Implement highlighting the piece or square when selected
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
        }
    }

    //is equivalent to LogicPiece getTeam
    public Color getCheckerColor() {
        return checkerColor;
    }

    public boolean teamCheck(Color color){
        return this.checkerColor.equals(color);
    }

    //is euqivilent to getY
    public int getRow(){
        return this.row;
    }

    //is euqivilent to getY
    public int getColumn(){
        return this.col;
    }

    //return the xy as a 1d list, can remove is deemed useless
    public int[] getPosition(){
        return new int[] {this.row, this.col};
    }

    //is equivilent to getKingStatus
    public boolean isKing(){
        return this.isKing;    }


    public void setIsKing(boolean isKing) {
        this.isKing = isKing;
    }

    public void promote(){
        if (this.checkerColor == CheckerSquare.TEAM1 && this.row == 0){
          this.isKing = true;
        }
        else if (this.checkerColor == CheckerSquare.TEAM2 && this.row == 7){
          this.isKing = true;
        }
      }
}
