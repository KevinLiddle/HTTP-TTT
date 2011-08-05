package javaTTT;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DrawHTMLTest {

  Board board;

  @Before
  public void setUp() {
    board = new Board();
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
    Assert.assertEquals(blankHTMLBoard, DrawHTML.draw(board));
  }

  @Test
  public void drawPartiallyFilledInBoard() {
    board.setCellValue(new int[] {0,0}, 1);
    board.setCellValue(new int[] {1,1}, 1);
    board.setCellValue(new int[] {2,2}, 1);
    board.setCellValue(new int[] {0,2}, -1);
    board.setCellValue(new int[] {1,2}, -1);
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
      "</table>";
    Assert.assertEquals(blankHTMLBoard, DrawHTML.draw(board));
  }


}