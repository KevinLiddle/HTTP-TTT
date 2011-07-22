import junit.framework.TestCase;

public class ServerTest extends TestCase{

  MockServer server;

  public void setUp() {
    server = new MockServer();
  }

  public void tearDown() {
    server.closeSockets();
  }

  public void testDefaultPort() {
    assertEquals( 8080, server.serverSocket.getLocalPort() );
  }

  public void testSpecifiedPort() {
    MockServer server3333 = new MockServer(3333);
    assertEquals( 3333, server3333.serverSocket.getLocalPort() );
    server3333.closeSockets();
    assertTrue( server3333.serverSocket.isClosed() );
    assertTrue( server3333.clientSocket.isClosed() );
  }

  public void testClientSocketPort() {
    assertEquals( server.serverSocket.getLocalPort() , server.clientSocket.getLocalPort() );
  }
}
