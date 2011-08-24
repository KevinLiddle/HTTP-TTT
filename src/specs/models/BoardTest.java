package specs.models;

import models.Board;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

  Board board;

  @Before
  public void setUp() {
    board = new Board();
  }

  @Test
  public void testBoardConstructor() {
    assertTrue( board != null );
    assertEquals( 3, board.dimension );
  }

  @Test
  public void testSetBoardCells() {
    int[] cell = { 2, 1 };
    assertEquals( 0, board.cellValueAt(cell));
    board.setCellValue( cell, 1);
    assertEquals( 1, board.cellValueAt(cell) );
  }


  @Test
  public void clearsTheBoard() {
    int[] cell = { 2, 1 };
    board.setCellValue( cell, 1);
    assertEquals( 1, board.cellValueAt(cell));
    board.clear();
    assertEquals( 0, board.cellValueAt(cell));
  }

  @Test
  public void openSpacesReturnsNumberOfOpenSpaces() {
    board.setCellValue(new int[] {0,0}, 1);
    board.setCellValue(new int[] {1,0}, 1);
    board.setCellValue(new int[] {0,1}, -1);
    board.setCellValue(new int[] {2,1}, -1);

    assertEquals(5, board.openSpaces());
  }

  @Test
  public void emptyReturnsTrueIfBoardIsEmpty() {
    assertTrue(board.empty());
    board.setCellValue(new int[] {0,0}, 1);
    assertFalse(board.empty());
  }


}