package HTTPServer;

import Application.Application;

import java.io.*;
import java.net.Socket;

public class SocketCommunication implements ConnectionServer {

  DataOutputStream os;
  private Application app;

  public SocketCommunication(Application _app) {
    app = _app;
  }

  public synchronized void serve(Socket connection) throws Exception {
    String[] request = request(connection.getInputStream());
    String output = "HTTP/1.0 " + app.status(request[1]) + "\n" +
                  "Content-Type: "+ contentType(request[1]) +"\n" +
                  "\n";
    os = new DataOutputStream(connection.getOutputStream());
    output += app.serverResponse(request);
    os.writeBytes(output);
  }

  public synchronized void close(Socket connection) throws Exception {
    connection.close();
  }

  private synchronized String contentType(String URI) {
    if(URI.endsWith(".css"))
      return "text/css";
    else if(URI.endsWith(".js"))
      return "text/javascript";
    else
      return "text/html";
  }

  private synchronized String[] request(InputStream inputStream) throws Exception {
    InputStreamReader isr = new InputStreamReader(inputStream);
    BufferedReader br = new BufferedReader(isr);
    String line = br.readLine();
    return line.split(" ");
  }


}
