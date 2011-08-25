package views;

import HTTPServer.Database;
import models.GameGUI;

public class LoadGamesPage {

  public static String draw(GameGUI game) {
    String output = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"home.css\" type=\"text/css\" />" +
      "</head><body>\n" +
      "<h2>Saved Games:</h2>\n" +
      "<ul>\n";
    for(int i = 0; i < Database.table().size(); i++){
      output += "<li><a href=\"/loadGame?id=" + i + "\">" + game.player1.name + " vs. " + game.player2.name + " - " + game.savedAt + "</a></li>";
    }
    output += "</ul>" +
      "<br /><a id=\"home\" class=\"link\" href=\"/\">Home</a>" +
      "</body></html>";
    return output;
  }
}
