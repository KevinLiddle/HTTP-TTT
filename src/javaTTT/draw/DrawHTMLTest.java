package javaTTT.draw;

import javaTTT.GameGUI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DrawHTMLTest {

  GameGUI game;
  String htmlFooter = "</body></html>";
  String winningBoard = "<html><head>" +
    DrawHTML.css +
      "</head><body><table>\n" +
      "<tr>\n" +
      "<td><p>X</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p>O</p></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p>X</p></td>\n" +
      "<td><p>O</p></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p>X</p></td>\n" +
      "</tr>\n" +
      "</table><br /><p>Player 1 Wins!</p><br /><a id=\"link\" href=\"/\">Home</a><br /><a id=\"link\" href=\"/HumanVsHuman\">Rematch</a>" + htmlFooter;

  @Before
  public void setUp() {
    game = new GameGUI("/HumanVsHuman");
  }

  @Test
  public void drawBlankHTMLBoard() {
    String blankHTMLBoard = "<html><head>" +
      DrawHTML.css +
      "</head><body><table>\n" +
      "<tr>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=0&column=0\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=0&column=1\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=0&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=1&column=0\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=1&column=1\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=1&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=2&column=0\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=2&column=1\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=2&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "</table>" + htmlFooter;
    Assert.assertEquals(blankHTMLBoard, DrawHTML.draw(game));
  }

  @Test
  public void drawPartiallyFilledInBoard() {
    setXWinner();
    Assert.assertEquals(winningBoard, DrawHTML.draw(game));
  }

  @Test
  public void drawComputerMoveLinkWhenNecessary() {
    game = new GameGUI("/HumanVsComputer");
    assertFalse(DrawHTML.draw(game).contains("<a id=\"link\" href=\"/ComputerMove\">Computer Move</a>"));
    game.turn = -1;
    assertTrue(DrawHTML.draw(game).contains("<a id=\"link\" href=\"/ComputerMove\">Computer Move</a>"));
  }

  @Test
  public void moveLinksAreNotLinksIfComputerPlayersTurn() {
    game = new GameGUI("/ComputerVsHuman");
    String computerTurnBoard = "<html><head>" +
      DrawHTML.css +
      "</head><body><table>\n" +
      "<tr>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "<td><p id=\"board_links\">-</p></td>\n" +
      "</tr>\n" +
      "</table><br />" +
      "<a id=\"link\" href=\"/ComputerMove\">Computer Move</a>" + htmlFooter;
    assertEquals(computerTurnBoard, DrawHTML.draw(game));
  }


  private void setXWinner() {
    game.board.setCellValue(new int[] {0,0}, 1);
    game.board.setCellValue(new int[] {1,1}, 1);
    game.board.setCellValue(new int[] {2,2}, 1);
    game.board.setCellValue(new int[] {0,2}, -1);
    game.board.setCellValue(new int[] {1,2}, -1);
  }


}