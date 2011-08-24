package specs.HTTPServer;

import HTTPServer.Database;
import models.GameGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DatabaseTest {

  Object obj;

  @Before
  public void setUp(){
    obj = Database.table().add(new GameGUI("/HumanVsHuman"));
  }

  @After
  public void tearDown() {
    Database.table().remove(obj);
  }

  @Test
  public void getInstanceReturnsDatabaseInstance() {
    assertEquals(1, Database.table().size());
    assertTrue(Database.table().get(0) instanceof GameGUI);
  }

}
