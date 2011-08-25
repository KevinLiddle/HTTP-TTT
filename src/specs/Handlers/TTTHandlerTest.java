package specs.Handlers;

import HTTPServer.Database;
import Handlers.TTTHandler;
import models.GameGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.util.Date;

import static junit.framework.Assert.*;

public class TTTHandlerTest {

  TTTHandler handler;

  @Before
  public void setUp() {
    handler = new TTTHandler();
  }

  @After
  public void tearDown() {
    Database.table().remove(handler.game);
  }

  @Test
  public void executeCallsHomeForAHomeRequest() throws Exception {
    assertTrue(homePage(handler.execute("/")));
  }

  @Test
  public void executeCallsNewGameForValidNewGameRequest() throws Exception {
    handler.execute("/HumanVsComputer");
    assertTrue(gamePage(handler.execute("/setName?player1Name=Kevin")));
  }

  @Test
  public void executeCallsNewGameForAnotherValidGameRequest() throws Exception {
    assertTrue(gamePage(handler.execute("/ComputerVsComputer")));
  }

  @Test
  public void executeCallsHumanMoveForValidBoardRequest() throws Exception {
    handler.execute("/HumanVsHuman");
    assertTrue(gamePage(handler.execute("/board?row=0&column=0")));
  }

  @Test
  public void executeCallsNotFoundForInvalidMoveRequest() throws Exception {
    assertTrue(notFoundPage(handler.execute("/board?row=3&column=0")));
  }

  @Test
  public void executeCallsNotFoundForRoutesNotFound() throws Exception {
    assertTrue(notFoundPage(handler.execute("/KarateKid")));
  }

  @Test
  public void setPlayerNamesParsesFormRequestAndSetsPlayerNames() throws Exception {
    handler.execute("/HumanVsComputer");
    handler.execute("/setName?player1Name=Kevin");
    assertEquals("Kevin", handler.game.player1.name);
    assertEquals("TicTacTobot 2000", handler.game.player2.name);
  }

  @Test
  public void gameBeginsAfterNamesSet() throws Exception {
    handler.execute("/HumanVsHuman");
    handler.execute("/setName?player1Name=Kevin&player2Name=Paul");
    assertEquals("Kevin", handler.game.player1.name);
    assertEquals("Paul", handler.game.player2.name);
  }

  @Test
  public void rematchClearsCurrentGameBoard() throws Exception {
    setUpGame();
    assertFalse(handler.game.board.empty());
    handler.execute("/rematch");
    assertTrue(handler.game.board.empty()); 
  }

  @Test
  public void saveGameSavesCurrentGameObjectToServer() throws Exception {
    setUpGame();
    assertTrue(homePage(handler.execute("/saveGame")));
    assertTrue(Database.table().contains(handler.game));
  }

  @Test
  public void loadGameBringsUserToBoardOfLoadedGame() throws Exception {
    setUpGame();
    assertTrue(homePage(handler.execute("/saveGame")));
    handler.execute("/loadGames");
    assertTrue(gameInProgress(handler.execute("/loadGame?id=0")));
  }

  @Test
  public void loadGameRemovesGameFromDatabase() throws Exception {
    setUpGame();
    handler.execute("/saveGame");
    assertTrue(readDocument(handler.execute("/loadGames")).contains("Kevin vs. Nivek"));
    handler.loadGame("/loadGame?id=0");
    assertFalse(readDocument(handler.execute("/loadGames")).contains("Kevin vs. Nivek"));
  }

  @Test
  public void saveGameSetsGamesSavedAtFieldToToday() throws Exception {
    setUpGame();
    handler.execute("/saveGame");
    GameGUI game = (GameGUI) Database.table().get(0);
    Date today = new Date();
    assertEquals(today.getDate(), game.savedAt.getDate());
  }


  private boolean homePage(BufferedReader br) throws Exception {
    return page(br, "Human vs. Human");
  }

  private boolean gamePage(BufferedReader br) throws Exception {
    return page(br, "table");
  }

  private boolean notFoundPage(BufferedReader br) throws Exception {
    return page(br, "Not Found");
  }

  private boolean gameInProgress(BufferedReader br) throws Exception {
    String document = readDocument(br);
    return (document.contains("table") && document.contains("X"));
  }

  private boolean page(BufferedReader br, String pageElement) throws Exception {
    return readDocument(br).contains(pageElement);
  }

  private void setUpGame() {
    handler.game = new GameGUI("/HumanVsHuman");
    handler.game.player1.setName("Kevin");
    handler.game.player2.setName("Nivek");
    handler.game.takeTurn(new int[] {0,0});
  }

  private String readDocument(BufferedReader br) throws Exception {
    String line = br.readLine();
    String document = "";
    while(line != null){
      document += line + "\n";
      line = br.readLine();
    }
    return document;
  }

}
