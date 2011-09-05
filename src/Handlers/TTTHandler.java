package Handlers;

import HTTPServer.Database;
import models.GameGUI;
import models.GameState;
import models.Player;
import views.DrawHTML;
import views.LoadGamesPage;
import views.PlayerNamesPage;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;

public class TTTHandler extends Handler {

  public static synchronized BufferedReader computerVsComputerGame(String request) throws Exception {
    GameGUI game = new GameGUI(request);
    int id = Database.add(game);
    return drawBoard(id, game);
  }

  public static synchronized BufferedReader playerNamesForm(String request) throws Exception {
    return new BufferedReader(new StringReader(PlayerNamesPage.draw(request)));
  }

  public static synchronized BufferedReader newGameWithHuman(String request) throws Exception {
    String[] names = parseNameRequest(request);
    return setPlayerNames(names);
  }

  public static synchronized BufferedReader rematch(String request) throws Exception {
    GameGUI game = (GameGUI) Database.table().get(getID(request));
    game.reset();
    game.takeTurn(null);
    return drawBoard(getID(request), game);
  }

  public static synchronized BufferedReader loadGamesPage(String request) throws Exception {
    removeCompletedGames();
    return new BufferedReader(new StringReader(LoadGamesPage.draw()));
  }

  public static synchronized BufferedReader loadGame(String request) throws Exception {
    GameGUI game = (GameGUI) Database.table().get(parseLoadGameRequest(request));
    return drawBoard(parseLoadGameRequest(request), game);
  }

  private static synchronized BufferedReader setPlayerNames(String[] names) throws Exception {
    GameGUI game = new GameGUI(gameRequest(names));
    int gameId = Database.add(game);
    Player[] players = {game.player1, game.player2};
    for(int i = 0; i < players.length; i++){
      if(names[i] != "")
        players[i].setName(names[i]);
    }
    return drawBoard(gameId, game);
  }

  private static synchronized BufferedReader drawBoard(int gameId, GameGUI game) throws Exception {
    return new BufferedReader(new StringReader(DrawHTML.draw(gameId, game)));
  }

  private static synchronized void removeCompletedGames() {
    Iterator<Map.Entry<Integer, GameGUI>> it = Database.table().entrySet().iterator();
    while(it.hasNext()){
      Map.Entry<Integer, GameGUI> entry = it.next();
      if(GameState.finished(entry.getValue().board))
        Database.table().remove(entry.getKey());
    }
  }

  private static synchronized String gameRequest(String[] playerNames) {
    String request = "/";
    for(int i = 0; i < playerNames.length; i++){
      if(playerNames[i] == "")
        request += "Computer";
      else
        request += "Human";
      if(i == 0)
        request += "Vs";
    }
    return request;
  }

  private static synchronized String[] parseNameRequest(String request) {
    String[] names = {"", ""};
    for(int i = 0; i < names.length; i++){
      String startingPoint = "player" + (i+1) + "Name=";
      int startIndex = request.indexOf(startingPoint);
      int endIndex = request.indexOf("&");
      if(endIndex < startIndex)
        endIndex = request.length();
      if(startIndex != -1){
        if(endIndex != -1)
          names[i] = request.substring(startIndex + startingPoint.length(), endIndex);
        else
          names[i] = request.substring(startIndex + startingPoint.length());
      }
    }
    return names;
  }

  private static synchronized int parseLoadGameRequest(String request) {
    String id = request.substring(request.indexOf("=") + 1);
    return Integer.parseInt(id);
  }

}
