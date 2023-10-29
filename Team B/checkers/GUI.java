package checkers;

import java.awt.Color;

public class GUI {
    public static String gameMode = null;
    public static boolean gameOver = true;
    public static int drawMoves = 0; //for check end
    public static boolean blueTurn;
    public static BotBoi jarvis;     //Jarvis will die...he's just here to see if things work
    public static void main(String[] args){
        new Ctegame(); //Created an instance of Ctegame
        
        //this loop waits for a game mode to be selected and for game to start before entering game loop
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
        while(true) {
            if(!gameOver) {
                move = nextMove();      //gets a move based on game mode.....also for the bots, checkerSelected should be updated in nextMove for graphics so we can tell whats going on
                while(!MoveValidator.isValidMove(blueTurn, move)){      //check if the move is valid and if not, get a new one
                    move = nextMove();                                  //^^^The AIs should not rely on this check and should validate their own moves with the same method...in other words
                }                                                       //      this check is mainly for the human players
                updateBoard(move);      //updates game board state and graphics
                blueTurn = !blueTurn;   //changing of turns
                if(blueTurn){           //this just displays whose turn it is
                    GamePanel.setTurnLabelText("Blue's turn");
                } else {
                    GamePanel.setTurnLabelText("Yellow's turn");
                }
                gameOver = checkGameOver();     //check to see if anybody has won
            } else{
                //do something to reset game  (might need to sleep thread)
            }
        }

    } 

    private static CheckerSquare[] nextMove(){
        CheckerSquare[] move;
        if(gameMode == "pvp") {         //human v human game mode
            return getMoveFromMouse();
        }
        if(gameMode == "pvc") {         //human v pc game mode
            if(!blueTurn){
                move = jarvis.getMove();   //bot makes move here
                move[0].setSelected(true);      //"animation" of bot move starts here
                try{                              //bots selected piece gets highlighted 
                    Thread.sleep(1000);    //the piece stays highlighted for a second for player to see
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                 move[0].setSelected(false); 
                return move;
            } else {
                return getMoveFromMouse();  //player makes move here if it's their turn
            }
        }
        if(gameMode == "cvc") {         //This will work just like pvc but with two bots
            return null;
        }
        return null;
    }
    
    private static CheckerSquare[] getMoveFromMouse() { //this function kinda scuffed but it works for now....if any one has an idea of how to make better lmk
        CheckerSquareMouse.active = true;               //activates the Mouse adapter
        while(CheckerSquareMouse.getMove() == null) {   //continually checks if the player has input a move
            try{
                Thread.sleep(75);
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        CheckerSquare[] move;                           //in order to be able to clear the move from mouse and still be able to use it
        move = CheckerSquareMouse.getMove();            //  I used a place holder move
        CheckerSquareMouse.clearMove();
        CheckerSquareMouse.active = false;
        return move;
    }
    
    //This method updates the game board state as well as the board graphics 
    private static void updateBoard(CheckerSquare[] move){
        move[1].toggleChecker(move[0].getCheckerColor());       //this toggles the square where piece is going 
        move[0].toggleChecker(null);                      //this toggles the square where piece came from....jumped pieces are toggled in MoveValidator isValidMove()
    }

    //Call everywhere checkPromote is called....I actually just call it at the end of every game loop iteration 
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
            GamePanel.setTurnLabelText("Blue Wins!");       //This will do for now but a victory panel would be nice
            return true;
        } else if (activeYellowPiece == 0 && drawMoves <= 40) {
            // Call win panel with yellow as victor
            GamePanel.setTurnLabelText("Yellow Wins!");     //^^^^
            return true;
        } else if (drawMoves > 40){
            // Call draw panel
            GamePanel.setTurnLabelText("It's a draw!");     //^^^^^
            return true;
        }
        return false;
    }

    // Call after every jump...right now this is called in MoveValidator isValidMove();
    public static void pieceJumped(){
        drawMoves = 0;
    }

    // Call after every move that is not a jump....called in checkPromote of MoveValidator b/c checkPromote called before every valid move
    public static void pieceMoved(){
        drawMoves = drawMoves + 1;
    }
}