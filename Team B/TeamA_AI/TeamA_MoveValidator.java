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

        try{
            Piece start = board[startCord[0]][startCord[1]];
            Piece end = board[endCord[0]][endCord[1]];
            if(!start.getPlayer().equals(playerColor)) return false; // If wrong team [color]

            else if (((endCord[0] < 0) || (endCord[0] > 7)) || ((endCord[1] < 0) || (endCord[1] > 7))) return false; // If move is out of bounds

            else if((abs(endCord[0] - startCord[0]) != abs(endCord[1] - startCord[1])) || abs(endCord[0] - startCord[0]) > 2) return false; // If move isn't diagonal

            else{ // If Move is Diagonal, Within Bounds, and the correct team [color]
                if(start.isKing()){ // If moved piece is a King
                    if(abs(endCord[0] - startCord[0]) == 2){ // If move is a Jump
                        Piece jumped = board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)];
                        if (jumped != null) if (jumped.getPlayer().equals(Board.oppositeColor(playerColor))){
                            return true;
                        }
                        else return false;
                    } // TBC: Add King Move validation [Move, not jump]
                }else{ // Moved Piece is a Peon [Not a KING]
                    if(start.getPlayer().equals(Piece.TEAM2)){ // If BLUE team [TEAM 2]
                        if(endCord[1] - startCord[1] > 0){ // If Move is not FORWARD [Blue should go UP, so deltaY is negative]
                            return false;
                        } else { // If BLUE move IS FORWARD
                            if(abs(endCord[0] - startCord[0]) == 2){ // If move is JUMP
                                Piece jumped = board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)];
                                if (jumped != null) if (jumped.getPlayer().equals(Board.oppositeColor(playerColor))){ // If jumping over opposing [YELLOW / TEAM 1] piece
                                return true;
                            }
                            else return false; // BLUE can't jump BLUE pieces
                            }
                        }
                    }else{ // If YELLOW Team [TEAM 1]
                        if(endCord[1] - startCord[1] < 0){ // If move is not FORWARD [Yellow should go down, so deltaY is positive]
                            return false;
                        } else { // If YELLOW move is forward
                            if(abs(endCord[0] - startCord[0]) == 2){ // If move is Jump
                                Piece jumped = board[startCord[0] + ((endCord[0] - startCord[0])/2)][startCord[1] + ((endCord[1] - startCord[1])/2)];
                                if (jumped != null) if (jumped.getPlayer().equals(Board.oppositeColor(playerColor))){ // If jumping over opposing [BLUE / TEAM 2] piece
                                return true;
                            }
                            else return false; // YELLOW can't jump YELLOW pieces
                            }
                        }
                    }
                }
            }
            return false;}
            catch (ArrayIndexOutOfBoundsException e){
                return false;
            }


    }
    private static void checkPromote(Piece start, Piece end, byte[] position){
        end.setKing(start.isKing());
        if(start.getPlayer() == Piece.TEAM2 && position[1] == 0) start.promote();
        else if(start.getPlayer() == Piece.TEAM1 && position[1] == 7) start.promote();
    }
}
