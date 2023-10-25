package AITeamB;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import checkers.CheckerSquare;


public class TeamB_AI {

    int sumOfScores=0; //need to make addToScore method

    public static void main(CheckerSquare[][] boardState)
    {
        Map<Integer, Move> allMoves = new TreeMap<>();
        /*treeMap.put("JumpPiece", 3);
        treeMap.put("becomeKing", 2);
        treeMap.put("blockOpp", 1);
        treeMap.put("noEvent", 0);
        treeMap.put("OppJump", -1);
        treeMap.put("OppKing", -2);
        treeMap.put("becomeBlocked", -3);*/

        //if(maxPlayerTurn==True){
        
        for(LogicPiece piece : boardState) //needs logic piece to be done first
        {
            Move move;
            move = valuate(piece); // assign best move to piece
            allMoves.put(move.score, move);
        }

        int bestScore = 0;
        for(int key : allMoves.keySet())
        {
            if(key>bestScore)
            {
                bestScore = key;
            }
        }
        Move moveToExec = allMoves.get(bestScore);
        executeMove(moveToExec);
        
    }

    // returns highest score move to be executed
    public static Move executeMove(Move move)
    {
        return move;
    }

    // find best move for a piece
    // each check will have a score and if the score is higher than the previous score, it will be the new score and setPieceTo will be set to the move that gave that score
    public static Move valuate(LogicPiece piece)
    {
        Move move = new Move();
        move.setPieceFrom(piece.getX(), piece.getY());
        move.setPieceTo(piece.getX(), piece.getY());;
        move.score = 0;

        // check if its a king, if so add in reverse checks
        // going to finish the rest of the checks for a normal piece first and then add in the king checks (should be almost a copy paste job)

        // check if it can jump
        // checks if it can jump to the right by checking if the piece to the right is an opponent and if the piece to the right of that is empty
        // need to check if the piece is in bounds too
        if(piece.getX()+2 < 8  && piece.getY()+2 < 8 && boardState[piece.getX()+1][piece.getY()+1] != null &&  piece.getTeam() != boardState[piece.getX()+1][piece.getY()+1].getTeam && boardState[piece.getX()+2][piece.getY()+2] == null)
        {
            if(move.getScore() < 3)
            {
                move.setPieceTo(piece.getX()+2, piece.getY()+2);
                move.setScore(3);
                if(doesKing(piece)) // check if the jump would also make it a king
                {
                    move.setScore(5);
                }
            }
        }
        // same as before but for the left
        if(piece.getX()-2 > -1  && piece.getY()+2 < 8 && boardState[piece.getX()-1][piece.getY()+1] != null &&  piece.getTeam() != boardState[piece.getX()-1][piece.getY()+1].getTeam && boardState[piece.getX()-2][piece.getY()+2] == null)
        {
            if(move.getScore() < 3)
            {
                move.setPieceTo(piece.getX()-2, piece.getY()+2);
                move.setScore(3);
                if(doesKing(piece)) // check if the jump would also make it a king
                {
                    move.setScore(5);
                }
            }
        }
        // check if it can become a king by left move
        if(piece.getX()-1 > -1  && piece.getY()+1 == 7 && boardState[piece.getX()-1][piece.getY()+1] == null)
        {
            if(move.getScore() < 2)
            {
                move.setPieceTo(piece.getX()-1, piece.getY()+1);
                move.setScore(2);
            }
        }
        // check if it can become a king by right move
        if(piece.getX()+1 > 8  && piece.getY()+1 == 7 && boardState[piece.getX()-1][piece.getY()+1] == null)
        {
            if(move.getScore() < 2)
            {
                move.setPieceTo(piece.getX()+1, piece.getY()+1);
                move.setScore(2);
            }
        }
        // check if it can block an opponent to the left
        // check if it can block an opponent to the right

        // check if it can move left
        if(piece.getX()-1 > -1  && piece.getY()+1 < 8 && boardState[piece.getX()-1][piece.getY()+1] == null)
        {
            if(move.getScore() < 1)
            {
                move.setPieceTo(piece.getX()-1, piece.getY()+1);
                move.setScore(1);
            }
        }
        // check if it can move right
        if(piece.getX()+1 > 8  && piece.getY()+1 < 8 && boardState[piece.getX()-1][piece.getY()+1] == null)
        {
            if(move.getScore() < 1)
            {
                move.setPieceTo(piece.getX()+1, piece.getY()+1);
                move.setScore(1);
            }
        }
        // check if it can be jumped

        return move;
    }
    
    public boolean doesKing(LogicPiece piece)
    {
        if(piece.getY() == 7)
        {
            return true;
        }
    }
}
