package TeamA_AI;

import java.awt.Color;
import java.util.List;

import checkers.CheckerSquare;

public class TeamA_GameNode {
    CheckerSquare[][] board;
    List<TeamA_GameNode> childNodes;
    Color playerColor;
    Color enemyColor;
    int value;
    byte playerPeon;
    byte playerKings;
    byte enemyPeon;
    byte enemyKings;
    byte depth;

    public TeamA_GameNode(CheckerSquare[][] board, Color playerColor, byte depth){

        this.board = board;
        this.playerColor = playerColor;
        this.enemyColor = (playerColor == CheckerSquare.TEAM1) ? CheckerSquare.TEAM2 : CheckerSquare.TEAM1;

    }

    public void addChildNode(TeamA_GameNode){

    }

    /**
     * @return the board
     */
    public CheckerSquare[][] getBoard() {
        return board;
    }

    /**
     * @return the childNodes
     */
    public List<TeamA_GameNode> getChildNodes() {
        return childNodes;
    }

    /**
     * @return the depth
     */
    public byte getDepth() {
        return depth;
    }

    /**
     * @return the enemyKings
     */
    public byte getEnemyKings() {
        return enemyKings;
    }

    /**
     * @return the enemyPeon
     */
    public byte getEnemyPeon() {
        return enemyPeon;
    }

    /**
     * @return the playerKings
     */
    public byte getPlayerKings() {
        return playerKings;
    }

    /**
     * @return the playerPeon
     */
    public byte getPlayerPeon() {
        return playerPeon;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param board the board to set
     */
    public void setBoard(CheckerSquare[][] board) {
        this.board = board;
    }

    /**
     * @param childNodes the childNodes to set
     */
    public void setChildNodes(List<TeamA_GameNode> childNodes) {
        this.childNodes = childNodes;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(byte depth) {
        this.depth = depth;
    }

    /**
     * @param enemyKings the enemyKings to set
     */
    public void setEnemyKings(byte enemyKings) {
        this.enemyKings = enemyKings;
    }

    /**
     * @param enemyPeon the enemyPeon to set
     */
    public void setEnemyPeon(byte enemyPeon) {
        this.enemyPeon = enemyPeon;
    }

    /**
     * @param playerKings the playerKings to set
     */
    public void setPlayerKings(byte playerKings) {
        this.playerKings = playerKings;
    }

    /**
     * @param playerPeon the playerPeon to set
     */
    public void setPlayerPeon(byte playerPeon) {
        this.playerPeon = playerPeon;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
}
