
// TEAM A | AI PSEUDOCODE
// Gavin Hager | 10/16/2023
// This pseudocode is for Minimax + Alpha-Beta Pruning

// Basic Pseudocode Concept:
public class TeamA_AI{
    public static gameNode Minimax(gameNode gameState, int depth, gameNode alpha, gameNode beta, boolean isMaximizerPlayer){
        
        gameNode posInfity = new gameNode(Integer.MAX_VALUE); // Negative Infinity approximation
        gameNode negInfinity = new gameNode(Integer.MIN_VALUE); // Positive infinity approximation

        /*
            If the move wins the game or the check-limit [depth] is reached, then evaluate the Score of the move
                Stops algorithm from checking beyond limit [depth] or continuing beyond victory
        */
        if((depth == 0) || (checkGameOver(gameState))){
            return staticEval(gameState);
        }

        if(isMaximizerPlayer == true){
            gameNode maxEval = negInfinity; 
            for(gameNode child : gameState.children){
                gameNode eval = Minimax(child, depth - 1, alpha, beta, false);
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if(beta.score <= alpha.score){
                    break; // Ignore branch if it CANNOT affect outcome
                }
            }
        return maxEval;
        }else{
            gameNode minEval = posInfity;
            for(gameNode child : gameState.children){
                gameNode eval = Minimax(child, depth - 1, alpha, beta, true);
                minEval = min(minEval, eval);
                beta = min(beta, eval);
                if(beta.score <= alpha.score){
                    break; // 
                }
            }
            return minEval;
        }
    }
}
