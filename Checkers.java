import java.util.Scanner;

public class Checkers {
    private static LogicBoard gameBoard = new LogicBoard();
    private static boolean blueTurn;
    public static boolean isBlueTurn() {
        return blueTurn;
    }
    public static void main(String[] args) {
        gameBoard.initializeBoard();
        boolean gameIsRunning = false;
        blueTurn = true;
        while (gameIsRunning) {
            gameBoard.makeMove(getPlayerMove());

            printBoardToConsole();
            // Check for game over conditions (e.g., one player has no pieces left)
            // Implement this logic according to the rules of checkers.

            // You can add AI logic here to make the computer player's move.
        }
        
        System.out.println("Game over!");
    }

    //method to get move (returns array containing start and end piece)
    private static LogicPiece[] getPlayerMove() {
        String team;
        if(blueTurn){
            team = "Blue";
        } else {
            team = "Yellow";
        }
        System.out.println(team + "'s turn");
        Scanner scanner = new Scanner(System.in);
        int[] move = new int[4];
        System.out.print("Enter your move (startX startY endX endY): ");
        for (int i = 0; i < 4; i++) {
            move[i] = scanner.nextInt();
        }
        scanner.close();
        return new LogicPiece[]{gameBoard.getPieceArray()[move[0]][move[1]], gameBoard.getPieceArray()[move[2]][move[3]]};
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