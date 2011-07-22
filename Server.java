import java.net.*;
import java.io.*;

public class Server {

  ServerSocket serverSocket;
  Socket clientSocket = null;

  public Server() {
    this(8080);
  }

  public Server(int _port) {
    serverSocket(_port);
    clientSocket();
  }

  private void serverSocket(int _port) {
    try {
      serverSocket = new ServerSocket(_port);
    }
    catch(IOException e) {
      System.out.println("Could not listen to port: " + _port);
      System.exit(-1);
    }
  }

  private void clientSocket() {
    try {
      clientSocket = serverSocket.accept();
      System.out.println("Connection received on port:" + serverSocket.getLocalPort() );
    }
    catch(IOException e) {
      System.out.println("Could not connect to server at port: " + serverSocket.getLocalPort() );
      System.exit(-1);
    }
  }

  public void closeSockets() {
    try{
      clientSocket.close();
      serverSocket.close();
    }
    catch(IOException e) {
      System.out.println("Could not close connection to port: " + serverSocket.getLocalPort() );
    }
  }

}
