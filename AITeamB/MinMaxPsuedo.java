public class MinMaxPsuedo 
{
    //  Function to find checker pieces (assuming we are blue and they are yellow) from the 8x8 gamestate board
    //  Returns a 2D array of checker pieces
    //  Iterates through the columns and rows of the board and adds the blue pieces to a 2D array
    public static Checker[][] findOurCheckers(GameState gamestate)
    {
        Checker[][] checkers = new Checker[8][8];
        int checkerCount = 0;
        for(int i = 0; i < 8; i++)
        {
            for(int j = 0; j < 8; j++) 
            {
                if(gamestate.board[i][j] == 1 && getTeam(gamestate.board[i][j]) == "blue") 
                {
                    checkers[i][j] = new Checker(i, j);
                    checkerCount++;
                }
            }
        }
        return checkers;
    }

    //  Minmax algorithm to find the best move for the AI (blue), not using alpha-beta pruning
    public static Move minmax(GameState gamestate, int depth, boolean maximizingPlayer)
    {
        if(depth == 0 || gamestate.isGameOver()) // If the game is over or we have reached the max depth, return the score of the gamestate
        {
            return new Move(gamestate.evaluate(), null);
        }

        if(maximizingPlayer) // If we are maximizing, find the best move for blue
        {
            int maxEval = Integer.MIN_VALUE; // Set the maxEval to the lowest possible value
            Move bestMove = null;
            for(Move move : gamestate.getLegalMoves()) // Iterate through all the legal moves
            {
                GameState newGamestate = gamestate.clone(); // Clone the gamestate
                newGamestate.makeMove(move); // Make the move on the new gamestate
                int eval = minmax(newGamestate, depth - 1, false).score; // Recursively call minmax on the new gamestate
                if(eval > maxEval)
                {
                    maxEval = eval;
                    bestMove = move;
                }
            }
            return new Move(maxEval, bestMove);
        }
        else // If we are minimizing, find the best move for yellow
        {
            int minEval = Integer.MAX_VALUE; // Set the minEval to the highest possible value
            Move bestMove = null;
            for(Move move : gamestate.getLegalMoves()) // Iterate through all the legal moves
            {
                GameState newGamestate = gamestate.clone(); // Clone the gamestate
                newGamestate.makeMove(move); // Make the move on the new gamestate
                int eval = minmax(newGamestate, depth - 1, true).score; // Recursively call minmax on the new gamestate
                if(eval < minEval)
                {
                    minEval = eval;
                    bestMove = move;
                }
            }
            return new Move(minEval, bestMove);
        }
    }