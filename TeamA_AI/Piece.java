import java.awt.Color;

import checkers.CheckerSquare;

public class Piece {
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

    public Color getOpposite(){
        return ((player == CheckerSquare.TEAM1) ? CheckerSquare.TEAM2 : CheckerSquare.TEAM1);
    }
}
