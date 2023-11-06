package checkers;

import java.awt.Color;

public class GUI {
    public static String gameMode = null;   // 3 possible values, "pvp" (person vs person) "pvc" (person vs computer) "cvc" (computer vs computer)
    public static boolean gameOver = true;  //game is over when gameOver = true
    public static int drawMoves = 0;        //for check end
    public static boolean blueTurn;         //its blue's turn when blueTurn = true
    public static BotBoi jarvis;            //this is where you can initialize bots...Jarvis will die =(...he's just here to see if things work
    public static void main(String[] args){
        new Ctegame(); //Created an instance of Ctegame, which constructs the GUI
        
        /* this loop waits for a game mode to be selected and for game to start before entering game loop...
        The reasoning for sleeping the thread is so the while loop doesn't bang out condition checks as fast
        as possible and get stuck or slow things down.*/
        while(gameMode == null || gameOver){
            try{
                Thread.sleep(100);
                //waiting for game mode...we have a delay so the machine doesn't kill itself checking the while condition
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        } 

        
        //CREATE BOTS HERE
        jarvis = new BotBoi(Color.YELLOW);

        CheckerSquare[] move = new CheckerSquare[2];  //The way our game works, a move is represented by an array containing 2 squares, the start and end respectively
        //GAME LOOP 
        while(true) {
            if(!gameOver) {     //This block runs the game while the game isn't over
                move = nextMove();      //gets a move based on game mode.....also for the bots, checkerSelected should be updated in nextMove for graphics so we can tell whats going on
                while(!MoveValidator.isValidMove(blueTurn, move)){      //check if the move is valid and if not, get a new one
                    move = nextMove();                                  //^^^The AIs should not rely on this check and should validate their own moves with the same method...in other words
                }                                                       //      this check is mainly for the human players
                updateBoard(move);      //updates game board data and GUI graphics
                blueTurn = !blueTurn;   //changing of turns after a move has been made
                if(blueTurn){           //this just displays whose turn it is in a jlabel
                    GamePanel.setTurnLabelText("Blue's turn");
                } else {
                    GamePanel.setTurnLabelText("Yellow's turn");
                }
                gameOver = checkGameOver();     //check to see if anybody has won
            } else{     //when the game is over, continually check to see if game has restarted (on a delay so loop doesn't bang out checks and break)
                try{
                    Thread.sleep(500);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    } //end of main()

    private static CheckerSquare[] nextMove(){  //very important method
        CheckerSquare[] move = null;
        if(gameMode == "pvp") {         //human v human game mode
            return getMoveFromMouse();
        }
        if(gameMode == "pvc") {         //human v pc game mode.....as of now bot is always yellow
            if(!blueTurn){
                move = jarvis.getMove();   //bot makes move here...rest of this method is just animation (if you can call it that)
                move[0].setSelected(true);      //"animation" of bot move starts here
                try{                              //bots selected piece gets highlighted 
                    Thread.sleep(1000);    //the piece stays highlighted for a second for player to see
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                 move[0].setSelected(false);    //removes the highlight (selected status) before making move
                return move;
            } else {
                return getMoveFromMouse();  //player makes move here if it's their turn
            }
        }
        if(gameMode == "cvc") {         //This will work just like pvc but with two bots
            if(!blueTurn){
                //move = yellowBot.getMove();
            } else {
                //move = blueBot.getMove();
            } //Now we animate the move
            move[0].setSelected(true);      //"animation" of bot move starts here
            try{                              //bots selected piece gets highlighted 
                Thread.sleep(1000);    //the piece stays highlighted for a second for player to see
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            move[0].setSelected(false);
            return move;
        }
        return null;  //shouldn't be reached but there's no squiggles so its gonna stay
    }


    
    private static CheckerSquare[] getMoveFromMouse() { //this function kinda scuffed but it works for now...sometimes it seems glitchy in game....if any one has an idea of how to make better lmk
        CheckerSquareMouse.active = true;               //activates the Mouse adapter
        while(CheckerSquareMouse.getMove() == null) {   //continually checks if the player has input a move
            try{
                Thread.sleep(75);
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        CheckerSquare[] move;                           //In order to be able to clear the move from mouse and still be able to use it,
        move = CheckerSquareMouse.getMove();            //   I used a place holder move.
        CheckerSquareMouse.clearMove();
        CheckerSquareMouse.active = false;
        return move;
    }
    
    //This method updates the game board state as well as the board graphics 
    private static void updateBoard(CheckerSquare[] move){
        move[1].toggleChecker(move[0].getCheckerColor());       //this toggles the square where piece is going 
        move[0].toggleChecker(null);                      //this toggles the square where piece came from....jumped pieces are toggled in MoveValidator isValidMove()
    }

    //Call everywhere checkPromote is called (Mark said)....I actually just call it at the end of every game loop iteration cause why not
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
            GamePanel.setTurnLabelText("Yellow Wins!");       //This will do for now but a victory panel would be nice
            return true;
        } else if (activeYellowPiece == 0 && drawMoves <= 40) {
            // Call win panel with yellow as victor
            GamePanel.setTurnLabelText("Blue Wins!");     //^^^^
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