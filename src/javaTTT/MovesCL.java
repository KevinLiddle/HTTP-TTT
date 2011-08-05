package javaTTT;

public class MovesCL extends Moves {

  public MovesCL() {
    board = new Board();
  }

  public void makeMove(Player player) {
    int[] move = { -1, -1 };
    while( invalid(move) ){
      move = player.move();
    }
    board.setCellValue( move, player.playerValue() );
  }

}
