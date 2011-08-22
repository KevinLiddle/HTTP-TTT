package Application;

import Handlers.TTTHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TTTApp extends Application {

  public TTTApp() {
    handler = new TTTHandler();
  }

  public synchronized String serverResponse(String[] request) throws Exception {
    BufferedReader content = handler.execute(request[1]);
    String output = "";
    String line = content.readLine();
    while(line != null) {
      output += line + "\n";
      line = content.readLine();
    }
    return output;
  }

  public synchronized String status(String URI) throws Exception {
    BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
    String line = br.readLine();
    while(line != null){
      String route = line.split("\\s*->\\s*")[0];
      if(URI.matches(route))
        return OK;
      line = br.readLine();
    }
    return NOT_FOUND;
  }
}
