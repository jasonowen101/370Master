import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class TeamB_AI {

    private CheckerSquare[][] boardState;
    private Color enemyColor;
    private Color teamColor;
    

    final static int KING_VALUE = 5;
    final static int JUMP_VALUE = 4;
    final static int UNSAFE_JUMP_VALUE = 3;
    final static int MOVE_VALUE = 2;
    final static int UNSAFE_MOVE_VALUE = 1;
    
    final static int UNSAFE_TO_SAFE = 1;

    /**
     * Constructor for the AI.
     * @param team The color of the team that the AI is on.
     */
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
            // Handle the case where team is null (provide default behavior or throw an exception).
            throw new IllegalArgumentException("Team color cannot be null!");
        }
    }

    /**
     * Calculates the move with the highest score to perform.
     * @return Length of 2 CheckerSquare array with the original and new positions a piece should move to.
     */
    public CheckerSquare[] getMove() {
        boardState = GamePanel.getSquares();
        if(teamColor.equals(Color.YELLOW)) {
            boardState = flipBoard(boardState);
        }

        // Find the best potential move of each piece.
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

        // Iterate through potential moves and select the ones with the highest score.
        Move toPerform = potentialMoves.get(0);
        ArrayList<Move> movePool = new ArrayList<>();
        movePool.add(toPerform);
        int score = 0;
        for(Move m : potentialMoves) {
            if(m.getScore() >= score) {
                if(m.getScore() > score) {
                    score = m.getScore();
                    movePool.clear();
                }
                movePool.add(m);
            }
        }

        // Assign a random move from the highest scoring moves to toPerform.
        toPerform = movePool.get((int) (Math.random() * movePool.size()));

        // Print selected move for debugging purposes.
        System.out.println(toPerform.getScore());
        System.out.println("Row1: " + toPerform.getMovement()[0].getRow() + 
            " Col1: " + toPerform.getMovement()[0].getCol() + 
            " Row2: " + toPerform.getMovement()[1].getRow() + 
            " Col2: " + toPerform.getMovement()[1].getCol());

        return toPerform.getMovement();
    }

    /**
     * Check if the position is within the bounds of the board.
     * @param r The row of the position to be checked.
     * @param c The column of the position to be checked.
     * @return Whether or not the position is within bounds.
     */
    private boolean validPos(int r, int c) {
        return r > -1 && r < 8 && c > -1 && c < 8;
    }

    /**
     * Check if the position is an enemy.
     * @param r The row of the position to be checked.
     * @param c The column of the position to be checked.
     * @return Whether or not the position is an enemy.
     */
    private boolean isEnemy(int r, int c) {
        if (validPos(r, c)) {
            Color checkerColor = boardState[r][c].getCheckerColor();
            return checkerColor != null && checkerColor.equals(enemyColor);
        } else {
            return false;
        }
    }

    /**
     * Check if the position is an enemy king.
     * @param r The row of the position to be checked.
     * @param c The column of the position to be checked.
     * @return Whether or not the position is an enemy king.
     */
    private boolean isEnemyKing(int r, int c) {
        if (validPos(r, c)) {
            Color checkerColor = boardState[r][c].getCheckerColor();
            return checkerColor != null && isEnemy(r, c) && boardState[r][c].isKing();
        } else {
            return false;
        }
    }

    /**
     * Check if the position is empty.
     * @param r The row of the position to be checked.
     * @param c The column of the position to be checked.
     * @return Whether or not the position is empty.
     */
    private boolean isEmpty(int r, int c) {
        if (validPos(r, c)) {
            return Objects.isNull(boardState[r][c].getCheckerColor());
        } else {
            return false;
        }
    }

    /**
     * Check if the position is a valid jump.
     * @param r1 The row of the piece to be jumped.
     * @param c1 The column of the piece to be jumped.
     * @param r2 The row of the destination position.
     * @param c2 The column of the destination postion.
     * @return Whether or not the positions could be a jump.
     */
    private boolean isJump(int r1, int c1, int r2, int c2) {
        return isEmpty(r2, c2) && validPos(r2, c2) && isEnemy(r1, c1);
    }

    /**
     * Checks whether or not the specified postion is safe.
     * @param r The row of the position to be checked.
     * @param c The column of the position to be checked.
     * @param originR The row that will become empty following the action.
     * @param originC The column that will become empty following the action.
     * @return Whether or not the new position is safe.
     */
    private boolean isSafe(int r, int c, int originR, int originC) {
        return !((isEnemy(r - 1, c - 1) && (isEmpty(r + 1, c + 1) || ((r + 1 == originR) && (c + 1 == originC))))
                || (isEnemy(r - 1, c + 1) && (isEmpty(r + 1, c - 1) || ((r + 1 == originR) && (c - 1 == originC))))
                || (isEnemyKing(r + 1, c - 1) && (isEmpty(r - 1, c + 1) || ((r - 1 == originR) && (c + 1 == originC))))
                || (isEnemyKing(r + 1, c + 1) && (isEmpty(r - 1, c - 1) || ((r - 1 == originR) && (c - 1 == originC)))));
    }

    /**
     * Check if the position would result in becoming a king.
     * @param r The row of the position to be checked.
     * @param c The column of the position to be checked.
     * @return Whether or not the position would result in becoming a king.
     */
    private boolean isKingSpace(int r, int c) {
        if (validPos(r, c))
            return isEmpty(r, c) && r == 7;
        else
            return false;
    }

    /**
     * Evaluates all possible moves a piece can make and assigns a score.
     * It then picks the move that has the highest score and returns it.
     * @param piece The piece to be evaluated.
     * @return The move with the highest score.
     */
    public Move valuate(CheckerSquare piece) {
        if (Objects.isNull(piece))
            throw new NullPointerException("Piece cannot be null!");

        // Create an ArrayList that stores the moves the piece can make.
        ArrayList<Move> moves = new ArrayList<>();
        Move bestMove = new Move(new CheckerSquare[]{null, null}, -1);

        // Store row and collumn of piece, flip if needed.
        int r = piece.getRow();
        if(teamColor.equals(Color.YELLOW)) {
            r = 7 - r;
        }
        int c = piece.getCol();

        // Adds a bonus if the piece is moving from an unsafe position.
        int safetyBonus = 0;
        if(!isSafe(r, c, r, c)) {
            safetyBonus = UNSAFE_TO_SAFE;
        }
        // Check if diagonal up-left is a jump.
        if (isJump(r - 1, c - 1, r - 2, c - 2)) {
            // Check if the end position is safe.
            if(isSafe(r - 2, c - 2, r - 1, c - 1)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 2][c - 2]}, JUMP_VALUE + safetyBonus));
            } else {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 2][c - 2]}, UNSAFE_JUMP_VALUE));
            }
        }
        // Check if diagonal up-right is a jump.
        if (isJump(r - 1, c + 1, r - 2, c + 2)) {
            // Check if the end position is safe.
            if(isSafe(r - 2, c + 2, r - 1, c + 1)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 2][c + 2]}, JUMP_VALUE + safetyBonus));
            } else {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 2][c + 2]}, UNSAFE_JUMP_VALUE));
            }
        }
        // Check if diagonal up-left is empty.
        if (isEmpty(r - 1, c - 1)) {
            // Check if position results in a king, is safe, or is unsafe.
            if (isKingSpace(r - 1, c - 1) && !piece.isKing()) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c - 1]}, KING_VALUE + safetyBonus));
            } else if (isSafe(r - 1, c - 1, r, c)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c - 1]}, MOVE_VALUE + safetyBonus));
            } else {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c - 1]}, UNSAFE_MOVE_VALUE));
            }
        }
        // Check if diagonal up-right is empty.
        if (isEmpty(r - 1, c + 1)) {
            // Check if position results in a king, is safe, or is unsafe.
            if (isKingSpace(r - 1, c + 1) && !piece.isKing()) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c + 1]}, KING_VALUE + safetyBonus));
            } else if (isSafe(r - 1, c + 1, r, c)) {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c + 1]}, MOVE_VALUE + safetyBonus));
            } else {
                moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r - 1][c + 1]}, UNSAFE_MOVE_VALUE));
            }
        }
        // Evaluate king specific moves.
        if (piece.isKing()) {
            // Check if diagonal down-left is a jump.
            if (isJump(r + 1, c - 1, r + 2, c - 2)) {
                // Check if the end position is safe.
                if(isSafe(r + 2, c - 2, r + 1, c - 1)) {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 2][c - 2]}, JUMP_VALUE + safetyBonus));
                } else {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 2][c - 2]}, UNSAFE_JUMP_VALUE));
                }
            }
            // Check if diagonal down-right is a jump.
            if (isJump(r + 1, c + 1, r + 2, c + 2)) {
                // Check if the end position is safe.
                if(isSafe(r + 2, c + 2, r + 1, c + 1)) {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 2][c + 2]}, JUMP_VALUE + safetyBonus));
                } else {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 2][c + 2]}, UNSAFE_JUMP_VALUE));
                }
            }
            // Check if diagonal down-left is empty.
            if (isEmpty(r + 1, c - 1)) {
                // Check if position is safe.
                if (isSafe(r + 1, c - 1, r, c)) {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c - 1]}, MOVE_VALUE + safetyBonus));
                } else {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c - 1]}, UNSAFE_MOVE_VALUE));
                }
            }
            // Check if diagonal down-right is empty.
            if (isEmpty(r + 1, c + 1)) {
                // Check if position is safe.
                if (isSafe(r + 1, c + 1, r, c)) {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c + 1]}, MOVE_VALUE + safetyBonus));
                } else {
                    moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r + 1][c + 1]}, UNSAFE_MOVE_VALUE));
                }
            }
        }

        // Iterate through potential moves and select the ones with the highest score.
        ArrayList<Move> movePool = new ArrayList<>();
        movePool.add(bestMove);
        int s = 0;
        for (Move m : moves) {
            if (m.getScore() >= s) {
                if(m.getScore() > s) {
                    s = m.getScore();
                    movePool.clear();
                }
                movePool.add(m);
            }
        }

        // Return a random move of the ones with a high score.
        return movePool.get((int) (Math.random() * movePool.size()));
    }

    /**
     * Vertically flips the supplied 2D array of CheckerSquares.
     * @param board The 2D array of CheckerSquares to be flipped.
     * @return The now flipped 2D array of CheckerSquares.
     */
    private CheckerSquare[][] flipBoard(CheckerSquare[][] board) {
        // Store board length to reduce calls to board.length.
        int length = board.length;
        CheckerSquare[][] flippedBoard = new CheckerSquare[length][length];

        // Iterate through the board and vertically flip it.
        for (int r = 0; r < length; r++) {
            for (int c = 0; c < length; c++) {
                flippedBoard[(length - 1) - r][c] = board[r][c];
            }
        }

        return flippedBoard;
    }

}