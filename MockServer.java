public class MockServer {

  MockServerSocket serverSocket;
  MockSocket clientSocket = null;
  int port;

  public MockServer() {
    this(8080);
  }

  public MockServer(int _port) {
    serverSocket = new MockServerSocket(_port);
    clientSocket = serverSocket.accept();
  }

  public void closeSockets() {
    serverSocket.close();
    clientSocket.close();
  }
}
