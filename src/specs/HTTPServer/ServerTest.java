package specs.HTTPServer;

import HTTPServer.ConnectionServer;
import HTTPServer.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class ServerTest {
  private int serverPort = 9999;
  private Server server;
  private TestServer testServer;

  @Before
  public void setUp() throws Exception {
    testServer = new TestServer();
    server = new Server(serverPort, testServer);
  }

  @After
  public void tearDown() {
    server.closeServerSocket();
  }

  @Test
  public void testDefaultPort() {
    assertEquals(9999, server.serverSocket.getLocalPort());
  }

  @Test
  public void givenStartedServer_ConnectionsShouldBeZeroAtFirst() throws Exception {
    server.start();
    Thread.sleep(10);
    assertEquals(0, server.getConnectionCount());
  }


  @Test
  public void givenStartedServer_ClientCanConnect() throws Exception {
    Socket clientSocket = new Socket();
    server.start();
    connectClient(clientSocket);
    assertEquals(1, server.getConnectionCount());
  }


  @Test
  public void givenStartedServer_ClientCanConnectTwice() throws Exception {
    server.start();
    Socket clientSocket = new Socket();
    connectClient(clientSocket);
    Socket clientSocket2 = new Socket();
    connectClient(clientSocket2);
    assertEquals(2, server.getConnectionCount());
    clientSocket2.close();
    clientSocket.close();
  }

  @Test
  public void givenAConnectedServer_canSendString() throws Exception {
    Socket clientSocket = new Socket();
    server.start();
    connectClient(clientSocket);

    OutputStream outputStream = clientSocket.getOutputStream();
    PrintStream ps = new PrintStream(outputStream);
    ps.println("hello");
    assertEquals("hello", testServer.getLastMessage());
  }

  @Test
  public void givenAConnectedServer_canSendMultipleStrings() throws Exception {
    server.start();
    String[] messages = {"hello", "goodbye"};
    for(String message : messages){
      Socket clientSocket = new Socket();
      connectClient(clientSocket);
      OutputStream outputStream = clientSocket.getOutputStream();
      PrintStream ps = new PrintStream(outputStream);
      ps.println(message);
      assertEquals(message, testServer.getLastMessage());
    }
  }


  private void connectClient(Socket clientSocket) throws Exception {
    InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
    clientSocket.connect(serverAddress);
    waitForConnection();
  }


  private void waitForConnection() throws InterruptedException {
    Thread.sleep(10);
  }


  private class TestServer implements ConnectionServer {

    private String lastMessage;

    public synchronized void serve(Socket connection) throws Exception {
      BufferedReader br;
      try {
        InputStreamReader isr = new InputStreamReader(connection.getInputStream());
        br = new BufferedReader(isr);
        lastMessage = br.readLine();
        close(connection);
      } catch (Exception e) {
        System.out.println("connection server error");
      }
    }

    public synchronized void close(Socket connection) throws Exception {
      connection.close();
    }

    public synchronized String getLastMessage() throws Exception {
      return lastMessage;
    }
  }
}
