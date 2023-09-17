class LogicPiece {

  private int x;
  private int y;

  private String team;

  private Boolean kingStatus;

  public LogicPiece(int x, int y) {           //didn't use this constructor
    this.x = x;
    this.y = y;

    kingStatus = false;

    team = "empty";
  }

  public LogicPiece(int x, int y, String team) {
    this.x = x;
    this.y = y;

    kingStatus = false;

    this.team = team;
  }

  public LogicPiece(LogicPiece piece, boolean isEmpty){
    // A constuctor I made for moving, just an idea, but this may change from what I understand
    this.x = piece.getX();
    this.y = piece.getY();

    kingStatus = piece.getKingStatus();

    this.team = isEmpty ? "empty" : piece.getTeam();
  }

  public String getTeam() {
    return team;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public boolean getKingStatus(){
    return kingStatus;
  }

  public void setX(int x){             //as of now, our plan is to not actually move any of the pieces within the array, so the set X and Y methods shouldn't be used
    this.x = x;
  }

  public void setY(int y){            //see above comment
    this.y = y;
  }

  public void promote(){
    kingStatus = true;
  }

}
