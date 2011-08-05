package javaTTT;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class MovesCLTest {

  static MovesCL moves;
  static HumanPlayerCL player1;
  static HumanPlayerCL player2;

  @Before
  public void setUp() {
    moves = new MovesCL();
    player1 = new HumanPlayerCL( 1, "Marcellus Wallace");
    player2 = new HumanPlayerCL( -1, "Butch");
  }

  @Test
  public void testBoardCellsSetOnMove() {
    int[] cell = {2,1};
    fakeMove(player2);

    assertEquals( -1, moves.board.cellValueAt( cell ) );
  }

  private static void fakeMove(Player player) {
    HumanPlayerCL humanPlayer = (HumanPlayerCL) player;
    String[] input = {"2\n1", "3, 0", "-1,2", "1\n1"};
    InputStream stream = new ByteArrayInputStream(input[0].getBytes());
    int[] move = humanPlayer.promptForMove(stream);
    int i = 1;
    while( move.length < 2 || move[0] > moves.board.dimension - 1 ||
           move[0] < 0     || move[1] > moves.board.dimension - 1 ||
           move[1] < 0     || moves.board.cellValueAt(move) != 0 )
    {
      stream = new ByteArrayInputStream(input[i].getBytes());
      move = humanPlayer.promptForMove(stream);
      i++;
    }
    moves.board.setCellValue( move, humanPlayer.playerValue());
  }

  @Test
  public void testMoveDoesNotAcceptInvalidMoves() {
    int[] move1 = {2,1};
    int[] move2 = {1,1};
    fakeMove(player1);
    fakeMove(player2);

    assertEquals( 1, moves.board.cellValueAt( move1 ) );
    assertEquals( -1, moves.board.cellValueAt( move2 ) );
  }

}
