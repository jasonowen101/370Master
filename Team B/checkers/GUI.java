package checkers;
public class GUI {
    public static String gameMode = null;
    public static boolean blueTurn;
    public static void main(String[] args){
        new Ctegame(); // Created an instance of Ctegame
        //this loop waits for a game mode to be selected before entering game loop
        while(gameMode == null){
            try{
                Thread.sleep(250);
                System.out.println("waiting for gameMode");
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        } 
        boolean gameOver = false;
        CheckerSquare[] move = new CheckerSquare[2];
        
        while(!gameOver) {
            move = nextMove(); //checkerSelected should be updated in nextMove for graphics
            while(!MoveValidator.isValidMove(blueTurn, move)){
                move = nextMove();
            }
            updateBoard(move);
            blueTurn = !blueTurn;
            if(blueTurn){
                GamePanel.setTurnLabelText("Blue's turn");
            } else {
                GamePanel.setTurnLabelText("Yellow's turn");
            }
            //checkGameOver();
            if(gameOver) {
                //do game over things
            }
        }

    } 

    private static CheckerSquare[] nextMove(){
        CheckerSquare[] move;
        if(gameMode == "pvp") {
            CheckerSquareMouse.active = true;
            while(CheckerSquareMouse.move == null) {
                try{
                    Thread.sleep(250);
                } catch(InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
            move = CheckerSquareMouse.move;
            CheckerSquareMouse.clearMove();
            CheckerSquareMouse.active = false;
            System.out.println("aiiiii");
            return move;
        }
        if(gameMode == "pvc") {
            return null;
        }
        if(gameMode == "cvc") {
            return null;
        }
        return null;
    }
    
    private static void updateBoard(CheckerSquare[] move){
        move[1].toggleChecker(move[0].getCheckerColor());
        move[0].toggleChecker(null);
    }
}