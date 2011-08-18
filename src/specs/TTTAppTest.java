package specs;

import Application.TTTApp;
import models.GameGUI;
import models.GameState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TTTAppTest {

  TTTApp app;

  @Before
  public void setUp() {
    app = new TTTApp();
    app.game = new GameGUI("/HumanVsHuman");
  }

  @Test
  public void validBoardURIsMatchValidBoardMove() {
    assertEquals(app.OK, app.status("/board?row=0&column=0"));
  }

  @Test
  public void validBoardURIsDontMatchInvalidURI() {
    assertEquals(app.NOT_FOUND, app.status("/board?row=0&column=0rgkhr"));
  }

  @Test
  public void validBoardURIsDontMatchInvalidMove() {
    assertEquals(app.NOT_FOUND, app.status("/board?row=0&column=3"));
  }

  @Test
  public void validBoardURIsDontMatchIfGameIsNull() {
    app.game = null;
    assertEquals(app.NOT_FOUND, app.status("/board?row=0&column=0"));
  }

  @Test
  public void computerCannotMoveIfNotItsTurn() throws Exception {
    app.game = new GameGUI("/HumanVsComputer");
    assertEquals(1, app.game.turn);
    app.serverResponse(new String[] {"GET", "/ComputerMove", "HTTP/1.0"});
    assertTrue(GameState.empty(app.game.board));
  }


}
