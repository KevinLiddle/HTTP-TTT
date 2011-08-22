package specs;

import Handlers.Handler;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.lang.reflect.Method;

import static junit.framework.Assert.assertEquals;

public class TTTHandlerTest {

  MockHandler handler;

  @Before
  public void setUp() {
    handler = new MockHandler();
  }

  @Test
  public void executeCallsHomeForAHomeRequest() throws Exception {
    assertEquals("home", handler.execute("/").readLine());
  }

  @Test
  public void executeCallsNewGameForValidNewGameRequest() throws Exception {
    assertEquals("newGame", handler.execute("/HumanVsComputer").readLine());
  }

  @Test
  public void executeCallsNewGameForAnotherValidGameRequest() throws Exception {
    assertEquals("newGame", handler.execute("/ComputerVsComputer").readLine());
  }


  @Test
  public void executeCallsHumanMoveForValidBoardRequest() throws Exception {
    assertEquals("humanMove", handler.execute("/board?row=0&column=0").readLine());
  }

  @Test
  public void executeCallsNotFoundForInvalidMoveRequest() throws Exception {
    assertEquals("notFound", handler.execute("/board?row=3&column=0").readLine());
  }

  @Test
  public void executeCallsComputerMoveForValidRequest() throws Exception {
    assertEquals("computerMove", handler.execute("/ComputerMove").readLine());
  }


  @Test
  public void executeCallsNotFoundForRoutesNotFound() throws Exception {
    assertEquals("notFound", handler.execute("/KarateKid").readLine());
  }


  public class MockHandler extends Handler {

    public BufferedReader execute(String request) throws Exception {
      BufferedReader br = new BufferedReader(new FileReader(new File("src/config/routes.txt")));
      String line = br.readLine();
      while(line != null){
        String[] route = line.split("\\s*->\\s*");
        if(request.matches(route[0])){
          Method method = this.getClass().getMethod(route[1], String.class);
          return new BufferedReader(new StringReader((String) method.invoke(this, request)));
        }
        line = br.readLine();
      }
      return new BufferedReader(new StringReader(mockNotFound(request)));
    }

    public String home(String request) {
      return "home";
    }

    public String newGame(String request) {
      return "newGame";
    }

    public String humanMove(String request) throws Exception {
      return "humanMove";
    }

    public String computerMove(String request) {
      return "computerMove";
    }

    public String mockNotFound(String request) {
      return "notFound";
    }

  }

}
