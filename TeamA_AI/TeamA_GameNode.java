//Potential Help
//https://github.com/Mchristos/checkers

package TeamA_AI;

import java.awt.Color;
import java.util.List;

import checkers.CheckerSquare;

public class TeamA_GameNode {
    CheckerSquare[][] board;
    List<TeamA_GameNode> childNodes;
    Color playerColor;
    Color enemyColor;
    int value = 0;
    byte playerPeon = 0;
    byte playerKings = 0;
    byte enemyPeon = 0;
    byte enemyKings = 0;
    byte depth;

    public TeamA_GameNode(CheckerSquare[][] board, Color playerColor, byte depth){

        this.board = board;
        this.playerColor = playerColor;
        this.enemyColor = (playerColor == CheckerSquare.TEAM1) ? CheckerSquare.TEAM2 : CheckerSquare.TEAM1;

        this.depth = depth;

        for(CheckerSquare[] col : board){
            for(CheckerSquare indSquare : col){
                if (indSquare.getCheckerColor() == playerColor){
                    if(indSquare.isKing()) this.playerKings++;
                    else this.playerPeon++;
                }
                else if (indSquare.getCheckerColor() == enemyColor){
                    if(indSquare.isKing()) this.enemyKings++;
                    else this.enemyPeon++;
                }
            }
        }
        this.value += (this.playerKings * TeamA_Weight.PLAYER_KING);
        this.value += (this.playerPeon * TeamA_Weight.PLAYER_PEON);
        this.value += (this.enemyKings * TeamA_Weight.ENEMY_KING);
        this.value += (this.enemyPeon * TeamA_Weight.ENEMY_PEON);

    }

    public TeamA_GameNode(TeamA_GameNode parentNode){

    }

    public void addChildNode(TeamA_GameNode){

    }

    private CheckerSquare[][] copy(CheckerSquare[][] original){
        CheckerSquare[][] copied;
        if(original.length > 0) if (original[0].length > 0){
            copied = new CheckerSquare[original.length][original[0].length];

            for(byte x = 0; x <  original.length; x++){
                for(byte y = 0; y <  original[x].length; y++){
                    copied[x][y] = new CheckerSquare(x,y);
                    copied[x][y].setVisible(false);
                    copied[x][y].setKing(original[x][y].isKing());
                    copied[x][y].setCheckerColor(original[x][y].getCheckerColor());
                }
            }
        }
        return copied;
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
