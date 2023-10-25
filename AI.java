public interface AI {
    /**
     * Function to calculate and return the move that the AI wants to perform.
     *
     * @return An array of two CheckerSquares containing the piece that is going to be moved, and the position it is being moved to.
     */
    public checkers.CheckerSquare[] getMove();
}
