package specs.Handlers;

import Handlers.Handler;
import org.junit.Test;

import java.io.BufferedReader;

import static junit.framework.Assert.assertTrue;

public class HomeHandlerTest {

  @Test
  public void executeCallsHomeForAHomeRequest() throws Exception {
    assertTrue(readDocument(Handler.execute("/")).contains("Human vs. Human"));
  }

  private String readDocument(BufferedReader br) throws Exception {
    String line = br.readLine();
    String document = "";
    while(line != null){
      document += line + "\n";
      line = br.readLine();
    }
    return document;
  }
}
