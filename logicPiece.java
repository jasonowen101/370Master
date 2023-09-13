class LogicPiece {

  private int x;
  private int y;

  private String team;

  private Boolean king;

  public logicPiece(int x, int y) {
    this.x = x;
    this.y = y;

    kingStatus = false;

    team = "empty";
  }

  public logicPiece(int x, int y, String team) {
    this.x = x;
    this.y = y;

    king = false;

    this.team = team;
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

  public void setX(int x){
    this.x = x;
  }

  public void setY(int y){
    this.y = y;
  }

  public void promote(){
    kingStatus = true;
  }

}
