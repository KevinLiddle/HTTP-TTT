package HTTPServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static org.junit.Assert.assertEquals;

public class SocketCommunicationTest {

  SocketCommunication client;
  Socket socket;
  Server server;
  PrintStream ps;
  BufferedReader br;

  @Before
  public void setUp() throws Exception {
    client = new SocketCommunication();
    socket = new Socket();
    server = new Server(3000, client);
    server.start();
    connectClient(3000);
    ps = new PrintStream(socket.getOutputStream());
    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
  }

  @After
  public void tearDown() throws Exception {
    server.closeServerSocket();
  }

  @Test
  public void serveSendsHTTPResponse() throws Exception {
    ps.println("GET / HTTP/1.0");
    waitToSend();
    assertEquals("HTTP/1.0 200 OK", br.readLine());
  }

  @Test
  public void serveDrawsABoardForNewGames() throws Exception {
    ps.println("GET /HumanVsHuman HTTP/1.0");
    waitToSend();
    assertEquals("HTTP/1.0 200 OK\n" +
                 "Content-Type: text/html\n" +
                 "\n" +
                 "<html><head>" +
      "<style>\n" +
      "        body {\n" +
      "            background-color: #282828;\n" +
      "            font-family: \"Century Gothic\";" +
      "            text-align: center;}\n" +
      "        table {" +
      "            margin-left: auto;" +
      "            margin-right: auto;" +
      "            padding-top: 20%;}" +
      "        td {" +
      "            color: #FFFFFF;" +
      "            font-size: 3em;" +
      "            padding-left: 0.7em;" +
      "            padding-right: 0.7em;" +
      "            text-align: center;}" +
      "        #board_links {\n" +
      "            color: #32A5F5;\n" +
      "            text-decoration: none;}\n" +
      "        p {" +
      "            color: #FFFFFF;}" +
      "        #link {" +
      "            color: #32A5F5;\n" +
      "            text-decoration: none;" +
      "            font-size: 1.5em;}" +
      "        #button {" +
      "            color: #463E3F;" +
      "            border: outset;" +
    "              background-color: #E6E6E6;" +
    "              text-decoration: none;}" +
    "          #button:active {" +
    "              background-color: #BDBDBD;" +
    "              border: inset;}" +
      "    </style>" +
      "</head><body><table>\n" +
      "<tr>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=0&column=0\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=0&column=1\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=0&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=1&column=0\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=1&column=1\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=1&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "<tr>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=2&column=0\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=2&column=1\">-</a></td>\n" +
      "<td><a id=\"board_links\" href=\"/board?row=2&column=2\">-</a></td>\n" +
      "</tr>\n" +
      "</table></body></html>", readResponse());
  }

  @Test
  public void givenGameNotCreated_CannotAccessBoardURI() throws Exception {
    ps.println("GET /board HTTP/1.0");
    waitToSend();
    assertEquals("HTTP/1.0 404 Not Found", br.readLine());
  }


  private String readResponse() throws Exception {
    String line = br.readLine();
    String response = "";
    while(line != null){
      response += line + "\n";
      line = br.readLine();
    }
    response = response.trim();
    return response;
  }

  private void connectClient(int serverPort) throws Exception {
    InetSocketAddress serverAddress = new InetSocketAddress(serverPort);
    socket.connect(serverAddress);
    waitToSend();
  }

  private void waitToSend() throws Exception {
    Thread.sleep(10);
  }

}
