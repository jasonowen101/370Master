
// TEAM A | AI PSEUDOCODE
// Gavin Hager | 10/16/2023
// This pseudocode is for Minimax + Alpha-Beta Pruning

// Basic Pseudocode Concept:
public class TeamA_AI{
    public static gameNode Minimax(gameNode gameState, int depth, gameNode alpha, gameNode beta, boolean isMaximizerPlayer){
        
        gameNode posInfity = new gameNode(Integer.MAX_VALUE);
        gameNode negInfinity = new gameNode(Integer.MIN_VALUE);

        if((depth == 0) || (checkGameOver(gameState))){
            return staticEval(gameState);
        }

        if(isMaximizerPlayer == true){
            gameNode maxEval = negInfinity; // Negative Infinity approximation
            for(gameNode child : gameState.children){
                gameNode eval = Minimax(child, depth - 1, alpha, beta, false);
                maxEval = max(maxEval, eval);
                alpha = max(alpha, eval);
                if(beta.score <= alpha.score){
                    break;
                }
            }
        return maxEval;
        }else{
            gameNode minEval = posInfity; // Positive infinity approximation
            for(gameNode child : gameState.children){
                gameNode eval = Minimax(child, depth - 1, alpha, beta, true);
                minEval = min(minEval, eval);
                beta = min(beta, eval);
                if(beta.score <= alpha.score){
                    break;
                }
            }
            return minEval;
        }
    }
}