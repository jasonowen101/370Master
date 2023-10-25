package checkers;
import java.awt.Color;
//THIS WAS MADE JUST TO SEE IF THINGS WORK, NOT FOR USE BY TEAMS UNLESS YOU WANT TO,
// BUT THE AI CLASS OF EACH TEAM NEEDS A getMove() METHOD THAT WORKS LIKE THE ONE IN THIS CLASS
public class BotBoi {
    //aight, sooo this class is going to take in game data and select the best move (prolly not the best).
    //the game should create an object of BotBoi which will be a robot player. 
    private Color team;         //right now teams are colors but this can be changed
    private CheckerSquare[][] pieces = GamePanel.getSquares();      //this assigns a name to the squares array for shorter name in this class

    //construct a bot and assign it a team
    public BotBoi(Color team) {
        this.team = team;
    }

    //The game should call bot.getMove(), and the bot should return its move as an array of 2 CheckerSquares
    public CheckerSquare[] getMove() {
        CheckerSquare[] move = new CheckerSquare[] {pieces[2][1], pieces[3][0]};  
        //^^^ initialized because i dont like red squiggles ^^^

        //function call to the algorithm that actually determines the best move (or any move as long as it works lmao) should go here
        //ALGORITHM SHOULD RETURN MOVE AS AN ARRAY OF CHECKERSQUARES (size of 2)
        //move = determineBestMove()     (or whatever the function is called)

        return move;
    }
}
