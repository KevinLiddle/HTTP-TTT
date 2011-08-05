package TTTServer;

import javaTTT.*;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SocketCommunication implements ConnectionServer {

  String[] request;
  DataOutputStream os;
  final String viewsRoot = "/Users/unclebob/Projects/HTTP_TTT/src/views/";
  final String OK = "200 OK";
  final String NOTFOUND = "404 Not Found";
  MovesGUI moves;
  Player player1;
  Player player2;
  String gameType;

  public synchronized void serve(Socket connection) throws Exception {
    request(connection);
    BufferedReader content = new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "home.html")));
    os = new DataOutputStream(connection.getOutputStream());

    if(request[0].equals("GET")){
      if(request[1].equals("/")){
        writeOutput(OK, content);
      }
      else if(request[1].equals("/HumanVsHuman")){
        setupGame("HumanVsHuman");
        content = new BufferedReader(new StringReader(DrawHTML.draw(moves.board)));
        writeOutput(OK, content);
      }
      else if(request[1].startsWith("/board") && moves != null){
        int[] move = parseMoveRequest(request[1]);
        if(moves.turn == player1.playerValue){
          player1.setMove(move);
          moves.makeMove(player1);
        } else if(moves.turn == player2.playerValue){
          player2.setMove(move);
          moves.makeMove(player2);
        }
        String output = DrawHTML.draw(moves.board);
        if(GameState.finished(moves.board)){
          output += gameOverMessage();
        }
        content = new BufferedReader(new StringReader(output));
        writeOutput(OK, content);
      }
      else {
        content = new BufferedReader(new InputStreamReader(new FileInputStream(viewsRoot + "404NotFound.html")));
        writeOutput(NOTFOUND, content);
      }
    }
  }

  public synchronized void close(Socket connection) throws Exception {
    connection.close();
  }

  private synchronized void setupGame(String gameType) {
    this.gameType = gameType;
    moves = new MovesGUI();
    player1 = new HumanPlayerGUI(1, "Player 1");
    player2 = new HumanPlayerGUI(-1, "Player 2");
  }

  private synchronized String gameOverMessage() {
    String message = "<br />";
    if(GameState.winner(moves.board) == 1)
      message += "Player 1 Wins!";
    else if(GameState.winner(moves.board) == -1)
      message += "Player 2 Wins!";
    else
      message += "Cat's Game...";
    message += "<br />" +
               "<a href=\"/\">Home</a>" +
               "<br />" +
               "<a href=\"/" + gameType + "\">Rematch</a>";
    return message;
  }

  private synchronized int[] parseMoveRequest(String request) {
    int[] move = new int[2];
    Pattern pattern = Pattern.compile("\\d");
    Matcher matcher = pattern.matcher(request);

    int i = 0;
    while(matcher.find()){
      move[i] = Integer.parseInt(matcher.group());
      i++;
    }
    return move;
  }

  private synchronized void request(Socket connection) throws Exception {
    InputStreamReader isr = new InputStreamReader(connection.getInputStream());
    BufferedReader br = new BufferedReader(isr);
    request = br.readLine().split(" ");
  }

  private void writeOutput(String status, BufferedReader content) throws Exception {
    os.writeBytes("HTTP/1.0 " + status + "\n" +
                  "Content-Type: text/html\n" +
                  "\n");
    String line = content.readLine();
    while(line != null) {
      os.writeBytes(line + "\n");
      line = content.readLine();
    }
  }
}
