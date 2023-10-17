package checkers;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckerSquareMouse extends MouseAdapter {

    private final CheckerSquare square;
    private static CheckerSquare currentSelection;
    public static boolean blueTurn = true;

    public static boolean isBlueTurn() {
        return blueTurn;
    }

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
                    if(isValidMove(currentSelection, square)){
                        square.toggleChecker(currentSelection.getCheckerColor());
                        System.out.println("MultiJump: " + Boolean.toString(checkMultiJump(square, currentSelection.getCheckerColor())));
                        currentSelection.toggleChecker(null);
                        currentSelection.setSelected(false);
                        currentSelection = null;
                        System.out.println("Square toggled!");
                        blueTurn = !blueTurn;
                        if(blueTurn){
                            GamePanel.setTurnLabelText("Blue's turn");
                        } else {
                            GamePanel.setTurnLabelText("Yellow's turn");
                        }
                    }
                }
            }
        }
        // Executes on right click - clears current selection
        // BUG: Right Click when nothing is selected causes NullPointerException
        // quick fix by Nathan Borchelt, feel free to change
        else if (e.getButton() == MouseEvent.BUTTON3) {

            if (currentSelection != null){
                currentSelection.setSelected(false);
                currentSelection = null;
                System.out.println("Square deselected!");
            }
            else System.out.println("Nothing was selected");
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
            System.out.println("1");
            return false;
        }else if(((endPiece.getCol() < 0) || (endPiece.getCol() >= 8)) // IF X out of bounds {0-7}
            || // OR
            ((endPiece.getRow() < 0) || (endPiece.getRow() >= 8))){ // IF Y out of bounds {0-7}
            System.out.println("2");
            return false;
        }else if(Math.abs(endPiece.getCol() - startPiece.getCol()) != Math.abs(endPiece.getRow() - startPiece.getRow())
        || Math.abs(endPiece.getCol() - startPiece.getCol()) > 2 ){


            /*
             * Move within Bounds, but not Diagonal
             * |OR|
             * Move within Bounds, is Diagonal, but is impossibly far {Longer than jump; 3+ spaces away}
             *
             * !!! NOT DESIGNED to handle Multijumps !!!
             */
            System.out.println("3");
            return false;
        }
        // else if(!endPiece.getCheckerColor().equals(null)){ // IF Target tile is not empty
        //         return false;
        // }
        else{
            if(startPiece.isKing() == true){// 1st Conditional Branch [King Vs Pawn]
                // If Piece is King => Don't need to worry about "Forward" direction
                if(Math.abs(endPiece.getCol() - startPiece.getCol()) == 2){
                    // Is JUMP
                    int jumpedX = startPiece.getCol() + ((endPiece.getCol() - startPiece.getCol()) / 2);
                    int jumpedY = startPiece.getRow() + ((endPiece.getRow() - startPiece.getRow()) / 2);
                    CheckerSquare jumpedPiece = GamePanel.getSquares()[jumpedY][jumpedX];
                    System.out.println("4");
                    if(isValidMove(startPiece, jumpedPiece)){
                        checkPromote(endPiece, startPiece);
                        jumpedPiece.toggleChecker(null);
                        return true;
                    }
                    return false;
                }else{
                    // Is MOVE
                    checkPromote(endPiece, startPiece);
                    return true;
                }
            }else{
                // Piece is NOT King
                if(blueTurn == true){ // 2nd Conditional Branch [Determines Forward direction]
                    if(endPiece.getRow() - startPiece.getRow() > 0){
                        // Is BACKWARD
                        System.out.println("start" + startPiece.getRow()+ "end" + endPiece.getRow());
                        System.out.println("5");
                        return false;
                    }else{
                        // FORWARD
                        if(Math.abs(endPiece.getCol() - startPiece.getCol()) == 2){
                            // Is JUMP
                            int jumpedX = startPiece.getCol() + ((endPiece.getCol() - startPiece.getCol()) / 2);
                            int jumpedY = startPiece.getRow() + ((endPiece.getRow() - startPiece.getRow()) / 2);
                            CheckerSquare jumpedPiece = GamePanel.getSquares()[jumpedY][jumpedX];

                            if(jumpedPiece.getCheckerColor().equals(startPiece.getCheckerColor())){
                                // Trying to jump friendly Piece
                                System.out.println("6");
                                return false;
                            }else{
                                jumpedPiece.toggleChecker(null);
                                checkPromote(endPiece, startPiece);
                                //current personal theory is check for multi jump here
                                return true;
                            }
                        }else{
                            // Is MOVE
                            checkPromote(endPiece, startPiece);
                            return true;
                        }
                    }
                }else{
                    // Player 2
                    if(endPiece.getRow() - startPiece.getRow() < 0){
                        // Is BACKWARD
                        System.out.println("7");
                        return false;
                    }else{
                        // FORWARD
                        if(Math.abs(endPiece.getCol() - startPiece.getCol()) == 2){
                            // Is JUMP
                            int jumpedX = startPiece.getCol() + ((endPiece.getCol() - startPiece.getCol()) / 2);
                            int jumpedY = startPiece.getRow() + ((endPiece.getRow() - startPiece.getRow()) / 2);
                            CheckerSquare jumpedPiece = GamePanel.getSquares()[jumpedY][jumpedX];
                            if(jumpedPiece.getCheckerColor().equals(startPiece.getCheckerColor())){
                                // Trying to jump friendly Piece
                                System.out.println("8");
                                return false;
                            }else{
                                jumpedPiece.toggleChecker(null);
                                checkPromote(endPiece, startPiece);
                                return true;
                            }
                        }else{
                            // Is MOVE
                            checkPromote(endPiece, startPiece);
                            return true;
                        }
                    }
                }
            }
        }
        //return false;// Failsafe return; shouldn't be reachable...
    }


    //current testing action for multiJump
    private static boolean checkMultiJump(CheckerSquare recheckPiece, Color pieceColor){
        int x = recheckPiece.getCol();
        int y = recheckPiece.getRow();
        Color playerColor = pieceColor;
        Color enemyColor = playerColor == CheckerSquare.TEAM1 ? CheckerSquare.TEAM2 : CheckerSquare.TEAM1;
        CheckerSquare[][] gameBoard =  GamePanel.getSquares();
        boolean jUpLeft = false;
        boolean jDownLeft = false;
        boolean jUpRight = false;
        boolean jDownRight = false;

        System.out.println("".join(" ", "x", Integer.toString(x), "y", Integer.toString(y)));
        if(recheckPiece.isKing()){
            try{
                jUpLeft = ((gameBoard[x-1][y-1].getCheckerColor() == enemyColor) && (gameBoard[x-2][y-2].getCheckerColor() == null));
            }
            catch(Exception e){
                jUpLeft = false;
            }
            try{
                jDownLeft = ((gameBoard[x-1][y+1].getCheckerColor() == enemyColor) && (gameBoard[x-2][y+2].getCheckerColor() == null));
            }
            catch(Exception e){
                jDownLeft = false;
            }
            try{
                jUpRight = ((gameBoard[x+1][y-1].getCheckerColor() == enemyColor) && (gameBoard[x+2][y-2].getCheckerColor() == null));
            }
            catch(Exception e){
                jUpRight = false;
            }
            try{
                jDownRight = ((gameBoard[x+1][y+1].getCheckerColor() == enemyColor) && (gameBoard[x+2][y+2].getCheckerColor() == null));
            }
            catch(Exception e){
                jDownRight = false;
            }
        }

        else{
            System.out.println("Not King Multi Jump");
            if(playerColor == CheckerSquare.TEAM2){
                try{
                    jUpLeft = ((gameBoard[x-1][y-1].getCheckerColor() == enemyColor) && (gameBoard[x-2][y-2].getCheckerColor() == null));
                    System.out.println("jUpLeft:"+jUpLeft);
                }
                catch(Exception e){
                    jUpLeft = false;
                }
                try{
                    jUpRight = ((gameBoard[x+1][y-1].getCheckerColor() == enemyColor) && (gameBoard[x+2][y-2].getCheckerColor() == null));
                    System.out.println("jUpRight:"+jUpRight);
                }
                catch(Exception e){
                    jUpRight = false;
                }
            }
            else{
                try{
                    jDownLeft = ((gameBoard[x-1][y+1].getCheckerColor() == enemyColor) && (gameBoard[x-2][y+2].getCheckerColor() == null));
                    System.out.println("jDownLeft:"+jDownLeft);
                }
                catch(Exception e){
                    jDownLeft = false;
                }
                try{
                    jDownRight = ((gameBoard[x+1][y+1].getCheckerColor() == enemyColor) && (gameBoard[x+2][y+2].getCheckerColor() == null));
                    System.out.println("jDownRight:"+jDownRight);
                }
                catch(Exception e){
                    jDownRight = false;
                }
            }

        }

        return jUpLeft || jDownLeft || jUpRight || jDownRight;
    }

    //this check promote method not only checks for promote but also toggles the king status...it must be called during any valid move
    private static void checkPromote(CheckerSquare endPiece, CheckerSquare startPiece){
        endPiece.setIsKing(startPiece.isKing());
        if(startPiece.getCheckerColor().equals(Color.BLUE) && (endPiece.getRow() == 0) || startPiece.getCheckerColor().equals(Color.YELLOW) && (endPiece.getRow() == 7)){
            endPiece.setIsKing(true);
        }
    }
}
