package checkers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckerSquareMouse extends MouseAdapter {

    private final CheckerSquare square;
    private static CheckerSquare currentSelection;
    private static boolean blueTurn = true;

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
                currentSelection.setSelected(true);
                System.out.println("Square selected!");
            }
            // Checks if currentSelection has a square
            else if (currentSelection != null) {
                // Checks if the square is the same as the selection or that the square is already occupied
                // Sets currentSelection to null
                if(square.equals(currentSelection) || square.getCheckerColor() != null) {
                    currentSelection.setSelected(false);
                    currentSelection = null;
                    System.out.println("Square deselected!");
                }
                // Checks if the clicked checkerSquare does not have a piece
                else if(square.getCheckerColor() == null) {
                    System.out.println("true");
                    if(isValidMove(currentSelection, square)){
                        square.toggleChecker(currentSelection.getCheckerColor());
                        currentSelection.toggleChecker(null);
                        currentSelection.setSelected(false);
                        currentSelection = null;
                        System.out.println("Square toggled!");
                        blueTurn = !blueTurn;
                    }
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

    private static boolean isValidMove(CheckerSquare startPiece, CheckerSquare endPiece) {
        /*
         * Moveset Rules:
         * Must be in bounds
         * Must be diagonals {Only Black Tiles}
         * Jumps: Jump over opponent's tile(s), landing point follows ruleset
         * Normal pieces can only move forward, kings can also move back
        */

        /* Check order:
         * Piece of Correct Team?
         * Within Bounds?
         * Is Diagonal?
         * Is Within Range?
         * Is Open Target?
         * Move or Jump?
         * King? => Forward move?
         */

         //changed player1turn and the string "player1" to isBlueTurn check and "blue"
        if((blueTurn == true) ^ (startPiece.getCheckerColor().equals(Color.BLUE))){ // IF Piece is not yours
            // (Turn = P1 AND Piece = P2) OR (Turn = P2 AND Piece = P1) {^ == XOR}
            return false;
        }else if(((endPiece.getRow() < 0) || (endPiece.getRow() >= 8)) // IF X out of bounds {0-7}
            || // OR
            ((endPiece.getCol() < 0) || (endPiece.getCol() >= 8))){ // IF Y out of bounds {0-7}
            return false;
        }else if(Math.abs(endPiece.getRow() - startPiece.getRow()) != Math.abs(endPiece.getCol() - startPiece.getCol())
        || Math.abs(endPiece.getRow() - startPiece.getRow()) > 2 ){
            /*
             * Move within Bounds, but not Diagonal
             * |OR|
             * Move within Bounds, is Diagonal, but is impossibly far {Longer than jump; 3+ spaces away}
             *
             * !!! NOT DESIGNED to handle Multijumps !!!
             */
             return false;
        }
        // else if(!endPiece.getCheckerColor().equals(null)){ // IF Target tile is not empty
        //         return false;
        // }
        else{
            if(startPiece.isKing() == true){// 1st Conditional Branch [King Vs Pawn]
                // If Piece is King => Don't need to worry about "Forward" direction
                if(endPiece.getRow() - startPiece.getRow() == 2){
                    // Is JUMP
                    int jumpedX = startPiece.getRow() + ((endPiece.getRow() - startPiece.getRow()) / 2);
                    int jumpedY = startPiece.getCol() + ((endPiece.getCol() - startPiece.getCol()) / 2);
                    CheckerSquare jumpedPiece = GamePanel.getSquares()[jumpedX][jumpedY];
                    return isValidMove(startPiece, jumpedPiece);
                }else{
                    // Is MOVE
                    return true;
                }
            }else{
                // Piece is NOT King
                if(blueTurn == true){ // 2nd Conditional Branch [Determines Forward direction]
                    if(endPiece.getCol() - startPiece.getCol() < 0){
                        // Is BACKWARD
                        return false;
                    }else{
                        // FORWARD
                        if(endPiece.getRow() - startPiece.getRow() == 2){
                            // Is JUMP
                            int jumpedX = startPiece.getRow() + ((endPiece.getRow() - startPiece.getRow()) / 2);
                            int jumpedY = startPiece.getCol() + ((endPiece.getCol() - startPiece.getCol()) / 2);
                            CheckerSquare jumpedPiece = GamePanel.getSquares()[jumpedX][jumpedY];

                            if(jumpedPiece.getClass().equals(startPiece.getClass())){
                                // Trying to jump friendly Piece
                                return false;
                            }else{
                                return true;
                            }
                        }else{
                            // Is MOVE
                            return true;
                        }
                    }
                }else{
                    // Player 2
                    if(endPiece.getCol() - startPiece.getCol() > 0){
                        // Is BACKWARD
                        return false;
                    }else{
                        // FORWARD
                        if(endPiece.getRow() - startPiece.getRow() == 2){
                            // Is JUMP
                            int jumpedX = startPiece.getRow() + ((endPiece.getRow() - startPiece.getRow()) / 2);
                            int jumpedY = startPiece.getCol() + ((endPiece.getCol() - startPiece.getCol()) / 2);
                            CheckerSquare jumpedPiece = GamePanel.getSquares()[jumpedX][jumpedY];
                            if(jumpedPiece.getClass().equals(startPiece.getClass())){
                                // Trying to jump friendly Piece
                                return false;
                            }else{
                                return true;
                            }
                        }else{
                            // Is MOVE
                            return true;
                        }
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
        int activeBluePiece = 0;
        int activeYellowPiece = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(((getCheckersSquare())[row][col]).getCheckerColor == Color.BLUE){
                    activeBluePiece = activeBluePiece + 1;
                }
                if(((getCheckersSquare())[row][col]).getCheckerColor == Color.YELLOW){
                    activeYellowPiece = activeYellowPiece + 1;
                }
            }
        }

        if (activeBluePiece == 0 && drawMoves <= 40){
            // Call win panel with blue as victor
        } else if (activeYellowPiece == 0 && drawMoves <= 40) {
            // Call win panel with yellow as victor
        } else if (drawMoves > 40){
            // Call draw panel
        }
    }

    // Call after every jump
    public static void pieceJumped(){
        drawMoves = 0;
    }

    // Call after every move that is not a jump
    public static void pieceMoved(){
        drawMoves = drawMoves + 1;
    }

}
