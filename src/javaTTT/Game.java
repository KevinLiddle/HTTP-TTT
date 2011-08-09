package javaTTT;

public abstract class Game {

  static final int PLAYER_1_VALUE = 1;
  static final int PLAYER_2_VALUE = -1;
  public Player player1;
  public Player player2;
  Moves moves;

  public abstract void createPlayers();

}
