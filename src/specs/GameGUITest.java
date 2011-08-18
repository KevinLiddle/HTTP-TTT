package specs;

import models.GameGUI;
import models.GameState;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class GameGUITest {

  GameGUI game;

  @Before
  public void setUp() {
    game = new GameGUI("/HumanVsHuman");
    game.player1.setMove(new int[] {0,0});
    game.player2.setMove(new int[] {1,1});
  }

  @Test
  public void makeMoveSetsBoard() {
    game.takeTurn(game.player1.move(game.board));
    assertEquals(1, game.board.cellValueAt(new int[]{0, 0}));
    game.takeTurn(game.player2.move(game.board));
    assertEquals(-1, game.board.cellValueAt(new int[]{1, 1}));
  }

  @Test
  public void invalidMovesNotSet() {
    game.player1.setMove(new int[] {3,1});
    game.takeTurn(game.player1.move(game.board));
    assertTrue(GameState.empty(game.board));
  }
}
