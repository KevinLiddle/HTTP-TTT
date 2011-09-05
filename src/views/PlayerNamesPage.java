package views;

public class PlayerNamesPage {

  public static String draw(String request) {
    String output = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"home.css\" type=\"text/css\" />" +
      "<script src=\"formValidation.js\" type=\"text/javascript\"></script>" +
      "</head><body>\n" +
      "<h2>Player Names</h2>\n" +
      "<form name=\"playerName\" action=\"setName\" method=\"get\" onsubmit=\"return validateForm()\">\n";
    int playerNumber = 1;
    String[] players = request.substring(1).split("Vs");
    for(String player : players){
      if(player.equals("Human")){
        output += "Player " + playerNumber + ": <input type=\"text\" name=\"player" + playerNumber + "Name\" />\n";
      }
      playerNumber++;
    }
    output += "<input type=\"submit\" value=\"Submit\" />\n" +
              "</form></body></html>";
    return output;
  }
}
