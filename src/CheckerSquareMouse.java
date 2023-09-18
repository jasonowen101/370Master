package src;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckerSquareMouse extends MouseAdapter {

    private final CheckerSquare square;
    private static CheckerSquare currentSelection;

    CheckerSquareMouse(CheckerSquare square) {
        super();
        this.square = square;
    }

    // TODO Implement rule validation from game logic
    @Override
    public void mouseClicked(MouseEvent e) {
        // Executes on left click
        if (e.getButton() == MouseEvent.BUTTON1) {
            // Checks if no square is selected and the clicked square has a piece
            if(currentSelection == null && square.getCheckerColor() != null) {
                currentSelection = square;
                System.out.println("Square selected!");
            }
            // Checks if currentSelection has a square
            else if (currentSelection != null) {
                // Checks if the square is the same as the selection or that the square is already occupied
                // Sets currentSelection to null
                if(square.equals(currentSelection) || square.getCheckerColor() != null) {
                    currentSelection = null;
                    System.out.println("Square deselected!");
                }
                // Checks if the clicked checkerSquare does not have a piece
                else if(square.getCheckerColor() == null) {
                    square.toggleChecker(currentSelection.getCheckerColor());
                    currentSelection.toggleChecker(null);
                    currentSelection = null;
                    System.out.println("Square toggled!");
                }
            }
        }
        // Executes on right click - clears current selection
        else if (e.getButton() == MouseEvent.BUTTON3) {
            currentSelection = null;
            System.out.println("Square deselected!");
        }
    }
}
