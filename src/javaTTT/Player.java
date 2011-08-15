package javaTTT;

public abstract class Player {

  public int playerValue;
  public String name;
  public int[] move;

  public Player(int playerValue) {
    this.playerValue = playerValue;
  }

  public void setMove(int[] move) {
    this.move  = move;
  }

  public void setName(String name) {
    this.name = name;
  }

  public abstract int[] move(Board board);

}
