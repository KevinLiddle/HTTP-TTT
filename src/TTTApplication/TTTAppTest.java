package TTTApplication;

import javaTTT.GameGUI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TTTAppTest {

  TTTApp app;

  @Before
  public void setUp() {
    app = new TTTApp();
    app.game = new GameGUI();
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


}
