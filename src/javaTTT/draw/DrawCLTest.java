package javaTTT.draw;

import javaTTT.GameCL;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DrawCLTest {

  GameCL game;

  @Before
  public void setUp() {
    game = new GameCL();
  }

  @Test
  public void testDrawBlankBoard() {
    String drawnBoard = "[ ][ ][ ]\n[ ][ ][ ]\n[ ][ ][ ]\n";
    String actualBoard = DrawCL.draw(game);
    assertEquals( drawnBoard, actualBoard );
  }

  @Test
  public void testDrawPartiallyFilledInBoard() {
    int[][] moves = {{0,0},{1,0},{2,2},{0,2},{1,1}};
    int[] values = {1,1,1,-1,-1};
    for(int i = 0; i < moves.length; i++)
      game.board.setCellValue(moves[i], values[i]);
    String drawnBoard = "[X][ ][O]\n[X][O][ ]\n[ ][ ][X]\n";
    String actualBoard = DrawCL.draw(game);
    assertEquals( drawnBoard, actualBoard );
  }
}
