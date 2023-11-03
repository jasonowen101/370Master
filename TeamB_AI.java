

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.awt.Color;

import checkers.CheckerSquare;
import checkers.GamePanel;



public class TeamB_AI implements AI {

    private static CheckerSquare[][] boardState;
	private static Color enemyColor;
	
	final static int KING_VALUE = 4;
	final static int JUMP_VALUE = 3;
    final static int MOVE_VALUE = 2;
    final static int UNSAFE_MOVE_VALUE = 1;

    private int sumOfScores = 0; //need to make addToScore method
    private static CheckerSquare[][] checkers = GamePanel.getSquares();
    private static Move perform;

    public static void mainAI() 
    {
        
        Map<Integer, Move> allMoves = new TreeMap<>();
        /*
        for (CheckerSquare[] checkerSquares : checkers) //needs logic piece to be done first
        {
            Move move;
            move = valuate(checkerSquares); // assign best move to piece
            allMoves.put(move.score, move);
        } */
        for(int i=0; i<checkers.length; i++)
        {
            for(int j=0; j<checkers[i].length; j++)
            {
                if(checkers[i][j].getCheckerColor() == Color.BLUE)
                {
                    valuate(checkers[i][j]);
                }
            }
        }

        int bestScore = 0;
        for (int key : allMoves.keySet()) {
            if (key > bestScore) {
                bestScore = key;
            }
        }
        perform = allMoves.get(bestScore);
        executeMove(perform);

    }

    public CheckerSquare[] getMove()
    {
        CheckerSquare[] moveToPerform = {
            perform.getPieceFrom(),
            perform.getPieceTo()

        };
        
        return moveToPerform;
    } 

    // returns highest score move to be executed
    public static Move executeMove(Move move)
    {


        return move;
    }

    // check if position is within bounds of board
	private static boolean validPos(int r, int c) {
        return r > -1 && r < 8 && c > -1 && c < 8;
    }

	// check if position is an enemy 
    private static boolean isEnemy(int r, int c) {
        if(validPos(r, c))
            return boardState[r][c].getCheckerColor().equals(enemyColor);
        else
            return false;
    }

	// check if position is an enemy king
    private static boolean isEnemyKing(int r, int c) {
        if(validPos(r, c))
            return isEnemy(r, c) && boardState[r][c].isKing();
        else
            return false;
    }

	// check if position is empty
    private static boolean isEmpty(int r, int c) {
        if(validPos(r, c))
            return Objects.isNull(boardState[r][c].getCheckerColor());
        else
            return false;
    }

	// check if the position is a jump
    private static boolean isJump(int r1, int c1, int r2, int c2) {
        return isEmpty(r1, c1) && isEnemy(r2, c2);
    }

	// check if position is safe
    private static boolean isSafe(int r, int c) {
		return !(isEnemy(r-1, c-1) && isEmpty(r+1, c+1))
                || (isEnemy(r-1, c+1) && isEmpty(r+1, c-1))
                || (isEnemyKing(r+1, c-1) && isEmpty(r-1, c+1))
                || (isEnemyKing(r+1, c+1) && isEmpty(r-1, c-1));
    }
	
	// check if position will king a piece
	private static boolean isKingSpace(int r, int c) {
		if(validPos(r ,c))
			return isEmpty(r, c) && r == 7;
		else
			return false;
	}
    // calculate all possible moves for a piece and return most valuable
    public static Move valuate(CheckerSquare piece) {
        if(Objects.isNull(piece))
            throw new NullPointerException("Piece cannot be null!");

        ArrayList<Move> moves = new ArrayList<>();
        Move perform = new Move(new CheckerSquare[]{null, null}, -1);

        int r = piece.getRow();
        int c = piece.getCol();

		// TODO: Ensure 

		// check if diagonal up-left is a jump
		if (isJump(r-1, c-1, r-2, c-2)) {
			moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-2][c-2]}, JUMP_VALUE));
		}
		// check if diagonal up-right is a jump
		if (isJump(r-1, c+1, r-2, c+2)) {
			moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-2][c+2]}, JUMP_VALUE));
		}
		// check if diagonal up-left is empty
		if (isEmpty(r-1, c-1)) {
			// check if position results in a king, is safe, or is unsafe
			if(isKingSpace(r-1, c-1) && !piece.isKing()) {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-1][c-1]}, KING_VALUE));
			} else if(isSafe(r-1, c-1)) {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-1][c-1]}, MOVE_VALUE));
			} else {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-1][c-1]}, UNSAFE_MOVE_VALUE));
			}
		}
		// check if diagonal up-right is empty
		if (isEmpty(r-1, c+1)) {
			// check if position results in a king, is safe, or is unsafe
			if(isKingSpace(r-1, c+1) && !piece.isKing()) {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-1][c+1]}, KING_VALUE));
			} else if(isSafe(r-1, c+1)) {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-1][c+1]}, MOVE_VALUE));
			} else {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r-1][c+1]}, UNSAFE_MOVE_VALUE));
			}
		}
		// evaluate king specific moves
		if(piece.isKing()) {
			// check if diagonal down-left is a jump
			if (isJump(r+1, c-1, r+2, c-2)) {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r+2][c-2]}, JUMP_VALUE));
			}
			// check if diagonal down-right is a jump
			if (isJump(r+1, c+1, r+2, c+2)) {
				moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r+2][c+2]}, JUMP_VALUE));
			}
			// check if diagonal down-left is empty
			if (isEmpty(r+1, c-1)) {
				// check if position is safe
				if(isSafe(r+1, c-1)) {
					moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r+1][c-1]}, MOVE_VALUE));
				} else {
					moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r+1][c-1]}, UNSAFE_MOVE_VALUE));
				}
			}
			// check if diagonal down-right is empty
			if (isEmpty(r+1, c+1)) {
				// check if position is safe
				if(isSafe(r+1, c+1)) {
					moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r+1][c+1]}, MOVE_VALUE));
				} else {
					moves.add(new Move(new CheckerSquare[]{boardState[r][c], boardState[r+1][c+1]}, UNSAFE_MOVE_VALUE));
				}
			}
		}

        int s = -1;
        for(Move m : moves) {
            if(m.getScore() > s) {
                s = m.getScore();
                perform = m;
            }
        }

        return perform;
    }
}
