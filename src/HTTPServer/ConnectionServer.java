package HTTPServer;

import java.net.Socket;

public interface ConnectionServer {
  void serve(Socket connection) throws Exception;

  void close(Socket clientSocket) throws Exception;
}
