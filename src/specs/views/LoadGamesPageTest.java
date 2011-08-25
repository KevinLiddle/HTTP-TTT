package specs.views;

import HTTPServer.Database;
import models.GameGUI;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import views.LoadGamesPage;

import java.util.Date;

import static junit.framework.Assert.assertEquals;

public class LoadGamesPageTest {

  GameGUI game;

  @Before
  public void setUp() {
    game = new GameGUI("/HumanVsHuman");
    game.player1.name = "Kevin";
    game.player2.name = "Nivek";
  }




  @After
  public void tearDown() {
    Database.table().remove(Database.table().remove(game));
  }

  @Test
  public void drawDisplaysTheLoadGamesPageWithOneGame() {
    game.savedAt = new Date();
    Database.table().add(game);
    String page = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"home.css\" type=\"text/css\" />" +
      "</head><body>\n" +
      "<h2>Saved Games:</h2>\n" +
      "<ul>\n" +
      "<li><a href=\"/loadGame?id=0\">Kevin vs. Nivek - " + game.savedAt + "</a></li>" +
      "</ul><br /><a id=\"home\" class=\"link\" href=\"/\">Home</a></body></html>";
    assertEquals(page, LoadGamesPage.draw(game));
  }


}
