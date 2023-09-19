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
            


            // Check for game over conditions (e.g., one player has no pieces left)
            // Implement this logic according to the rules of checkers.

            // You can add AI logic here to make the computer player's move.
        }
        printBoardToConsole();
        System.out.println("Game over!");
    }
    //GUI should basically do this but with cool graphics
    public static void printBoardToConsole() {
        for(int y = 0; y < 8; ++y){
            for(int x = 0; x < 8; ++x){
                if(gameBoard.getBoard()[x][y].getTeam() == "blue"){
                    System.out.print(" B ");
                } else if(gameBoard.getBoard()[x][y].getTeam() == "yellow") {
                    System.out.print(" Y ");
                } else {
                    System.out.print(" * ");
                }
            }
            System.out.println("");
        }
    }
}