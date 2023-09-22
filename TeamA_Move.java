public class TeamA_Move {
    
    private LogicBoard currentState;
    private TeamA_Move nextMove;
    private int value;

    public TeamA_Move(LogicBoard initialState){
        this.currentState = initialState;
        this.value = 0;       

    }

    public TeamA_Move getNextMove(){
        return this.nextMove;
    }

    private int returnValue(TeamA_Move nextMove){
        try{
            return returnValue(nextMove.getNextMove());
        }
        catch(Exception e){
            return value;
        }
    }

    public int returnValue(){
        return returnValue(this.nextMove);
    }
}
