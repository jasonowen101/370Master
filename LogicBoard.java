//package HelperFiles;
//JTO: This may prove helpful to get you started

public class LogicBoard {
    private static LogicPiece[][] board;
    private static final int BOARD_SIZE = 8;

    public LogicPiece[][] getPieceArray() {
        return board;
    }
    // I COMMENTED OUT THIS MAIN METHOD SINCE THE GAME WILL BE RUN FROM CHECKERS CLASS, AND I WASN'T SURE IF THERE WAS ANYTHING IMPORTANT IN HERE.
    //
    // public static void main(String[] args) {
    //     initializeBoard();
    //     boolean player1Turn = true;
    //     boolean gameIsRunning = true;

    //     while (gameIsRunning) {
    //         printBoard();
    //         char currentPlayerPiece = player1Turn ? PLAYER_1_PIECE : PLAYER_2_PIECE;
    //         System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn (" + currentPlayerPiece + ")");
    //         int[] move = getPlayerMove();
    //         int startX = move[0];
    //         int startY = move[1];
    //         int endX = move[2];
    //         int endY = move[3];

    //         if (isValidMove(startX, startY, endX, endY, currentPlayerPiece)) {
    //             makeMove(startX, startY, endX, endY, currentPlayerPiece);
    //             player1Turn = !player1Turn;
    //         } else {
    //             System.out.println("Invalid move! Try again.");
    //         }

    //         // Check for game over conditions (e.g., one player has no pieces left)
    //         // Implement this logic according to the rules of checkers.

    //         // You can add AI logic here to make the computer player's move.
    //     }

    //     System.out.println("Game over!");
    // }

    public void initializeBoard() {
        board = new LogicPiece[BOARD_SIZE][BOARD_SIZE];  //use whatever class the checkers pieces are

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if ((x + y) % 2 != 0) {
                    board[x][y] = new LogicPiece(x, y, "empty");      //this piece should be effectively null and wont be visible
                } else if (y <= 2) {
                    board[x][y] = new LogicPiece(x, y, "blue");      //call piece constructor with either player 1 parameter or player 2 parameter (should use colors once we know what they are)
                } else if (y >= BOARD_SIZE - 3) {
                    board[x][y] = new LogicPiece(x, y, "yellow");      //call piece constructor with other player parameter
                } else {
                    board[x][y] = new LogicPiece(x, y, "empty");      //this piece should be effectively null and wont be visible
                }
            }
        }
    }

    private static boolean isValidMove(LogicPiece startPiece, LogicPiece endPiece) {
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
        if((Checkers.isBlueTurn() == true) ^ (startPiece.getTeam().equals("blue"))){ // IF Piece is not yours
            // (Turn = P1 AND Piece = P2) OR (Turn = P2 AND Piece = P1) {^ == XOR}
            return false;
        }else if(((endPiece.getX() < 0) || (endPiece.getX() >= BOARD_SIZE)) // IF X out of bounds {0-7}
            || // OR
            ((endPiece.getY() < 0) || (endPiece.getY() >= BOARD_SIZE))){ // IF Y out of bounds {0-7}
            return false;
        }else if(Math.abs(endPiece.getX() - startPiece.getX()) != Math.abs(endPiece.getY() - startPiece.getY())
        || Math.abs(endPiece.getX() - startPiece.getX()) > 2 ){
            /*
             * Move within Bounds, but not Diagonal
             * |OR|
             * Move within Bounds, is Diagonal, but is impossibly far {Longer than jump; 3+ spaces away}
             *
             * !!! NOT DESIGNED to handle Multijumps !!!
             */
             return false;
        }else if(!endPiece.getTeam().equals("empty")){ // IF Target tile is not empty
                return false;
        }else{
            if(startPiece.getKingStatus() == true){// 1st Conditional Branch [King Vs Pawn]
                // If Piece is King => Don't need to worry about "Forward" direction
                if(endPiece.getX() - startPiece.getX() == 2){
                    // Is JUMP
                    int jumpedX = startPiece.getX() + ((endPiece.getX() - startPiece.getX()) / 2);
                    int jumpedY = startPiece.getY() + ((endPiece.getY() - startPiece.getY()) / 2);
                    LogicPiece jumpedPiece = board[jumpedX][jumpedY];
                    return isValidMove(startPiece, jumpedPiece);
                }else{
                    // Is MOVE
                    return true;
                }
            }else{
                // Piece is NOT King
                if(Checkers.isBlueTurn() == true){ // 2nd Conditional Branch [Determines Forward direction]
                    if(endPiece.getY() - startPiece.getY() < 0){
                        // Is BACKWARD
                        return false;
                    }else{
                        // FORWARD
                        if(endPiece.getX() - startPiece.getX() == 2){
                            // Is JUMP
                            int jumpedX = startPiece.getX() + ((endPiece.getX() - startPiece.getX()) / 2);
                            int jumpedY = startPiece.getY() + ((endPiece.getY() - startPiece.getY()) / 2);
                            LogicPiece jumpedPiece = board[jumpedX][jumpedY];

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
                    if(endPiece.getY() - startPiece.getY() > 0){
                        // Is BACKWARD
                        return false;
                    }else{
                        // FORWARD
                        if(endPiece.getX() - startPiece.getX() == 2){
                            // Is JUMP
                            int jumpedX = startPiece.getX() + ((endPiece.getX() - startPiece.getX()) / 2);
                            int jumpedY = startPiece.getY() + ((endPiece.getY() - startPiece.getY()) / 2);
                            LogicPiece jumpedPiece = board[jumpedX][jumpedY];
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

    public boolean makeMove(LogicPiece [] moves){
        LogicPiece startPiece = moves[0];
        LogicPiece endLocation = moves[1];
        if (isValidMove(startPiece, endLocation)){
            //here instead of creating a new piece can we not just reuse start piece or change the flags of the piece? idk just wondering
            board[endLocation.getX()][endLocation.getY()] = new LogicPiece(startPiece, false);
            board[startPiece.getX()][startPiece.getY()] = new LogicPiece(endLocation,  true);
            return true;
        } else {
            return false;
        }
    }
}
