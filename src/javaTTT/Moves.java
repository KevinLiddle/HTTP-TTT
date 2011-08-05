package javaTTT;

public abstract class Moves {

  public Board board;

  public abstract void makeMove(Player player);

  protected boolean invalid(int[] move) {
    return (move.length < 2 || move[0] > board.dimension - 1 ||
            move[0] < 0     || move[1] > board.dimension - 1 ||
            move[1] < 0     || board.cellValueAt(move) != 0);
  }
}
