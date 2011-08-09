package javaTTT.draw;

import javaTTT.GameGUI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DrawHTMLTest {

  GameGUI game;

  @Before
  public void setUp() {
    game = new GameGUI();
  }

  @Test
  public void drawBlankHTMLBoard() {
    String blankHTMLBoard = "<table>\n" +
      "<tr>\n" +
      "<td><a href=\"/board?row=0&column=0\">-</a></td>\n" +
      "<td><a href=\"/board?row=0&column=1\">-</a></td>\n" +
      "<td><a href=\"/board?row=0&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a href=\"/board?row=1&column=0\">-</a></td>\n" +
      "<td><a href=\"/board?row=1&column=1\">-</a></td>\n" +
      "<td><a href=\"/board?row=1&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a href=\"/board?row=2&column=0\">-</a></td>\n" +
      "<td><a href=\"/board?row=2&column=1\">-</a></td>\n" +
      "<td><a href=\"/board?row=2&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "</table>";
    Assert.assertEquals(blankHTMLBoard, DrawHTML.draw(game));
  }

  @Test
  public void drawPartiallyFilledInBoard() {
    game.moves.board.setCellValue(new int[] {0,0}, 1);
    game.moves.board.setCellValue(new int[] {1,1}, 1);
    game.moves.board.setCellValue(new int[] {2,2}, 1);
    game.moves.board.setCellValue(new int[] {0,2}, -1);
    game.moves.board.setCellValue(new int[] {1,2}, -1);
    String blankHTMLBoard = "<table>\n" +
      "<tr>\n" +
      "<td>X</td>\n" +
      "<td><a href=\"/board?row=0&column=1\">-</a></td>\n" +
      "<td>O</td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a href=\"/board?row=1&column=0\">-</a></td>\n" +
      "<td>X</td>\n" +
      "<td>O</td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a href=\"/board?row=2&column=0\">-</a></td>\n" +
      "<td><a href=\"/board?row=2&column=1\">-</a></td>\n" +
      "<td>X</td>\n" +
      "</tr>\n" +
      "</table><br />Player 1 Wins!<br /><a href=\"/\">Home</a><br /><a href=\"/HumanVsHuman\">Rematch</a>";
    Assert.assertEquals(blankHTMLBoard, DrawHTML.draw(game));
  }


}