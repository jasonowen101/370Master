package checkers;

import java.awt.Color;

public class GUI {
    public static String gameMode = null;
    public static boolean gameOver = true;
    public static int drawMoves = 0; //for check end
    public static boolean blueTurn;
    public static BotBoi jarvis;
    public static void main(String[] args){
        new Ctegame(); //Created an instance of Ctegame
        
        //this loop waits for a game mode to be selected before entering game loop
        while(gameMode == null || gameOver){
            try{
                Thread.sleep(100);
                //waiting for game mode...we have a delay so the machine doesn't kill itself checking the while condition
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        } 

        
        //create bots here
        jarvis = new BotBoi(Color.YELLOW);

        CheckerSquare[] move = new CheckerSquare[2];
        //GAME LOOP 
        while(!gameOver) {
            move = nextMove();      //gets a move based on game mode.....also for the bots, checkerSelected should be updated in nextMove for graphics so we can tell whats going on
            while(!MoveValidator.isValidMove(blueTurn, move)){      //check if the move is valid and if not, get a new one
                move = nextMove();                                  //^^^The AIs should not rely on this check and should validate their own moves with the same method...in other words
            }                                                       //      this check is mainly for the human players
            updateBoard(move);      //updates game board state and graphics
            blueTurn = !blueTurn;   //changing of turns
            if(blueTurn){
                GamePanel.setTurnLabelText("Blue's turn");
            } else {
                GamePanel.setTurnLabelText("Yellow's turn");
            }
            gameOver = checkGameOver();
        }

    } 

    private static CheckerSquare[] nextMove(){
        CheckerSquare[] move;
        if(gameMode == "pvp") {
            return getMoveFromMouse();
        }
        if(gameMode == "pvc") {
            if(!blueTurn){
                move = jarvis.getMove();   //bot makes move here
                return move;
            } else {
                return getMoveFromMouse();
            }
        }
        if(gameMode == "cvc") {
            return null;
        }
        return null;
    }
    
    private static CheckerSquare[] getMoveFromMouse() {
        CheckerSquareMouse.active = true;
        while(CheckerSquareMouse.getMove() == null) {
            try{
                Thread.sleep(75);
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        CheckerSquare[] move;
        move = CheckerSquareMouse.getMove();
        CheckerSquareMouse.clearMove();
        CheckerSquareMouse.active = false;
        return move;
    }
    
    //This method updates the game board state as well as the board graphics 
    private static void updateBoard(CheckerSquare[] move){
        move[1].toggleChecker(move[0].getCheckerColor());
        move[0].toggleChecker(null);
    }

    //Call everywhere checkPromote is called
    public static boolean checkGameOver(){
        int activeBluePiece = 0;
        int activeYellowPiece = 0;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(((GamePanel.getSquares())[row][col]).getCheckerColor() == Color.BLUE){
                    activeBluePiece = activeBluePiece + 1;
                }
                if(((GamePanel.getSquares())[row][col]).getCheckerColor() == Color.YELLOW){
                    activeYellowPiece = activeYellowPiece + 1;
                }
            }
        }

        if (activeBluePiece == 0 && drawMoves <= 40){
            // Call win panel with blue as victor
            GamePanel.setTurnLabelText("Blue Wins!");
            return true;
        } else if (activeYellowPiece == 0 && drawMoves <= 40) {
            // Call win panel with yellow as victor
            GamePanel.setTurnLabelText("Yellow Wins!");
            return true;
        } else if (drawMoves > 40){
            // Call draw panel
            GamePanel.setTurnLabelText("It's a draw!");
            return true;
        }
        return false;
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