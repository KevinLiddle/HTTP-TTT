package TTTApplication;

import javaTTT.GameGUI;
import javaTTT.MachinePlayer;
import javaTTT.draw.DrawHTML;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

public class TTTApp extends Application {

  final String viewsRoot = "/Users/unclebob/Projects/HTTP_TTT/src/views/";
  GameGUI game;

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
    String[] validURIs = {"/", "/HumanVsHuman", validBoardURIs()};
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

  private BufferedReader home() throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "home.html")));
  }

  private BufferedReader newGame(String request) {
    game = new GameGUI(request);
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private BufferedReader humanMove(String request) {
    game.takeTurn(game.parseMoveRequest(request));
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private BufferedReader computerMove() {
    if(game.player1 instanceof MachinePlayer)
      game.takeTurn(game.player1.move(game.board));
    else if(game.player2 instanceof MachinePlayer)
      game.takeTurn(game.player2.move(game.board));
    return new BufferedReader(new StringReader(DrawHTML.draw(game)));
  }

  private BufferedReader notFound() throws Exception {
    return new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "404NotFound.html")));
  }

}
