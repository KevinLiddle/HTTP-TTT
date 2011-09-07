package HTTPServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Server implements Runnable {

  public ServerSocket serverSocket;
  private int connections = 0;
  private ConnectionServer connectionServer;
  private boolean running = false;
  private LinkedList<Thread> threads = new LinkedList<Thread>();

  public static void main(String args[]) throws Exception {
    SocketCommunication client = new SocketCommunication(new ApplicationResponder());
    Server server = new Server(3000, client);
    server.start();
  }

  public Server(int _port, ConnectionServer connectionServer) throws Exception {
    this.connectionServer = connectionServer;
    try {
      serverSocket = new ServerSocket(_port);
    } catch (IOException e) {
      System.out.println("Could not listen to port: " + _port);
      System.exit(-1);
    }
  }

  public void closeServerSocket() {
    try {
      running = false;
      serverSocket.close();
    } catch (IOException e) {
      System.out.println("Could not close connection to port: " + serverSocket.getLocalPort());
    }
  }

  public void start() {
    new Thread(this).start();
  }

  public void run() {
    try {
      running = true;
      while (running) {
        Socket clientSocket = serverSocket.accept();
        serveConnection(clientSocket);
        connections++;
      }
    } catch (SocketException e) {
      waitForClose();
    } catch (Exception e) {
      System.out.println("Could not connect to server.");
      System.exit(-1);
    }
  }

  private void serveConnection(Socket clientSocket) {
    threads.add(new Thread(new ConnectionServerDriver(clientSocket)));
    threads.getLast().start();
  }

  private void waitForClose() {
    try {
      Thread.sleep(10); // let close finish
    } catch (InterruptedException e1) {
      System.out.println("Thread cannot sleep.");
    }
  }

  public int getConnectionCount() {
    return connections;
  }

  private class ConnectionServerDriver implements Runnable {
    private Socket clientSocket;

    public ConnectionServerDriver(Socket clientSocket) {
      this.clientSocket = clientSocket;
    }
    public void run() {
      try {
        connectionServer.serve(clientSocket);
        connectionServer.close(clientSocket);
      } catch (Exception e) {
        Thread lastThread = threads.removeLast();
        lastThread = null;
        e.printStackTrace();
      }
    }
  }
}
