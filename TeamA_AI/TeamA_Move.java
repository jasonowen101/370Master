
import checkers.CheckerSquare;
import checkers.MoveValidator;

public class TeamA_Move {

    public static boolean validMove(CheckerSquare start, CheckerSquare end){
        return MoveValidator.isValidMove(start.getCheckerColor(), new CheckerSquare[]{start, end});
    }
}
