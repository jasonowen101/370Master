package AITeamB;


public class Move {
    int score;
    int[] PieceFrom = new int[1]; //uses values of existing boardstate
    int[] PieceTo = new int[1] ; //dependent on move

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

    public int[] getpieceFrom()
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
