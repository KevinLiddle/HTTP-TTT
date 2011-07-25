package httpTTT;

import java.io.IOException;
import java.net.Socket;

public interface ConnectionServer {
  void serve(Socket connection) throws Exception;
}
