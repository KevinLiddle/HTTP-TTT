package views;

import models.GameGUI;
import models.HumanPlayerGUI;
import models.Player;

public class PlayerNamesPage {

  public static String draw(GameGUI game) {
    String output = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"home.css\" type=\"text/css\" />" +
      "<script src=\"formValidation.js\" type=\"text/javascript\"></script>" +
      "</head><body>\n" +
      "<h2>Player Names</h2>\n" +
      "<form name=\"playerName\" action=\"setName\" method=\"get\" onsubmit=\"return validateForm()\">\n";
    Player[] players = new Player[] {game.player1, game.player2};
    int playerNumber = 1;
    for(Player player : players){
      if(player instanceof HumanPlayerGUI){
        output += "Player " + playerNumber + ": <input type=\"text\" name=\"player" + playerNumber + "Name\" />\n";
      } 
      playerNumber++;
    }
    output += "<input type=\"submit\" value=\"Submit\" />\n" +
              "</form>";
    return output;
  }
}
