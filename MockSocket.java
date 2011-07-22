import junit.framework.Assert;

public class MockSocket {

  int port;
  boolean closed = false;

  public MockSocket() {}

  public MockSocket(int _port) {
    port = _port;
  }

  public int getLocalPort() {
    return port;
  }

  public void close() {
    if( closed )
      Assert.fail("Trying to close a closed socket.");
    else
      closed = true;
  }

  public boolean isClosed() {
    return closed;
  }

}
