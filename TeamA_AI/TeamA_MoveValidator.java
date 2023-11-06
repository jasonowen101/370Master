import java.awt.Color;

public class TeamA_MoveValidator {

    private Piece[][] board;

    public TeamA_MoveValidator(Piece[][] board){
        this.board = board;
    }

    public Piece[][] getBoard(){
        return this.board;
    }

    private static byte abs(int num){
        return (byte) Math.abs(num);
    }

    public static boolean isValidMove(Piece[][] board, Color playerColor, byte[] startCord, byte[] endCord){

        Piece start = board[startCord[0]][startCord[1]];
        Piece end = board[endCord[0]][endCord[1]];
        if(!start.getPlayer().equals(playerColor)) return false;

        else if (((endCord[0] < 0) || (endCord[0] > 7)) || ((endCord[1] < 0) || (endCord[1] > 7))) return false;

        else if((abs(endCord[0] - startCord[0]) != abs(endCord[1] - startCord[1])) || abs(endCord[0] - startCord[0]) > 2) return false;

        else{
            if(start.isKing()){
                if(abs(endCord[0] - startCord[0]) == 2){
                    Piece jumped = board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)];
                    if (jumped != null) if (jumped.getPlayer().equals(Board.oppositeColor(playerColor))){
                        board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)] = null;
                        checkPromote(start, end, endCord);
                        return true;
                    }
                    else return false;
                }
            }else{
                if(start.getPlayer().equals(Piece.TEAM2)){
                    if(endCord[1] - startCord[1] > 0){
                        return false;
                    } else {
                        if(abs(endCord[0] - startCord[0]) == 2){
                            Piece jumped = board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)];
                            if (jumped != null) if (jumped.getPlayer().equals(Board.oppositeColor(playerColor))){
                            board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)] = null;
                            checkPromote(start, end, endCord);
                            return true;
                        }
                        else return false;
                        }
                    }
                }else{
                    if(endCord[1] - startCord[1] < 0){
                        return false;
                    } else {
                        if(abs(endCord[0] - startCord[0]) == 2){
                            Piece jumped = board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)];
                            if (jumped != null) if (jumped.getPlayer().equals(Board.oppositeColor(playerColor))){
                            board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)] = null;
                            checkPromote(start, end, endCord);
                            return true;
                        }
                        else return false;
                        }
                    }
                }
            }
        }


    }
    private static void checkPromote(Piece start, Piece end, byte[] position){
        end.setKing(start.isKing());
        if(start.getPlayer() == Piece.TEAM2 && position[1] == 0) start.promote();
        else if(start.getPlayer() == Piece.TEAM1 && position[1] == 7) start.promote();
    }
}
