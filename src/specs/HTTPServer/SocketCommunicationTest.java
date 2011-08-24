package specs.HTTPServer;

import HTTPServer.ApplicationResponder;
import HTTPServer.Server;
import HTTPServer.SocketCommunication;
import Handlers.TTTHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketCommunicationTest {

  SocketCommunication client;
  Socket socket;
  Server server;
  PrintStream ps;
  BufferedReader br;

  @Before
  public void setUp() throws Exception {
    client = new SocketCommunication(new ApplicationResponder(new TTTHandler()));
    socket = new Socket();
    server = new Server(3000, client);
    server.start();
    connectClient(3000);
    ps = new PrintStream(socket.getOutputStream());
    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  @After
  public void tearDown() throws Exception {
    server.closeServerSocket();
  }

  @Test
  public void serveSendsHTTPResponse() throws Exception {
    ps.println("GET / HTTP/1.0");
    waitToSend();
    assertEquals("HTTP/1.0 200 OK", br.readLine());
  }

  @Test
  public void givenGameNotCreated_CannotAccessBoardURI() throws Exception {
    ps.println("GET /board HTTP/1.0");
    waitToSend();
    assertEquals("HTTP/1.0 404 Not Found", br.readLine());
  }


  private String readResponse() throws Exception {
    String line = br.readLine();
    String response = "";
    while(line != null){
      response += line + "\n";
      line = br.readLine();
    }
    response = response.trim();
    return response;
  }

  private void connectClient(int serverPort) throws Exception {
    InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
    socket.connect(serverAddress);
    waitToSend();
  }

  private void waitToSend() throws Exception {
    Thread.sleep(10);
  }

}
