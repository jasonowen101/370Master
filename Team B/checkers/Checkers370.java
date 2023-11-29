import java.awt.Color;

public class Checkers370 {
    public static String gameMode = null;   // 3 possible values, "pvp" (person vs person) "pvc" (person vs computer) "cvc" (computer vs computer)
    public static boolean gameOver = true;  //game is over when gameOver = true
    public static int drawMoves = 0;        //for check end
    public static boolean blueTurn;         //its blue's turn when blueTurn = true
     public static final int BOT_MOVE_DELAY = 1000;    //ms value of how long the bot move animation lasts
    //Declare bots here
    public static TeamB_AI teamB;
    public static TeamA_AI teamA;
    //made create bots a method that is called in menu panel when game mode is selected, this way bots get recreated at start of new game
    public static void createBots(String team) {
        //CREATE BOTS HERE
        if(team == "B") {
            teamB = new TeamB_AI(Color.YELLOW);
            teamA = null;
        } else if(team == "A") {
            teamA = new TeamA_AI((byte) 10, CheckerSquare.TEAM1);
            teamB = null;
        } else {
            teamA = new TeamA_AI((byte) 10, CheckerSquare.TEAM2);
            teamB = new TeamB_AI(Color.YELLOW);
        }
    }
    public static void main(String[] args){
        new Ctegame(); //Created an instance of Ctegame, which constructs the GUI

        CheckerSquare[] move = new CheckerSquare[2];  //The way our game works, a move is represented by an array containing 2 squares, the start and end respectively
        //GAME LOOP (runs continually until game exited)
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
                gameOver = checkGameOver();     //check to see if anybody has won...checkGameOver also will display endscreen on game end
            } else{ //when the game is over, continually check to see if game has restarted (on a delay so loop doesn't bang out checks and break)
                    //this effectively transforms the while loop into a timer that only has a delay when needed
                try{
                    Thread.sleep(500);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        } //end of game loop
    } //end of main()

    private static CheckerSquare[] nextMove(){  //very important method that gets the move from player or bot
        CheckerSquare[] move = null;
        if(gameMode == "pvp") {         //human v human game mode
            return getMoveFromMouse();
        }
        if(gameMode == "pvc") {         //human v pc game mode.....as of now bot is always yellow
            if(!blueTurn){
                if(teamA != null)
                    move = teamA.getMove(GamePanel.getSquares());   //bot makes move here...rest of this method is just animation (if you can call it that)
                if(teamB != null)
                    move = teamB.getMove();
                move[0].setSelected(true);      //"animation" of bot move starts here
                try{                              //bots selected piece gets highlighted
                    Thread.sleep(BOT_MOVE_DELAY);    //the piece stays highlighted for a second for player to see
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
            if(blueTurn){
                move = teamA.getMove(GamePanel.getSquares());
            } else {
                move = teamB.getMove();
            }
            //Now we animate the move
            move[0].setSelected(true);      //"animation" of bot move starts here
            try{                              //bots selected piece gets highlighted
                Thread.sleep(BOT_MOVE_DELAY);    //the piece stays highlighted for a second for player to see
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            move[0].setSelected(false);
            return move;
        }
        return null;  //shouldn't be reached but there's no squiggles so its gonna stay
    }

    //getMoveFromMouse() does exactly what it sounds like
    //this function kinda scuffed but it works...sometimes it seems glitchy in game(i realized this is because my high dpi setting...mouse adapter doesn't like to take clicks when mouse moving)
    private static CheckerSquare[] getMoveFromMouse() {
        CheckerSquareMouse.clearMove();
        CheckerSquareMouse.active = true;               //activates the Mouse adapter
        while(CheckerSquareMouse.getMove() == null) {   //continually checks if the player has input a potentially valid move
            try{    //slight delay on checks so nothing breaks
                Thread.sleep(75);
            } catch(InterruptedException exception) {
                exception.printStackTrace();
            }
        }
        CheckerSquareMouse.active = false;      //deactivates the Mouse (so no input to the board when not wanted)
        return CheckerSquareMouse.getMove();
    }

    //This method updates the game board state as well as the board graphics
    private static void updateBoard(CheckerSquare[] move){
        move[1].toggleChecker(move[0].getCheckerColor());       //this toggles the square where piece is going
        move[0].toggleChecker(null);                      //this toggles the square where piece came from....jumped pieces are toggled in MoveValidator isValidMove()
    }

    //Call everywhere checkPromote is called (Mark said)....I actually just call it at the end of every game loop iteration cause why not
    public static boolean checkGameOver(){
        boolean gameOverStatus = false;
        int activeBluePiece = 0;
        int activeYellowPiece = 0;

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if(((GamePanel.getSquares())[row][col]).getCheckerColor() == Color.BLUE){
                    activeBluePiece = activeBluePiece + 1;
                    if(blueTurn){
                        if(!MoveValidator.checkStaleMateMoves(blueTurn, (GamePanel.getSquares())[row][col])){
                            yellowWin();
                            gameOverStatus = true;
                        }
                    }
                }
                if(((GamePanel.getSquares())[row][col]).getCheckerColor() == Color.YELLOW){
                    activeYellowPiece = activeYellowPiece + 1;
                    if(!blueTurn){
                        if(!MoveValidator.checkStaleMateMoves(blueTurn, (GamePanel.getSquares())[row][col])){
                            blueWin();
                            gameOverStatus = true;
                        }
                    }
                }
            }
        }

        if (activeBluePiece == 0 && drawMoves <= 40){
            GamePanel.setTurnLabelText("Yellow Wins!");
            //set endscreen winner and show
            yellowWin();
            gameOverStatus = true;
        } else if (activeYellowPiece == 0 && drawMoves <= 40) {
            GamePanel.setTurnLabelText("Blue Wins!");
            //set endscreen winner and show
            blueWin();
            gameOverStatus = true;
        } else if (drawMoves > 40){
            GamePanel.setTurnLabelText("It's a draw!");
            //set endscreen winner and show
            Ctegame.endScreen.setWinner("draw");
            Ctegame.cl.show(Ctegame.cards, "EndScreen");
            gameOverStatus = true;
        }
        return gameOverStatus;
    }

    /*
    //Checks if the nextMove method will return a move or if it'll be null Then based on the current turn will
    //return true to set the status of the game to game over and call the end screens
    public static boolean checkStalemate(boolean avalibleMoves) {
        if (!avalibleMoves){
            if (blueTurn) {
                yellowWin();
                return true;
            } else {
                blueWin();
                return true;
            }
        } else {
            return false;
        }
    }
    */

    //Call for yellow win
    public static void yellowWin(){
        Ctegame.endScreen.setWinner("yellow");
        Ctegame.cl.show(Ctegame.cards, "EndScreen");
    }

    //Call for blue win
    public static void blueWin(){
        Ctegame.endScreen.setWinner("blue");
        Ctegame.cl.show(Ctegame.cards, "EndScreen");
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
