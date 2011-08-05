package javaTTT;

public class MovesGUI extends Moves {

  public int turn = 1;

  public MovesGUI() {
    board = new Board();
  }

  public void makeMove(Player player) {
    int[] move = player.move();
    
    if( valid(move) ){
      board.setCellValue( move, player.playerValue() );
      turn *= -1;
    }
  }

  private boolean valid(int[] move) {
    return !invalid(move);
  }
}
