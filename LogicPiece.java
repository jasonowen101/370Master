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

  public String getTeam() {
    return team;
  }

  public int getX(){
    return x;
  }

  public int getY(){
    return y;
  }

  public Boolean getKingStatus(){
    return kingStatus;
  }

  public void setX(int x){             //as of now, our plan is to not actually move any of the pieces within the array, so the set X and Y methods shouldn't be used
    this.x = x;
  }

  public void setY(int y){            //see above comment
    this.y = y;
  }

  //Names are only place orders, Team1 is the placeholder for the team who's pieces start on the bottom of the board with the top left being (0,0). Team2 is the opposite
  //Feel free to adjust as needed. Also, this method should be called at the end of the movement and jump for the piece that is at the new location. 
  public void promote(){
    if (team == "Team1" && y = 0){
      kingStatus = true;
    }
    if (team == "Team2" && y = 8){
      kingStatus = true;
    }
  }

}
