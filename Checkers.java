import java.util.InputMismatchException;
import java.util.Scanner;
public class Checkers {
    private static LogicBoard gameBoard = new LogicBoard();
    private static boolean blueTurn;
    private static Scanner scn = new Scanner(System.in);
    public static boolean isBlueTurn() {
        return blueTurn;
    }
    public static void main(String[] args) {
        gameBoard.initializeBoard();
        boolean gameIsRunning = true;
        blueTurn = true;
        //MAIN GAME LOOP
        while (gameIsRunning) {
            printBoardToConsole();
            while(true){
                if(gameBoard.makeMove(getPlayerMove())) {
                    blueTurn = !blueTurn;
                    break;
                } else {
                    System.out.println("Invalid move. Please try again.");
                }
            }
            // Check for game over conditions (e.g., one player has no pieces left)
            // Implement this logic according to the rules of checkers.

            // You can add AI logic here to make the computer player's move.
        }
        //on end game condition this executes
        System.out.println("Game over!");
    }

    //method to get move (returns array containing start and end piece) SHOULDNT BE NEEDED ONCE GUI IS INTEGRATED
    private static LogicPiece[] getPlayerMove() {
        String team;
        if(blueTurn){
            team = "Blue";
        } else {
            team = "Yellow";
        }
        System.out.println(team + "'s turn");
        System.out.println("Enter your move (startX startY endX endY): ");
        int startX;
        int startY;
        int endX;
        int endY;
        while(true) {
            try{
                startX = scn.nextInt();
                startY = scn.nextInt();
                endX = scn.nextInt();
                endY = scn.nextInt();
                break;
            } catch(InputMismatchException e) {
                System.out.println("Invalid Input");
                scn.nextLine(); //should prevent infinite exception loop i hope
            }
        }
        return new LogicPiece[]{gameBoard.getPieceArray()[startX][startY], gameBoard.getPieceArray()[endX][endY]};
    }

    //GUI should basically do this but with cool graphics
    public static void printBoardToConsole() {
        for(int y = 0; y < 8; ++y){
            for(int x = 0; x < 8; ++x){
                if(gameBoard.getPieceArray()[x][y].getTeam() == "blue"){
                    System.out.print(" B ");
                } else if(gameBoard.getPieceArray()[x][y].getTeam() == "yellow") {
                    System.out.print(" Y ");
                } else {
                    System.out.print(" * ");
                }
            }
            System.out.println("");
        }
    }
}