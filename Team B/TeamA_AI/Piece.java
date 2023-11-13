import java.awt.Color;

<<<<<<< HEAD
=======
//import checkers.CheckerSquare;

>>>>>>> d950b9bdb79931a46a581a5732d15a6d2b8f2c35
public class Piece {

    public static Color TEAM1 = CheckerSquare.TEAM1;
    public static Color TEAM2 = CheckerSquare.TEAM2;

    private Color player;
    private boolean king;

    public Piece(Color player, boolean king){
        this.player = player;
        this.king = king;
    }

    public Piece(Color player){
        this.player = player;
        this.king = false;
    }

    public boolean isKing() {
        return king;
    }

    public Color getPlayer() {
        return player;
    }

    public void setKing(boolean king) {
        this.king = king;
    }

    public void promote(){
        this.king = true;
    }

    public Color getOpposite(){
        return Board.oppositeColor(player);
    }
}
