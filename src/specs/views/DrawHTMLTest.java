package specs.views;

import models.GameGUI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import views.DrawHTML;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class DrawHTMLTest {

  GameGUI game;
  String htmlFooter = "<br /><a id=\"home\" class=\"link\" href=\"/\">Home</a></body></html>";
  String winningBoard = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"game.css\" type=\"text/css\" />" +
      "<script src=\"jquery.js\" type=\"text/javascript\"></script>" +
      "<script src=\"machinePlayer.js\" type=\"text/javascript\"></script>" +
      "</head><body><h2>Player 1(X) vs. Player 2(O)</h2>" +
      "<p class=\"turn\">Player 1's turn</p>" +
      "<table border=\"2\" bordercolor=\"#32A5F5\">\n" +
      "<tr>\n" +
      "<td><p class=\"marker\">X</p></td>\n" +
      "<td><p class=\"board_links\">-</p></td>\n" +
      "<td><p class=\"marker\">O</p></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><p class=\"board_links\">-</p></td>\n" +
      "<td><p class=\"marker\">X</p></td>\n" +
      "<td><p class=\"marker\">O</p></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><p class=\"board_links\">-</p></td>\n" +
      "<td><p class=\"board_links\">-</p></td>\n" +
      "<td><p class=\"marker\">X</p></td>\n" +
      "</tr>\n" +
      "</table><br /><p>Player 1 Wins!</p><br /><a class=\"link\" href=\"/rematch\">Rematch</a>" + htmlFooter;

  @Before
  public void setUp() {
    game = new GameGUI("/HumanVsHuman");
    game.player1.setName("Player 1");
    game.player2.setName("Player 2");
  }

  @Test
  public void drawBlankHTMLBoard() {
    String blankHTMLBoard = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"game.css\" type=\"text/css\" />" +
      "<script src=\"jquery.js\" type=\"text/javascript\"></script>" +
      "<script src=\"machinePlayer.js\" type=\"text/javascript\"></script>" +
      "</head><body><h2>Player 1(X) vs. Player 2(O)</h2>" +
      "<p class=\"turn\">Player 1's turn</p>" +
      "<table border=\"2\" bordercolor=\"#32A5F5\">\n" +
      "<tr>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=0&column=0\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=0&column=1\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=0&column=2\">----</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=1&column=0\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=1&column=1\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=1&column=2\">----</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=2&column=0\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=2&column=1\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/board?row=2&column=2\">----</a></td>\n" +
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
    game = new GameGUI("/ComputerVsComputer");
    assertTrue(DrawHTML.draw(game).contains("<a class=\"button\" href=\"/ComputerMove\">Computer Moves</a>"));
  }

  @Test
  public void saveGameLinkAppearsMidGame() {
    game = new GameGUI("/HumanVsComputer");
    assertFalse(DrawHTML.draw(game).contains("Save Game"));
    game.takeTurn(new int[] {0,0});
    game.takeTurn(null);
    assertTrue(DrawHTML.draw(game).contains("Save Game"));
  }


  private void setXWinner() {
    game.board.setCellValue(new int[] {0,0}, 1);
    game.board.setCellValue(new int[] {1,1}, 1);
    game.board.setCellValue(new int[] {2,2}, 1);
    game.board.setCellValue(new int[] {0,2}, -1);
    game.board.setCellValue(new int[] {1,2}, -1);
  }


}