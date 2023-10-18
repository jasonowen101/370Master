package AITeamB;

import checkers.CheckerSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Defines a TreeNode for use with deciding the best move for the Team B AI to choose.
 * The first node is considered the root node, as all nodes added on to it and it's
 * children will be accessible from it.
 */
public final class MinMaxTreeNode {
    private final boolean isMaxPlayer;
    private int score;
    private final checkers.CheckerSquare[][] boardState;
    private final List<MinMaxTreeNode> children;

    /**
     * Constructs a new MinMaxNode with the given arguments. Initializes score to 0 and
     * children to an empty ArrayList storing MinMaxTreeNodes
     *
     * @param isMaxPlayer Defines if a player is a max player or not
     * @param boardState The current state of the board in this node
     */
    public MinMaxTreeNode(boolean isMaxPlayer, checkers.CheckerSquare[][] boardState) {
        this.isMaxPlayer = isMaxPlayer;
        this.score = 0;
        this.boardState = Objects.requireNonNull(boardState);
        this.children = new ArrayList<MinMaxTreeNode>();
    }

    /**
     * Adds the given MinMaxNode to the list of children in this MinMaxNode
     *
     * @param n A non-null MinMaxNode to be added to this MinMaxNode's children
     */
    public void addChild(MinMaxTreeNode n) {
        children.add(Objects.requireNonNull(n));
    }

    public boolean getIsMaxPlayer() {
        return isMaxPlayer;
    }

    public int getScore() {
        return score;
    }

    public CheckerSquare[][] getBoardState() {
        return boardState;
    }

    public List<MinMaxTreeNode> getChildren() {
        return children;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
