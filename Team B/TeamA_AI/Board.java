import java.util.ArrayList;
import java.util.HashMap;

import java.awt.Color;

public class Board {
    private final int PIECE_VALUE = 1;
    private final int KING_VALUE = 5;
    private Color turn;

    Piece[][] state;

    public HashMap<Color, Byte> pieceCount;
    public HashMap<Color, Byte> kingCount;

    private byte[] fromPos;
    private byte[] toPos;

    public Board(){
        state = new Piece[8][8];
    }

    // Generic Getter method for opposite team's color
    // Indifferent to underlying color system
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
                if(boardState[y][x].getCheckerColor()!=null){
                    board.state[x][y] = new Piece(boardState[y][x].getCheckerColor());
                    if(boardState[y][x].getCheckerColor().equals(AIColor)){
                        if(boardState[y][x].isKing()) playerKng++;
                        playerCnt++;
                    }
                    else{
                        if(boardState[y][x].isKing()) enemyKng++;
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

    // Basic Score calculation [# of pieces for player]
    private int score(Color player){
        int value = this.pieceCount.get(player) * PIECE_VALUE + this.kingCount.get(player) * KING_VALUE;
        System.out.print(value);
        System.out.print(",");
        return value;
    }

    // Builds valid move logic tree
    public ArrayList<Board> getSuccessors(){
        ArrayList<Board> successors = getSuccessors(true);
        successors.addAll(getSuccessors(false));

        return successors;
    }

    // Helper method for getSuccessors()
    // Splits logic to handle Moves and Jumps
    public ArrayList<Board> getSuccessors(boolean jump){
        ArrayList<Board> jumpResult = new ArrayList<>();

        for(byte y = 0; y < this.state.length; y++){
            for(byte x = 0; x < this.state[y].length; x++){
                if(this.state[x][y] != null) if (this.state[x][y].getPlayer() == this.turn){
                    jumpResult.addAll(getSuccessors(this.state[x][y], new byte[]{x,y} , jump));
                }
            }
        }
        return jumpResult;
    }

    // Helper method for getSuccessors()
    // ...
    private ArrayList<Board> getSuccessors(Piece piece, byte[] position, boolean jump){
        if(piece.getPlayer() != this.turn) return new ArrayList<Board>();
        if(jump) return jumpSuccessors(piece, position);
        else return nonJumpSuccessors(piece, position);
    }

    /*
     * future of regular move
     */
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

    /*
     * futures with jump action
     */
    private ArrayList<Board> jumpSuccessors(Piece piece, byte[] position){
        ArrayList<Board> result = new ArrayList<>();

        for(byte dx : new byte[]{-2,2}){
            for(byte dy : new byte[]{-2,2}){
                byte[] endCord = new byte[]{(byte) (position[0] + dx), (byte) (position[1] + dy)};
                if(TeamA_MoveValidator.isValidMove(state, turn, position, endCord)){
                    result.add(createNewState(position, endCord, piece, true));
                }
            }
        }
        return result;
    }

    /*
     * creates the next instance of valid moves to calculate
     */
    private Board createNewState(byte[] oldPos, byte[] newPos, Piece piece, boolean jumped){
        try{
            Board result = this.deepCopy();

            result.pieceCount = new HashMap<>(pieceCount);
            result.kingCount = new HashMap<>(kingCount);

            if(rankUp(piece.getPlayer(), newPos)){
                piece = new Piece(turn, true);

                result.kingCount.replace(piece.getPlayer(), (byte) (result.kingCount.get(piece.getPlayer()) + 1));
            }
            result.state[oldPos[0]][oldPos[1]] = null;
            result.state[newPos[0]][newPos[1]] = piece;

            result.fromPos = oldPos;
            result.toPos = newPos;

            result.turn = piece.getOpposite();

            if (jumped){
                byte[] middlePos = new byte[] {(byte) (oldPos[0] + ((newPos[0] - oldPos[0])/2)), (byte) (oldPos[1] + ((newPos[1] - oldPos[1])/2))};
                if (result.state[middlePos[0]][middlePos[1]].isKing()){
                    result.kingCount.replace(result.turn, (byte) (result.kingCount.get(result.turn) - 1));
                }
                result.state[middlePos[0]][middlePos[1]] = null;
                result.pieceCount.replace(result.turn, (byte) (result.pieceCount.get(result.turn) - 1));
            }
            return result;
        }
        catch (NullPointerException npex){
            return this;
        }

    }


    /*
     * returns true if a piece is at the spot to rank up
     */
    private boolean rankUp(Color turn, byte[] position){
        if(turn.equals(Piece.TEAM2) && position[1] == 0) return true;
        else if(turn.equals(Piece.TEAM1) && position[1] == 7) return true;
        return false;
    }

    // Getter method for piece start position
    public byte[] getFromPos() {
        return new byte[] {fromPos[1], fromPos[0]};
    }

    // Getter method for # of input team's king pieces
    public byte getKingCount(Color turn) {
        return this.kingCount.get(turn);
    }

    // Getter method for # of input team's pieces
    public byte getPieceCount(Color turn) {
        return this.pieceCount.get(turn);
    }

    // Getter method for current boardState
    public Piece[][] getState() {
        return state;
    }

    // Getter method for piece end position
    public byte[] getToPos() {
        return new byte[] {toPos[1], toPos[0]};
    }

    // Getter method for current turn
    public Color getTurn() {
        return turn;
    }

    // Basic GameOver check [Has a team lost all pieces?]
    public boolean isGameOver(){
        return ((pieceCount.get(Piece.TEAM1) == 0) || (pieceCount.get(Piece.TEAM2) == 0));
    }



}
