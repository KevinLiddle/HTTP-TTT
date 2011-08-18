package models;

public abstract class Game {

  static final int PLAYER_1_VALUE = 1;
  static final int PLAYER_2_VALUE = -1;
  public Player player1;
  public Player player2;
  public Board board;

  public abstract void createPlayers();

  public boolean invalid(int[] move) {
    return (move == null || move.length < 2 || move[0] > board.dimension - 1 ||
            move[0] < 0  || move[1] > board.dimension - 1 ||
            move[1] < 0  || board.cellValueAt(move) != 0);
  }

}
