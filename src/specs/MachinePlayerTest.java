package specs;

import models.Board;
import models.MachinePlayer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

public class MachinePlayerTest {

  MachinePlayer player;
  Board board;

  @Before
  public void setUp() {
    player = new MachinePlayer(-1);
    board = new Board();
  }

  @Test
  public void machinePlayerCreated() {
    assertTrue(player != null);
  }

  @Test
  public void moveReturnsTheStoredMove() {
    int[] cell = {0,0};
    player.setMove(cell);
    assertArrayEquals(cell, player.move(board));
  }

  @Test
  public void makeMoveSetsABlockForMachine() {
    board.setCellValue(new int[] {0,0}, 1);
    board.setCellValue(new int[] {1,0}, 1);
    board.setCellValue(new int[] {0,1}, -1);
    player.move(board);

    assertArrayEquals(new int[]{2, 0}, player.move(board));
  }

  @Test
  public void makeMoveWinsWhenItCan() {
    board.setCellValue(new int[] {0,0}, -1);
    board.setCellValue(new int[] {1,0}, -1);
    board.setCellValue(new int[] {0,1}, 1);
    board.setCellValue(new int[] {0,2}, 1);
    board.setCellValue(new int[] {1,1}, 1);
    player.move(board);

    assertArrayEquals(new int[] {2,0}, player.move(board));
  }

}
