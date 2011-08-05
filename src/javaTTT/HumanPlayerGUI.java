package javaTTT;

public class HumanPlayerGUI extends Player {

  private int[] move;

  public HumanPlayerGUI(int playerValue, String playerName) {
    super(playerValue, playerName);
  }

  public int[] move() {
    return this.move;
  }

  public void setMove(int[] move){
    this.move = move;
  }
}