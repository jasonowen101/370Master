import java.awt.Color;

//import checkers.CheckerSquare;

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
