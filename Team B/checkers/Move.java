import java.util.Arrays;

public class Move {
    private int score;
    private CheckerSquare[] movement;

    public Move(CheckerSquare[] movement, int score)
    {
        this.movement = movement;
        this.score = score;

    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    public CheckerSquare[] getMovement()
    {
        return movement;
    }
}
