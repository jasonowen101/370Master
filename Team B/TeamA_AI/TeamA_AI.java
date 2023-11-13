import java.util.ArrayList;
import java.util.Random;

<<<<<<< HEAD
=======
//import checkers.CheckerSquare;

>>>>>>> d950b9bdb79931a46a581a5732d15a6d2b8f2c35
import java.awt.Color;

public class TeamA_AI {

    private byte depth;
    private Color player;

    public TeamA_AI(byte depth, Color player){
        this.depth = depth;
        this.player = player;
    }

    public CheckerSquare[] getMove(CheckerSquare[][] checkerBoard){
        Board move = move(Board.InitialState(this.player, checkerBoard), this.player);

        byte[] selectedPiece = move.getFromPos();
        byte[] endPosition = move.getToPos();

        return new CheckerSquare[] {checkerBoard[selectedPiece[0]][selectedPiece[1]], checkerBoard[endPosition[0]][endPosition[1]]};
    }

    public Board move(Board boardState, Color player){
        if(boardState.getTurn().equals(player)){
            ArrayList<Board> successors = boardState.getSuccessors();
            return minimaxMove(successors);
        }
        throw new RuntimeException("Move gen error, not pley's turn");
    }

    private Board minimaxMove(ArrayList<Board> successors){
        if(successors.size() == 1) return successors.get(0);

        int bestScore = Integer.MIN_VALUE;
        ArrayList<Board> equalBest = new ArrayList<>();

        for (Board success : successors){
            int value = minimax(success, this.depth);
            if(value > bestScore){
                bestScore = value;
                equalBest.clear();
            }

            if(value == bestScore){
                equalBest.add(success);
            }
        }
        return randomMove(equalBest);

    }

    //This will definently need checking, would be interesting to
    //see if it works
    private Board randomMove(ArrayList<Board> bestMoves){
        return bestMoves.get((new Random()).nextInt(bestMoves.size()));
    }

    private int minimax(Board node, byte depth){
        int alpha = Integer.MAX_VALUE;
        int beta = Integer.MIN_VALUE;
        return minimax(node, depth, alpha, beta);
    }

    private int minimax(Board node, byte depth, int alpha, int beta){
        if (depth == 0 || node.isGameOver()){
            return Math.max(node.computeHeuristic(player, true),node.computeHeuristic(player, false));
        }

        if(node.getTurn().equals(this.player)){
            int v = Integer.MIN_VALUE;

            for(Board child : node.getSuccessors()){
                v = Math.max(v, minimax(child, (byte) (depth-1), alpha, beta));
                alpha = Math.max(alpha, v);

                if(alpha >= beta) break;
            }
            return v;
        }

        if(node.getTurn().equals(Board.oppositeColor(player))){
            int v = Integer.MAX_VALUE;

            for(Board child : node.getSuccessors()){
                v = Math.min(v, minimax(child, (byte) (depth-1), alpha, beta));

                beta = Math.min(beta, v);

                if (alpha >= beta) break;
            }
            return v;
        }
        throw new RuntimeException("Error in minimax algorithm");

    }


}
