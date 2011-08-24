package specs.HTTPServer;

import HTTPServer.ApplicationResponder;
import Handlers.TTTHandler;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ApplicationResponderTest {

  ApplicationResponder app;

  @Before
  public void setUp() throws Exception {
    app = new ApplicationResponder(new TTTHandler());
    app.serverResponse(new String[] {"GET", "/HumanVsComputer", "HTTP/1.0"});
  }

  @Test
  public void validBoardURIsMatchValidBoardMove() throws Exception {
    assertEquals(app.OK, app.status("/board?row=0&column=0"));
  }

  @Test
  public void validBoardURIsDontMatchInvalidURI() throws Exception {
    assertEquals(app.NOT_FOUND, app.status("/board?row=0&column=0rgkhr"));
  }

  @Test
  public void validBoardURIsDontMatchInvalidMove() throws Exception {
    assertEquals(app.NOT_FOUND, app.status("/board?row=0&column=3"));
  }

  @Test
  public void computerCannotMoveIfNotItsTurn() throws Exception {
    assertEquals(app.OK, app.status("/ComputerMove"));
    assertTrue(app.serverResponse(new String[]{"GET", "/ComputerMove", "HTTP/1.0"}).contains("Please select a game type:"));
  }


}
