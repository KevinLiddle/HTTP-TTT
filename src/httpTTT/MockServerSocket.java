package httpTTT;

public class MockServerSocket extends MockSocket{

  public MockServerSocket() {
    this(8080);
  }

  public MockServerSocket(int _port) {
    port = _port;
  }

  public MockSocket accept() {
    return new MockSocket(this.port);
  }
}
