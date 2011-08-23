package specs;

import models.GameGUI;
import org.junit.Test;
import views.PlayerNamesPage;

import static junit.framework.Assert.assertEquals;

public class PlayerNamesPageTest {

  GameGUI game;

  @Test
  public void oneNameFieldDisplayedForOneHumanPlayer() {
    game = new GameGUI("/HumanVsComputer");
    assertEquals("<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"home.css\" type=\"text/css\" />" +
      "</head><body>\n" +
      "<h2>Player Names</h2>\n" +
      "<form name=\"playerName\" action=\"setName\" method=\"get\">\n" +
      "Player 1: <input type=\"text\" name=\"player1Name\" />\n" +
      "<input type=\"submit\" value=\"Submit\" />\n" +
      "</form>", PlayerNamesPage.draw(game));
  }

  @Test
  public void twoNameFieldsForTwoHumanPlayers() {
    game = new GameGUI("/HumanVsHuman");
    assertEquals("<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"home.css\" type=\"text/css\" />" +
      "</head><body>\n" +
      "<h2>Player Names</h2>\n" +
      "<form name=\"playerName\" action=\"setName\" method=\"get\">\n" +
      "Player 1: <input type=\"text\" name=\"player1Name\" />\n" +
      "Player 2: <input type=\"text\" name=\"player2Name\" />\n" +
      "<input type=\"submit\" value=\"Submit\" />\n" +
      "</form>", PlayerNamesPage.draw(game));
  }


}
