public class Move {
    int score;
    int[] PieceFrom = new int[2]; //uses values of existing boardstate
    int[] PieceTo = new int[2] ; //dependent on move
    private CheckerSquare[] movement;
    private int i;

    public Move(CheckerSquare[] movement, int i)
    {
        this.movement = movement;
        this.i = i;

    }

    public void setPieceTo(int x, int y)
    {
        PieceTo[0] = x;
        PieceTo[1] = y;
    }

    public void setPieceFrom(int x, int y)
    {
        PieceFrom[0] = x;
        PieceFrom[1] = y;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int[] getPieceTo()
    {
        return PieceTo;
    }

    public int[] getPieceFrom()
    {
        return PieceFrom;
    }

    public int getScore()
    {
        return score;
    }

    public String toString()
    {
    return PieceFrom + " " + PieceTo + " " + score;
    }


}
