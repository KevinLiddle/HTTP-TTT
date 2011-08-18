package specs;

import models.Board;
import models.HumanPlayerGUI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HumanPlayerGUITest {

  HumanPlayerGUI player;
  Board board;

  @Before
  public void setUp() {
    player = new HumanPlayerGUI(1);
    board = new Board();
  }

  @Test
  public void setMoveSetsMoveVariable_moveGetsMove() {
    int[] expectedMove = {2,1};
    player.setMove(expectedMove);
    assertEquals(expectedMove, player.move(board));
  }


}
