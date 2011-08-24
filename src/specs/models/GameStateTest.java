package specs.models;

import models.Board;
import models.GameState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateTest {

  Board board;

  @Before
  public void setUp() {
    board = new Board();
    int[][] moves = {{0,0},{2,0},{0,2},{2,2}};
    for(int[] move : moves)
      board.setCellValue(move,1);
  }

  @Test
  public void testHorizontalWinnerReturnsZeroForNoWinner() {
    Assert.assertEquals(0, GameState.HorizontalWinner(board));
  }

  @Test
  public void testHorizontalWinnerIsEqualToWinnerValue() {
    int[] cell = {0,1};
    board.setCellValue( cell, 1);
    assertEquals( 1, GameState.HorizontalWinner(board) );
  }

  @Test
  public void testVerticalWinnerReturnsZeroForNoWinner() {
    assertEquals( 0, GameState.VerticalWinner(board) );
  }

  @Test
  public void testVerticalWinnerReturnsWinnerValue() {
    int[] cell = {1,2};
    board.setCellValue( cell, 1);
    assertEquals( 1, GameState.VerticalWinner(board) );
  }

  @Test
  public void testDiagonalWinnerReturnsZeroForNoWinner() {
    assertEquals( 0, GameState.DiagonalWinner(board) );
  }

  @Test
  public void testDiagonalWinnerReturnsOneForFirstDiagonal() {
    int[] cell1 = {1,1};
    int[] cell2 = {0,2};
    board.setCellValue( cell1, 1);
    board.setCellValue( cell2, 0);
    assertEquals( 1, GameState.DiagonalWinner(board) );
  }

  @Test
  public void testDiagonalWinnerReturnsOneForSecondDiagonal() {
    int[] cell1 = {1,1};
    int[] cell2 = {0,0};
    board.setCellValue( cell1, 1 );
    board.setCellValue( cell2, 0 );
    assertEquals( 1, GameState.DiagonalWinner(board) );
  }

  @Test
  public void testWinnerForVertical() {
    int[] cell = {1,2};
    board.setCellValue( cell, 1 );
    assertEquals( 1, GameState.winner(board) );
  }

  @Test
  public void testWinnerForHorizontal() {
    int[] cell = {0,1};
    board.setCellValue( cell, 1 );
    assertEquals( 1, GameState.winner(board) );
  }

  @Test
  public void testWinnerForDiagonal() {
    int[] cell = {1,1};
    board.setCellValue( cell, 1 );
    assertEquals( 1, GameState.winner(board) );
  }

  @Test
  public void testWinnerIsZeroForNoWinner() {
    assertEquals( 0, GameState.winner(board) );
  }

  @Test
  public void testHasWinnerForVertical() {
    int[] cell = {1,2};
    board.setCellValue( cell, 1 );
    assertTrue( GameState.hasWinner(board) );
  }

  @Test
  public void testHasWinnerForHorizontal() {
    int[] cell = {0,1};
    board.setCellValue( cell, 1 );
    assertTrue( GameState.hasWinner(board) );
  }

  @Test
  public void testHasWinnerForDiagonal() {
    int[] cell = {1,1};
    board.setCellValue( cell, 1 );
    assertTrue( GameState.hasWinner(board) );
  }

  @Test
  public void testHasWinnerIsFalse() {
    assertFalse( GameState.hasWinner(board) );
  }

  @Test
  public void testFullForNonFullBoard() {
    assertFalse( GameState.full(board) );
  }

  @Test
  public void testFullForFullBoard() {
    for(int i = 0; i < board.dimension; i++){
      for(int j = 0; j < board.dimension; j++){
        int[] cell = {i,j};
        board.setCellValue(cell, 1);
      }
    }
    assertTrue( GameState.full(board) );
  }

  @Test
  public void testFinished() {
    assertFalse( GameState.finished(board) );
  }

  @Test
  public void testFinishedOnGameWithWinner() {
    for(int i = 0; i < board.dimension; i++){
      int[] cell = {i,0};
      board.setCellValue(cell, 1);
    }
    assertTrue( GameState.finished(board) );
  }

  @Test
  public void testFinishedOnFullBoard() {
    for(int i = 0; i < board.dimension; i++){
      for(int j = 0; j < board.dimension; j++){
        int[] cell = {i,j};
        board.setCellValue(cell, 1);
      }
    }
    assertTrue( GameState.finished(board) );
  }

  @Test
  public void emptyBoardReturnsFalseForNonEmptyBoard() {
    assertFalse(GameState.empty(board));
  }

  @Test
  public void emptyBoardReturnsTrueForEmptyBoard() {
    board.clear();
    assertTrue(GameState.empty(board));
  }

}
