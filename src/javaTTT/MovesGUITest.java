package javaTTT;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class MovesGUITest {

  MovesGUI moves;
  Player player1;
  Player player2;

  @Before
  public void setUp() {
    moves = new MovesGUI();
    player1 = new HumanPlayerGUI(1, "Player 1");
    player2 = new HumanPlayerGUI(-1, "Player 2");
    player1.setMove(new int[] {0,0});
    player2.setMove(new int[] {1,1});
  }

  @Test
  public void makeMoveSetsBoard() {
    moves.makeMove(player1);
    assertEquals(1, moves.board.cellValueAt(new int[] {0,0}));
    moves.makeMove(player2);
    assertEquals(-1, moves.board.cellValueAt(new int[] {1,1}));
  }

  @Test
  public void invalidMovesNotSet() {
    player1.setMove(new int[] {3,1});
    moves.makeMove(player1);
    assertTrue(GameState.empty(moves.board));
  }


}
