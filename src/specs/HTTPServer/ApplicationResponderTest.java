package specs.HTTPServer;

import HTTPServer.ApplicationResponder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class ApplicationResponderTest {

  ApplicationResponder app;

  @Before
  public void setUp() throws Exception {
    app = new ApplicationResponder();
    app.serverResponse(new String[] {"GET", "/HumanVsComputer", "HTTP/1.0"});
  }

  @Test
  public void validBoardURIsMatchValidBoardMove() throws Exception {
    assertEquals(app.OK, app.status("/0/board?row=0&column=0"));
  }

  @Test
  public void validBoardURIsDontMatchInvalidURI() throws Exception {
    assertEquals(app.NOT_FOUND, app.status("/0/board?row=0&column=0rgkhr"));
  }

  @Test
  public void validBoardURIsDontMatchInvalidMove() throws Exception {
    assertEquals(app.NOT_FOUND, app.status("/0/board?row=0&column=3"));
  }

  @Test
  public void computerCannotMoveIfNotItsTurn() throws Exception {
    assertEquals(app.OK, app.status("/0/ComputerMove"));
    app.serverResponse(new String[]{"GET", "/setName?player1Name=Kevin", "HTTP/1.0"});
    assertTrue(app.serverResponse(new String[]{"GET", "/0/ComputerMove", "HTTP/1.0"}).contains("Please select a game type:"));
  }


}
