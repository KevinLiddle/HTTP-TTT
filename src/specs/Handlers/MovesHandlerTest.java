package specs.Handlers;

import HTTPServer.Database;
import Handlers.Handler;
import models.GameGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;

public class MovesHandlerTest {

  @Before
  public void setUp() {
    Database.table().clear();
  }

  @After
  public void tearDown() {
    Database.table().clear();
  }

  @Test
  public void executeCallsHumanMoveForValidBoardRequest() throws Exception {
    int id = Database.add(new GameGUI("/HumanVsHuman"));
    assertTrue(HandlerTestHelpers.gamePage(Handler.execute("/" + id + "/board?row=0&column=0")));
  }

  @Test
  public void executeCallsNotFoundForInvalidMoveRequest() throws Exception {
    assertTrue(HandlerTestHelpers.notFoundPage(Handler.execute("/board?row=3&column=0")));
  }
}
