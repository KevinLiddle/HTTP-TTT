package Handlers;

import HTTPServer.Database;
import models.GameGUI;
import models.MachinePlayer;
import models.Player;
import views.DrawHTML;
import views.LoadGamesPage;
import views.PlayerNamesPage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTTHandler extends Handler {

  final String viewsRoot = "src/views/";
  public GameGUI game;

  public synchronized BufferedReader home(String request) throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "home.html")));
  }

  public synchronized BufferedReader humanMove(String request) throws Exception {
    if(game == null)
      return home(request);
    else{
      game.takeTurn(parseMoveRequest(request));
      return new BufferedReader(new StringReader(DrawHTML.draw(game)));
    }
  }

  public synchronized BufferedReader computerVsComputerGame(String request) throws Exception {
    game = new GameGUI(request);
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  public synchronized BufferedReader computerMove(String request) throws Exception {
    Player player = game.findPlayerByTurn();
    if(player instanceof MachinePlayer){
      game.takeTurn(player.move(game.board));
      return new BufferedReader(new StringReader(DrawHTML.draw(game)));
    }
    else
      return home(request);
  }

  public synchronized BufferedReader playerNamesForm(String request) throws Exception {
    game = new GameGUI(request);
    return new BufferedReader(new StringReader(PlayerNamesPage.draw(game)));
  }

  public synchronized BufferedReader setPlayerNames(String request) throws Exception {
    String[] names = parseNameRequest(request);
    return newGameWithHuman(names);
  }

  public synchronized BufferedReader rematch(String request) throws Exception {
    game.reset();
    game.takeTurn(null);
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  public synchronized BufferedReader saveGame(String request) throws Exception {
    game.savedAt = new Date();
    Database.table().add(game);
    return home(request);
  }

  public synchronized BufferedReader loadGamesPage(String request) throws Exception {
    return new BufferedReader(new StringReader(LoadGamesPage.draw()));
  }

  public synchronized BufferedReader loadGame(String request) throws Exception {
    game = (GameGUI) Database.table().get(parseLoadGameRequest(request));
    Database.table().remove(game);
    return newGameWithHuman(new String[] {game.player1.name, game.player2.name});
  }

  private synchronized BufferedReader newGameWithHuman(String[] names) {
    Player[] players = {game.player1, game.player2};
    for(int i = 0; i < players.length; i++){
      if(names[i] != "")
        players[i].setName(names[i]);
    }
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private synchronized int[] parseMoveRequest(String request) {
    int[] move = new int[2];
    Pattern pattern = Pattern.compile("\\d");
    Matcher matcher = pattern.matcher(request);

    int i = 0;
    while(matcher.find()){
      move[i] = Integer.parseInt(matcher.group());
      i++;
    }
    return move;
  }

  private synchronized String[] parseNameRequest(String request) {
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

  private synchronized int parseLoadGameRequest(String request) {
    String id = request.substring(request.indexOf("=") + 1);
    return Integer.parseInt(id);
  }

}
