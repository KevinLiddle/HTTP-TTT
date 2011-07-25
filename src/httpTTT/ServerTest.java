package httpTTT;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
  public void testSpecifiedPort() {
    MockServer server3333 = new MockServer(3333);
    assertEquals(3333, server3333.serverSocket.getLocalPort());
    server3333.closeSockets();
    assertTrue(server3333.serverSocket.isClosed());
    assertTrue(server3333.clientSocket.isClosed());
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
    Socket clientSocket = new Socket();
    connectClient(clientSocket);
    Socket clientSocket2 = new Socket();
    connectClient(clientSocket2);

    OutputStream outputStream = clientSocket.getOutputStream();
    PrintStream ps = new PrintStream(outputStream);
    ps.println("hello");
    assertEquals("hello", testServer.getLastMessage());
    OutputStream outputStream2 = clientSocket2.getOutputStream();
    PrintStream ps2 = new PrintStream(outputStream2);
    ps2.println("goodbye");
    assertEquals("goodbye", testServer.getLastMessage());
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
        java.io.InputStream inputStream = connection.getInputStream();
        InputStreamReader isr = new InputStreamReader(inputStream);
        br = new BufferedReader(isr);
        lastMessage = br.readLine();
      } catch (Exception e) {
        System.out.println("connection server error");
        e.printStackTrace();
      }
    }

    public synchronized String getLastMessage() throws Exception {
      return lastMessage;
    }
  }
}
