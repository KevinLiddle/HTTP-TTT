package specs.Handlers;

import HTTPServer.Database;
import Handlers.Handler;
import models.GameGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

public class TTTHandlerTest {

  @Before
  public void setUp() {
    Database.table().clear();
  }

  @After
  public void tearDown() {
    Database.table().clear();
  }

  @Test
  public void executeCallsNewGameForValidNewGameRequest() throws Exception {
    Handler.execute("/HumanVsComputer");
    assertTrue(HandlerTestHelpers.gamePage(Handler.execute("/setName?player1Name=Kevin")));
  }

  @Test
  public void executeCallsNewGameForAnotherValidGameRequest() throws Exception {
    assertTrue(HandlerTestHelpers.gamePage(Handler.execute("/ComputerVsComputer")));
  }

  @Test
  public void executeCallsNotFoundForRoutesNotFound() throws Exception {
    assertTrue(HandlerTestHelpers.notFoundPage(Handler.execute("/KarateKid")));
  }

  @Test
  public void setPlayerNamesParsesFormRequestAndSetsPlayerNames() throws Exception {
    Handler.execute("/HumanVsComputer");
    Handler.execute("/setName?player1Name=Kevin");
    GameGUI game = (GameGUI) Database.table().get(0);
    assertEquals("Kevin", game.player1.name);
    assertEquals("TicTacTobot 2000", game.player2.name);
  }

  @Test
  public void gameBeginsAfterNamesSet() throws Exception {
    Handler.execute("/HumanVsHuman");
    Handler.execute("/setName?player1Name=Kevin&player2Name=Paul");
    GameGUI game = (GameGUI) Database.table().get(0);
    assertEquals("Kevin", game.player1.name);
    assertEquals("Paul", game.player2.name);
  }

  @Test
  public void rematchClearsCurrentGameBoard() throws Exception {
    setUpGame();
    GameGUI game = (GameGUI) Database.table().get(0);
    assertFalse(game.board.empty());
    Handler.execute("/0/rematch");
    assertTrue(game.board.empty());
  }

  @Test
  public void saveGameSavesCurrentGameObjectToServer() throws Exception {
    setUpGame();
    GameGUI game = (GameGUI) Database.table().get(0);
    assertTrue(Database.table().contains(game));
  }

  @Test
  public void loadGameBringsUserToBoardOfLoadedGame() throws Exception {
    setUpGame();
    Handler.execute("/loadGames");
    assertTrue(HandlerTestHelpers.gameInProgress(Handler.execute("/loadGame?id=0")));
  }

  @Test
  public void finishingAGameRemovesItFromDB() throws Exception {
    setUpGame();
    Handler.execute("/0/board?row=1&column=1");
    Handler.execute("/0/board?row=0&column=1");
    Handler.execute("/0/board?row=2&column=2");
    Handler.execute("/0/board?row=0&column=2");
    assertFalse(HandlerTestHelpers.readDocument(Handler.execute("/loadGames")).contains("Kevin vs. Nivek"));
  }


//  @Test
//  public void saveGameSetsGamesSavedAtFieldToToday() throws Exception {
//    setUpGame();
//    GameGUI game = (GameGUI) Database.table().get(0);
//    Date today = new Date();
//    assertEquals(today.getDate(), game.createdAt.getDate());
//  }

  @Test
  public void savedGamesPickUpWhereTheyLeftOff() throws Exception {
    setUpGame();
    Handler.execute("/0/saveGame");
    assertEquals("<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"game.css\" type=\"text/css\" />" +
      "<script src=\"jquery.js\" type=\"text/javascript\"></script>" +
      "<script src=\"machinePlayer.js\" type=\"text/javascript\"></script>" +
      "</head><body><h2>Kevin(X) vs. Nivek(O)</h2>" +
      "<p class=\"turn\">Nivek's turn</p>" +
      "<table border=\"2\" bordercolor=\"#32A5F5\">\n" +
      "<tr>\n" +
      "<td><p class=\"marker\">X</p></td>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=0&column=1\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=0&column=2\">----</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=1&column=0\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=1&column=1\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=1&column=2\">----</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=2&column=0\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=2&column=1\">----</a></td>\n" +
      "<td><a class=\"board_links\" href=\"/0/board?row=2&column=2\">----</a></td>\n" +
      "</tr>\n" +
      "</table>" +
      "<br /><a id=\"home\" class=\"link\" href=\"/\">Home</a>" +
      "</body></html>\n",
      HandlerTestHelpers.readDocument(Handler.execute("/loadGame?id=0")));
    assertEquals(1, ((GameGUI) Database.table().get(0)).board.cellValueAt(new int[]{0, 0}));
  }

  private void setUpGame() throws Exception {
    Handler.execute("/setName?player1Name=Kevin&player2Name=Nivek");
    Handler.execute("/0/board?row=0&column=0");
  }

}
