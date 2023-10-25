

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import checkers.CheckerSquare;


public class TeamB_AI {

    int sumOfScores = 0; //need to make addToScore method

    public static void main(CheckerSquare[][] boardState) {
        Map<Integer, Move> allMoves = new TreeMap<>();
        /*treeMap.put("JumpPiece", 3);
        treeMap.put("becomeKing", 2);
        treeMap.put("blockOpp", 1);
        treeMap.put("noEvent", 0);
        treeMap.put("OppJump", -1);
        treeMap.put("OppKing", -2);
        treeMap.put("becomeBlocked", -3);*/

        //if(maxPlayerTurn==True)

        for (LogicPiece piece : boardState) //needs logic piece to be done first
        {
            Move move;
            move = valuate(piece);
            allMoves.put(move.score, move);
        }

        int bestScore = 0;
        for (int key : allMoves.keySet()) {
            if (key > bestScore) {
                bestScore = key;
            }
        }
        Move moveToExec = allMoves.get(bestScore);
        executeMove(moveToExec);

    }

    public static void executeMove(Move move) {
        /*place holder for main move method when 
        executing the executeMove method, parameters are the starting
        position and ending position of the piece being moved*/
        moveMove(move.getpieceFrom, move.getPieceTo);
    }
}
