package checkers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckerSquareMouse extends MouseAdapter {

    private final CheckerSquare square;
    private static CheckerSquare currentSelection;
    public static boolean active = false;
    public static CheckerSquare[] move;

    public static void clearMove() {
        move = null;
    }

    CheckerSquareMouse(CheckerSquare square) {
        super();
        this.square = square;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(active){
            // Executes on left click
            if (e.getButton() == MouseEvent.BUTTON1) {
                // Checks if no square is selected and the clicked square has a piece
                if(currentSelection == null && ((square.getCheckerColor() == Color.BLUE) == GUI.blueTurn)) {
                    currentSelection = square;
                    currentSelection.setSelected(true);
                }
                // Checks if currentSelection has a square
                else if (currentSelection != null) {
                    // Checks if the square is the same as the selection or that the square is already occupied
                    // Sets currentSelection to null
                    if(square.equals(currentSelection) || square.getCheckerColor() != null) {
                        currentSelection.setSelected(false);
                        currentSelection = null;
                    }
                    // Checks if the clicked checkerSquare does not have a piece
                    else if(square.getCheckerColor() == null) {
                        move = new CheckerSquare[] {currentSelection, square};
                        currentSelection.setSelected(false);
                        currentSelection = null;
                    }
                }
            }
            // Executes on right click - clears current selection
            else if (e.getButton() == MouseEvent.BUTTON3) {
                currentSelection.setSelected(false);
                currentSelection = null;
                System.out.println("Square deselected!");
            }
    
        }
    }
}
