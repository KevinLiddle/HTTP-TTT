package HTTPServer;

import TTTApplication.Application;
import TTTApplication.TTTApp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketCommunication implements ConnectionServer {

  DataOutputStream os;
  Application app = new TTTApp();

  public synchronized void serve(Socket connection) throws Exception {
    String[] request = request(connection);
    String output = "HTTP/1.0 " + app.status(request[1]) + "\n" +
                  "Content-Type: text/html\n" +
                  "\n";
    os = new DataOutputStream(connection.getOutputStream());
    os.writeBytes(output + app.serverResponse(request));
  }

  public synchronized void close(Socket connection) throws Exception {
    connection.close();
  }

  private synchronized String[] request(Socket connection) throws Exception {
    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
    BufferedReader br = new BufferedReader(isr);
    return br.readLine().split(" ");
  }


}
