package checkers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static checkers.GamePanel.getSquares;
import checkers.CheckerSquare;

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
                if(selectedSquare == null && ((square.getCheckerColor() == Color.BLUE) == GUI.blueTurn)) {
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
        }
        //return false;// Failsafe return; shouldn't be reachable...
    }

    //put at top of file
    public static int drawMoves = 0;

    //Call everywhere checkPromote is called
    public static void checkEnd(){
        checkers.CheckerSquare temp;
        int activeBluePiece = 0;
        int activeYellowPiece = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                temp = ((getSquares())[row][col]);
                if((temp.getCheckerColor()) == Color.BLUE){
                    activeBluePiece = activeBluePiece + 1;
                }
                if((temp.getCheckerColor()) == Color.YELLOW){
                    activeYellowPiece = activeYellowPiece + 1;
                }
            }
        }

        if (activeBluePiece == 0 && drawMoves <= 40){
            // Call win panel with yellow as victor
        } else if (activeYellowPiece == 0 && drawMoves <= 40) {
            // Call win panel with blue as victor
        } else if (drawMoves > 40){
            // Call draw panel
        }
    }

    // Call after every jump
    public static void pieceJumped(){
        drawMoves = 0;
            // Executes on right click - clears current selection
            else if (e.getButton() == MouseEvent.BUTTON3 && selectedSquare != null) {
                selectedSquare.setSelected(false);
                selectedSquare = null;
            }
        }
    }
}
