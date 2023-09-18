package HelperFiles;
//JTO: This may prove helpful to get you started

import java.util.Scanner;

public class LogicBoard {
    private static LogicPiece[][] board;
    private static final int BOARD_SIZE = 8;
    private static final char EMPTY_CELL = '-';
    private static final char PLAYER_1_PIECE = 'X';
    private static final char PLAYER_2_PIECE = 'O';

    public static void main(String[] args) {
        initializeBoard();
        boolean player1Turn = true;
        boolean gameIsRunning = true;

        while (gameIsRunning) {
            printBoard();
            char currentPlayerPiece = player1Turn ? PLAYER_1_PIECE : PLAYER_2_PIECE;
            System.out.println("Player " + (player1Turn ? "1" : "2") + "'s turn (" + currentPlayerPiece + ")");
            int[] move = getPlayerMove();
            int startX = move[0];
            int startY = move[1];
            int endX = move[2];
            int endY = move[3];

            if (isValidMove(startX, startY, endX, endY, currentPlayerPiece)) {
                makeMove(startX, startY, endX, endY, currentPlayerPiece);
                player1Turn = !player1Turn;
            } else {
                System.out.println("Invalid move! Try again.");
            }

            // Check for game over conditions (e.g., one player has no pieces left)
            // Implement this logic according to the rules of checkers.

            // You can add AI logic here to make the computer player's move.
        }

        System.out.println("Game over!");
    }

    private static void initializeBoard() {
        board = new LogicPiece[BOARD_SIZE][BOARD_SIZE];  //use whatever class the checkers pieces are

        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                if ((x + y) % 2 != 0) {
                    board[x][y] = new LogicPiece(x, y, "empty");      //this piece should be effectively null and wont be visible
                } else if (y <= 2) {
                    board[x][y] = new LogicPiece(x, y, "player1");      //call piece constructor with either player 1 parameter or player 2 parameter (should use colors once we know what they are)
                } else if (y >= BOARD_SIZE - 3) {
                    board[x][y] = new LogicPiece(x, y, "player2");      //call piece constructor with other player parameter
                } else {
                    board[x][y] = new LogicPiece(x, y, "empty");      //this piece should be effectively null and wont be visible
                }
            }
        }
    }

    private static void printBoard() {
        System.out.println("  0 1 2 3 4 5 6 7");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static int[] getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        int[] move = new int[4];
        System.out.print("Enter your move (startX startY endX endY): ");
        for (int i = 0; i < 4; i++) {
            move[i] = scanner.nextInt();
        }
        return move;
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

        if((player1Turn == true) ^ (startPiece.getTeam().equals("player1"))){ // IF Piece is not yours
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
                if(player1Turn == true){ // 2nd Conditional Branch [Determines Forward direction]
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
        return false;// Failsafe return; shouldn't be reachable...
    }

    private static void makeMove(LogicPiece startPiece, LogicPiece endLocation){
        if (isValidMove(startPiece, endLocation)){
            board[endLocation.getX()][endLocation.getY()] = new LogicPiece(startPiece, false);
            board[startPiece.getX()][startPiece.getY()] = new LogicPiece(endLocation,  true);
        }
    }
}
