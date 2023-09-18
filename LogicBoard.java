public class LogicBoard {
    private LogicPiece[][] board = new LogicPiece[8][8];
    //going to leave this void for now, may want to make it boolean for if they deselect/ make a bad move
    public void makeMove(LogicPiece startPiece, LogicPiece endLocation){
        if (isValidMove(startPiece, endLocation)){
            this.board[endLocation.getX()][endLocation.getY()] = new LogicPiece(startPiece, false);
            this.board[startPiece.getX()][startPiece.getY()] = new LogicPiece(endLocation,  true);
        }
    }
}
