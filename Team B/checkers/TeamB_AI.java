import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class TeamB_AI {

    private CheckerSquare[][] boardState;
    private Color enemyColor;
    private Color teamColor;
    

    final static int KING_VALUE = 4;
    final static int JUMP_VALUE = 3;
    final static int MOVE_VALUE = 2;
    final static int UNSAFE_MOVE_VALUE = 1;

    // constructor for AI
    public TeamB_AI(Color team) {
        if (team != null) {
            if (Color.BLUE.equals(team)) {
                enemyColor = Color.YELLOW;
                teamColor = Color.BLUE;
            }
            else {
                enemyColor = Color.BLUE;
                teamColor = Color.YELLOW;
            }
        } else {
            // Handle the case where team is null (provide default behavior or throw an exception)
            throw new IllegalArgumentException("Team color cannot be null!");
        }
    }

    public CheckerSquare[] getMove() {
        boardState = GamePanel.getSquares();
        if(teamColor.equals(Color.YELLOW)) {
            boardState = flipBoard(boardState);
        }

        // find the best potential move of each piece
        ArrayList<Move> potentialMoves = new ArrayList<>();
        for(CheckerSquare[] boardState1D : boardState) {
            for(CheckerSquare square : boardState1D) {
                if(square.getCheckerColor() == teamColor) {
                    Move m = valuate(square);
                    if(m.getScore() >= 0) {
                        potentialMoves.add(m);
                    }
                }
            }
        }

        // iterate through potential moves and select the highest score
        Move toPerform = potentialMoves.get(0);
        int score = -1;
        for(Move m : potentialMoves) {
            if(m.getScore() > score) {
                toPerform = m;
                score = m.getScore();
                if(potentialMoves.get(0).getScore() == potentialMoves.get(potentialMoves.size() - 1).getScore()) {
                    toPerform = potentialMoves.get(0);
                }
            }
        }

        // print selected move for debugging purposes
        System.out.println(toPerform.getScore());
        System.out.println("Row1: " + toPerform.getMovement()[0].getRow() + 
            " Col1: " + toPerform.getMovement()[0].getCol() + 
            " Row2: " + toPerform.getMovement()[1].getRow() + 
            " Col2: " + toPerform.getMovement()[1].getCol());

        return toPerform.getMovement();
    }

    // check if position is within bounds of board
    private boolean validPos(int r, int c) {
        return r > -1 && r < 8 && c > -1 && c < 8;
    }

    // check if position is an enemy
    private boolean isEnemy(int r, int c) {
        if (validPos(r, c)) {
            Color checkerColor = boardState[r][c].getCheckerColor();
            return checkerColor != null && checkerColor.equals(enemyColor);
        } else {
            return false;
        }
    }

    // check if position is an enemy king
    private boolean isEnemyKing(int r, int c) {
        if (validPos(r, c)) {
            Color checkerColor = boardState[r][c].getCheckerColor();
            return checkerColor != null && isEnemy(r, c) && boardState[r][c].isKing();
        } else {
            return false;
        }
    }

    // check if position is empty
    private boolean isEmpty(int r, int c) {
        if (validPos(r, c)) {
            return Objects.isNull(boardState[r][c].getCheckerColor());
        } else {
            return false;
        }
    }

    // check if the position is a jump
    private boolean isJump(int r1, int c1, int r2, int c2) {
        return isEmpty(r2, c2) && validPos(r2, c2) && isEnemy(r1, c1);
    }

    // check if position is safe. isLeft and isDown are for indicating the direction
    // a piece is moving
    private boolean isSafe(int r, int c, boolean isLeft, boolean isDown) {
        return !(isEnemy(r - 1, c - 1) && (isEmpty(r + 1, c + 1) || (isLeft && !isDown)))
                || (isEnemy(r - 1, c + 1) && (isEmpty(r + 1, c - 1) || (!isLeft && !isDown)))
                || (isEnemyKing(r + 1, c - 1) && (isEmpty(r - 1, c + 1) || (isLeft && isDown)))
                || (isEnemyKing(r + 1, c + 1) && (isEmpty(r - 1, c - 1) || (!isLeft && isDown)));
    }

    // check if position will king a piece
    private boolean isKingSpace(int r, int c) {
        if (validPos(r, c))
            return isEmpty(r, c) && r == 7;
        else
            return false;
    }

    // calculate all possible moves for a piece and return most valuable
    public Move valuate(CheckerSquare piece) {
        if (Objects.isNull(piece))
            throw new NullPointerException("Piece cannot be null!");

        // create an ArrayList that stores the moves the piece can make
        ArrayList<Move> moves = new ArrayList<>();
        Move bestMove = new Move(new CheckerSquare[]{null, null}, -1);

        // store row and collumn of piece, flip if needed
        int r = piece.getRow();
        if(teamColor.equals(Color.YELLOW)) {
            r = 7 - r;
        }
        int c = piece.getCol();

        // check if diagonal up-left is a jump
        if (isJump(r - 1, c - 1, r - 2, c - 2)) {
            moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 2][c - 2]}, JUMP_VALUE));
        }
        // check if diagonal up-right is a jump
        if (isJump(r - 1, c + 1, r - 2, c + 2)) {
            moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 2][c + 2]}, JUMP_VALUE));
        }
        // check if diagonal up-left is empty
        if (isEmpty(r - 1, c - 1)) {
            // check if position results in a king, is safe, or is unsafe
            if (isKingSpace(r - 1, c - 1) && !piece.isKing()) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c - 1]}, KING_VALUE));
            } else if (isSafe(r - 1, c - 1, true, false)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c - 1]}, MOVE_VALUE));
            } else {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c - 1]}, UNSAFE_MOVE_VALUE));
            }
        }
        // check if diagonal up-right is empty
        if (isEmpty(r - 1, c + 1)) {
            // check if position results in a king, is safe, or is unsafe
            if (isKingSpace(r - 1, c + 1) && !piece.isKing()) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c + 1]}, KING_VALUE));
            } else if (isSafe(r - 1, c + 1, false, false)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c + 1]}, MOVE_VALUE));
            } else {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c + 1]}, UNSAFE_MOVE_VALUE));
            }
        }
        // evaluate king specific moves
        if (piece.isKing()) {
            // check if diagonal down-left is a jump
            if (isJump(r + 1, c - 1, r + 2, c - 2)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 2][c - 2]}, JUMP_VALUE));
            }
            // check if diagonal down-right is a jump
            if (isJump(r + 1, c + 1, r + 2, c + 2)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 2][c + 2]}, JUMP_VALUE));
            }
            // check if diagonal down-left is empty
            if (isEmpty(r + 1, c - 1)) {
                // check if position is safe
                if (isSafe(r + 1, c - 1, true, true)) {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c - 1]}, MOVE_VALUE));
                } else {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c - 1]},
                            UNSAFE_MOVE_VALUE));
                }
            }
            // check if diagonal down-right is empty
            if (isEmpty(r + 1, c + 1)) {
                // check if position is safe
                if (isSafe(r + 1, c + 1, false, true)) {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c + 1]}, MOVE_VALUE));
                } else {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c + 1]},
                            UNSAFE_MOVE_VALUE));
                }
            }
        }

        // iterate through potential moves and select the highest score
        int s = -1;
        for (Move m : moves) {
            if (m.getScore() > s) {
                s = m.getScore();
                bestMove = m;
            }
        }

        return bestMove;
    }

    // vertically flips the supplied CheckerSquare[][] and returns it
    private CheckerSquare[][] flipBoard(CheckerSquare[][] board) {
        int length = board.length;
        CheckerSquare[][] flippedBoard = new CheckerSquare[length][length];

        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                flippedBoard[(length - 1) - r][c] = board[r][c];
            }
        }

        return flippedBoard;
    }

}