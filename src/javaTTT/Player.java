package javaTTT;

public abstract class Player {

  public int playerValue;
  public String playerName;

  public Player(int playerValue, String playerName) {
    this.playerValue = playerValue;
    this.playerName = playerName;
  }

  public int playerValue() {
    return playerValue;
  }

  public String playerName() {
    return playerName;
  }

  public abstract int[] move();

  public abstract void setMove(int[] move);

}
