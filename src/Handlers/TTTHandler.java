package Handlers;

import models.GameGUI;
import models.MachinePlayer;
import models.Player;
import views.DrawHTML;

import java.io.*;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTTHandler extends Handler {

  final String viewsRoot = "src/views/";
  public GameGUI game;

  public synchronized BufferedReader execute(String request) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
    String line = br.readLine();
    while(line != null){
      String[] route = line.split("\\s*->\\s*");
      if(request.matches(route[0])){
        Method method = this.getClass().getMethod(route[1], String.class);
        return (BufferedReader) method.invoke(this, request);
      }
      line = br.readLine();
    }
    return notFound(request);
  }

  public synchronized BufferedReader home(String request) throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "home.html")));
  }

  public synchronized BufferedReader newGame(String request) {
    game = new GameGUI(request);
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  public synchronized BufferedReader humanMove(String request) throws Exception {
    if(game == null)
      return home(request);
    else{
      game.takeTurn(parseMoveRequest(request));
      return new BufferedReader(new StringReader(DrawHTML.draw(game)));
    }
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

}
