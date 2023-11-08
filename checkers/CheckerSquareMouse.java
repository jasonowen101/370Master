package checkers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckerSquareMouse extends MouseAdapter {

    private final CheckerSquare square;
    private static CheckerSquare selectedSquare;
    public static boolean active = false;
    private static CheckerSquare[] move;

    public static CheckerSquare[] getMove() {
        return move;
    }
    public static void clearMove() {
        move = null;
    }

    public static void clearSelection() {
        selectedSquare = null;
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
                // Checks if no square is selected and the clicked square has a piece and that the piece matches turn color
                if(selectedSquare == null && ((square.getCheckerColor() == Color.BLUE) == GUI.blueTurn) && square.getCheckerColor() != null) {
                    selectedSquare = square;
                    selectedSquare.setSelected(true);
                }
                // Checks if selectedSquare has a square
                else if (selectedSquare != null) {
                    // Checks if the square is the same as the selection or that the square is already occupied
                    // Sets selectedSquare to null
                    if(square.equals(selectedSquare) || square.getCheckerColor() != null) {
                        selectedSquare.setSelected(false);
                        selectedSquare = null;
                    }
                    // Checks if the clicked square does not have a piece
                    else if(square.getCheckerColor() == null) {
                        move = new CheckerSquare[] {selectedSquare, square};
                        selectedSquare.setSelected(false);
                        selectedSquare = null;
                    }
                }
            }
            // Executes on right click - clears current selection
            else if (e.getButton() == MouseEvent.BUTTON3 && selectedSquare != null) {
                selectedSquare.setSelected(false);
                selectedSquare = null;
            }
        }
    }
}
