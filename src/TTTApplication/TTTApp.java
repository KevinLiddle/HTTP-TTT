package TTTApplication;

import javaTTT.GameGUI;
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

  private BufferedReader handleRequests(String[] request) throws Exception {
    BufferedReader content = null;

    if(request[0].equals("GET")){
      if(request[1].equals("/")){
        content = new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "home.html")));
      }
      else if(request[1].equals("/HumanVsHuman")){
        game = new GameGUI();
        content = new BufferedReader(new StringReader(DrawHTML.draw(game)));
      }
      else if(request[1].matches(validBoardURIs())){
        game.takeTurn(game.parseMoveRequest(request[1]));
        content = new BufferedReader(new StringReader(DrawHTML.draw(game)));
      }
      else {
        content = new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "404NotFound.html")));
      }
    }
    return content;
  }

  public String status(String URI) {
    String[] validURIs = {"/", "/HumanVsHuman", validBoardURIs()};
    for(String validURI : validURIs){
      if(URI.matches(validURI))
        return OK;
    }
    return NOT_FOUND;
  }

  private String validBoardURIs() {
    int maxBoardCellIndex;
    String pattern = "";
    if(game != null){
      maxBoardCellIndex = game.moves.board.dimension - 1;
      pattern = "/board\\?row=[0-" + maxBoardCellIndex + "]&column=[0-" + maxBoardCellIndex + "]";
    }
    return pattern;
  }
}
