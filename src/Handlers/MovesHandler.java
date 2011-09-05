package Handlers;

import HTTPServer.Database;
import models.GameGUI;
import models.MachinePlayer;
import models.Player;
import views.DrawHTML;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MovesHandler extends Handler {

  public static synchronized BufferedReader humanMove(String request) throws Exception {
    int gameId = getID(request);
    int[] move = parseMoveRequest(request.replaceAll("/\\d+", ""));
    GameGUI game = (GameGUI) Database.table().get(gameId);
    if(game == null)
      return HomeHandler.home(request);
    else{
      game.takeTurn(new int[] {move[0], move[1]});
      return drawBoard(gameId, game);
    }
  }

  public static synchronized BufferedReader computerMove(String request) throws Exception {
    GameGUI game = (GameGUI) Database.table().get(getID(request));
    if(game != null){
      Player player = game.findPlayerByTurn();
      if(player instanceof MachinePlayer){
        game.takeTurn(player.move(game.board));
        return drawBoard(getID(request), game);
      }
    }
    return HomeHandler.home(null);
  }

  private static synchronized BufferedReader drawBoard(int gameId, GameGUI game) throws Exception {
    return new BufferedReader(new StringReader(DrawHTML.draw(gameId, game)));
  }

  private static synchronized int[] parseMoveRequest(String request) {
    int[] move = new int[3];
    Pattern pattern = Pattern.compile("\\d+");
    Matcher matcher = pattern.matcher(request);

    int i = 0;
    while(matcher.find()){
      move[i] = Integer.parseInt(matcher.group());
      i++;
    }
    return move;
  }
}
