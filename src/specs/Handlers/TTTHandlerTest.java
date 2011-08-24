package specs.Handlers;

import Handlers.TTTHandler;
import models.GameGUI;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;

import static junit.framework.Assert.*;

public class TTTHandlerTest {

  TTTHandler handler;

  @Before
  public void setUp() {
    handler = new TTTHandler();
  }

  @Test
  public void executeCallsHomeForAHomeRequest() throws Exception {
    assertTrue(readDocument(handler.execute("/")).contains("Human vs. Human"));
  }

  @Test
  public void executeCallsNewGameForValidNewGameRequest() throws Exception {
    handler.execute("/HumanVsComputer");
    assertTrue(readDocument(handler.execute("/setName?player1Name=Kevin")).contains("table"));
  }

  @Test
  public void executeCallsNewGameForAnotherValidGameRequest() throws Exception {
    assertTrue(readDocument(handler.execute("/ComputerVsComputer")).contains("table"));
  }

  @Test
  public void executeCallsHumanMoveForValidBoardRequest() throws Exception {
    handler.execute("/HumanVsHuman");
    assertTrue(readDocument(handler.execute("/board?row=0&column=0")).contains("table"));
  }

  @Test
  public void executeCallsNotFoundForInvalidMoveRequest() throws Exception {
    assertTrue(readDocument(handler.execute("/board?row=3&column=0")).contains("Not Found"));
  }

  @Test
  public void executeCallsNotFoundForRoutesNotFound() throws Exception {
    assertTrue(readDocument(handler.execute("/KarateKid")).contains("Not Found"));
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
    handler.game = new GameGUI("/HumanVsHuman");
    handler.game.player1.setName("Kevin");
    handler.game.player2.setName("Nivek");
    handler.game.takeTurn(new int[]{0, 0});
    assertFalse(handler.game.board.empty());
    handler.execute("/rematch");
    assertTrue(handler.game.board.empty()); 
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
