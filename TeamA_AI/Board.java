import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import checkers.CheckerSquare;

import java.awt.Color;

public class Board {
    private Color turn;

    Piece[][] state;

    public HashMap<Color, Byte> pieceCount;
    public HashMap<Color, Byte> kingCount;

    public Board(){
        state = new Piece[8][8];
    }

    public static Color oppositeColor(Color color){
        return ((color == CheckerSquare.TEAM1) ? CheckerSquare.TEAM2 : CheckerSquare.TEAM1);
    }

    public static Board InitialState(Color AIColor, CheckerSquare[][] boardState){
        Board board = new Board();
        board.turn = AIColor;

        Color enemyColor = oppositeColor(AIColor);

        byte playerCnt = 0;
        byte enemyCnt = 0;

        byte playerKng = 0;
        byte enemyKng = 0;

        for (byte y = 0; y < boardState.length; y++){
            for(byte x = 0; x < boardState[y].length; x++){
                if(!boardState[x][y].getCheckerColor().equals(null)){
                    board.state[x][y] = new Piece(boardState[x][y].getCheckerColor());
                    if(boardState[x][y].getCheckerColor().equals(AIColor)){
                        if(boardState[x][y].isKing()) playerKng++;
                        playerCnt++;
                    }
                    else{
                        if(boardState[x][y].isKing()) enemyKng++;
                        enemyCnt++;
                    }
                }
            }
        }

        board.pieceCount = new HashMap<>();
        board.pieceCount.put(AIColor, playerCnt);
        board.pieceCount.put(enemyColor, enemyCnt);

        board.kingCount = new HashMap<>();
        board.pieceCount.put(AIColor, playerKng);
        board.pieceCount.put(enemyColor, enemyKng);

        return board;
    }

    private static Board copy(Board board, Color AIColor, Piece[][] boardState){
        board = new Board();
        board.turn = AIColor;

        Color enemyColor = oppositeColor(AIColor);

        byte playerCnt = 0;
        byte enemyCnt = 0;

        byte playerKng = 0;
        byte enemyKng = 0;

        for (byte y = 0; y < boardState.length; y++){
            for(byte x = 0; x < boardState[y].length; x++){
                if(boardState[x][y] != null){
                    board.state[x][y] = new Piece(boardState[x][y].getPlayer());
                    if(boardState[x][y].getPlayer().equals(AIColor)){
                        if(boardState[x][y].isKing()) playerKng++;
                        playerCnt++;
                    }
                    else{
                        if(boardState[x][y].isKing()) enemyKng++;
                        enemyCnt++;
                    }
                }
            }
        }

        board.pieceCount = new HashMap<>();
        board.pieceCount.put(AIColor, playerCnt);
        board.pieceCount.put(enemyColor, enemyCnt);

        board.kingCount = new HashMap<>();
        board.pieceCount.put(AIColor, playerKng);
        board.pieceCount.put(enemyColor, enemyKng);

        return board;
    }

    private Board deepCopy(){
        Board board = new Board();
        return copy(board, this.turn, this.state);
    }

    public int computeHeuristic(Color player, boolean heuristicOption){
        if(this.pieceCount.get(oppositeColor(player)) == 0){
            return Integer.MAX_VALUE;
        }
        else if(this.pieceCount.get(player) == 0){
            return Integer.MIN_VALUE;
        }
        else{
            if(heuristicOption){
                return (score(player) - score(oppositeColor(player)));
            }
            return (score(player) / score(oppositeColor(player)));
        }
    }

    private int score(Color player){
        return this.pieceCount.get(player) + this.kingCount.get(player);
    }


    public Color getTurn() {
        return turn;
    }

    public boolean isGameOver(){
        return ((pieceCount.get(Piece.TEAM1) == 0) || (pieceCount.get(Piece.TEAM2) == 0));
    }

    public ArrayList<Board> getSuccessors(){
        ArrayList<Board> successors = getSuccessors(true);
        successors.addAll(getSuccessors(false));

        return successors;
    }

    public ArrayList<Board> getSuccessors(boolean jump){
        ArrayList<Board> jumpResult = new ArrayList<>();

        for(byte x = 0; x < this.state.length; x++){
            for(byte y = 0; y < this.state[x].length; y++){
                if(this.state[x][y] != null) if (this.state[x][y].getPlayer() == this.turn){
                    jumpResult.addAll(getSuccessors(this.state[x][y], new byte[]{x,y} , jump));
                }
            }
        }
        return jumpResult;
    }

    private ArrayList<Board> getSuccessors(Piece piece, byte[] position, boolean jump){
        if(piece.getPlayer() != this.turn) return new ArrayList<Board>();
        if(jump) return jumpSuccessors(piece, position);
        else return nonJumpSuccessors(piece, position);
    }

    private ArrayList<Board> nonJumpSuccessors(Piece piece, byte[] position){
        ArrayList<Board> result = new ArrayList<>();

        for(byte dx : new byte[]{-1,1}){
            for(byte dy : new byte[]{-1,1}){
                byte[] endCord = new byte[]{(byte) (position[0] + dx), (byte) (position[1] + dy)};
                if(TeamA_MoveValidator.isValidMove(state, turn, position, endCord)){
                    result.add(createNewState(position, endCord, piece, false));
                }
            }
        }
        return result;
    }



}
