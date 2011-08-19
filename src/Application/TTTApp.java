package Application;

import models.GameGUI;
import models.MachinePlayer;
import views.DrawHTML;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TTTApp extends Application {

  final String viewsRoot = "/Users/unclebob/Projects/HTTP_TTT/src/views/";
  public GameGUI game;

  public synchronized String serverResponse(String[] request) throws Exception {
    BufferedReader content = handleRequests(request);
    String output = "";
    String line = content.readLine();
    while(line != null) {
      output += line + "\n";
      line = content.readLine();
    }
    return output;
  }

  public synchronized String status(String URI) {
    String[] validURIs = {"/", validBoardURIs(), "/HumanVsHuman", "/HumanVsComputer", "/ComputerVsHuman",
                          "/ComputerVsComputer", "/ComputerMove", "/home.css", "/game.css", "/notFound.css",
                          "/machinePlayer.js", "/jquery.js"};
    for(String validURI : validURIs){
      if(URI.matches(validURI))
        return OK;
    }
    return NOT_FOUND;
  }
  
  private synchronized BufferedReader handleRequests(String[] request) throws Exception {

    if(request[0].equals("GET")){
      if(request[1].equals("/"))
        return home();
      else if(validGameTypeURI(request[1]))
        return newGame(request[1]);
      else if(request[1].matches(validBoardURIs()))
        return humanMove(request[1]);
      else if(request[1].equals("/ComputerMove"))
        return computerMove();
      else if(request[1].endsWith(".css"))
        return stylesheets(request[1]);
      else if(request[1].endsWith(".js"))
        return javascripts(request[1]);
    }
    return notFound();
  }

  private String validBoardURIs() {
    int maxBoardCellIndex;
    String pattern = "";
    if(game != null){
      maxBoardCellIndex = game.board.dimension - 1;
      pattern = "/board\\?row=[0-" + maxBoardCellIndex + "]&column=[0-" + maxBoardCellIndex + "]";
    }
    return pattern;
  }

  private boolean validGameTypeURI(String URI) {
    String[] validGameTypes = {"/HumanVsHuman", "/HumanVsComputer", "/ComputerVsHuman", "/ComputerVsComputer"};
    for(String gameType : validGameTypes){
      if(gameType.equals(URI))
        return true;
    }
    return false;
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

  private BufferedReader home() throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "home.html")));
  }

  private BufferedReader newGame(String request) {
    game = new GameGUI(request);
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private BufferedReader humanMove(String request) {
    game.takeTurn(parseMoveRequest(request));
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private BufferedReader computerMove() {
    if(game.player1 instanceof MachinePlayer && game.turn == game.player1.playerValue)
      game.takeTurn(game.player1.move(game.board));
    else if(game.player2 instanceof MachinePlayer && game.turn == game.player2.playerValue)
      game.takeTurn(game.player2.move(game.board));
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private BufferedReader notFound() throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "404NotFound.html")));
  }

  private BufferedReader stylesheets(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "stylesheets" + request)));
  }

  private BufferedReader javascripts(String request) throws Exception {
    return new BufferedReader(new FileReader(new File(viewsRoot + "javascripts" + request)));
  }

}
