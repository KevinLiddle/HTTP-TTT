package views;

import models.GameGUI;
import models.GameState;
import models.MachinePlayer;

import java.util.Arrays;

public class DrawHTML extends Draw {
  
  public static String draw(GameGUI game) {
    String drawnBoard = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"icon\" href=\"favicon.ico\" type=\"image/icon\" />" +
      "<link rel=\"stylesheet\" href=\"game.css\" type=\"text/css\" />" +
      "<script src=\"jquery.js\" type=\"text/javascript\"></script>" +
      "<script src=\"machinePlayer.js\" type=\"text/javascript\"></script>" +
      "</head><body>" +
      "<h2>" + game.player1.name + "(X) vs. " + game.player2.name + "(O)</h2>" +
      "<table border=\"2\" bordercolor=\"#32A5F5\">\n";
    for (int i = 0; i < game.board.dimension; i++) {
      drawnBoard += "<tr>\n";
      for (int j = 0; j < game.board.dimension; j++) {
        drawnBoard += "<td>" + markerHTML(game, i, j) + "</td>\n";
      }
      drawnBoard += "</tr>\n";
    }
    drawnBoard += "</table>";

    if(GameState.finished(game.board))
      drawnBoard += gameOverMessage(game);
    else if(displayComputerMoveLink(game)){
      drawnBoard += "<br /><a class=\"button\" href=\"/ComputerMove\">Computer Moves</a>";
    }
    return drawnBoard + "<br /><a id=\"home\" class=\"link\" href=\"/\">Home</a></body></html>";
  }

  public static String gameOverMessage(GameGUI game) {
    String message = "<br />";
    if(GameState.winner(game.board) == 1)
      message += "<p>" + game.player1.name + " Wins!</p>";
    else if(GameState.winner(game.board) == -1)
      message += "<p>" + game.player2.name + " Wins!</p>";
    else
      message += "<p>" + "Cat's Game...</p>";
    message += "<br />" +
               "<a class=\"link\" href=\"/rematch\">Rematch</a>";
    return message;
  }

  private static String markerHTML(GameGUI game, int row, int column) {
    String value = marker(game.board, row, column);
    if(value != " "){
      return "<p class=\"" + moveType(game, row, column) + "marker\">" + value + "</p>";
    }
    else if(GameState.finished(game.board))
      return "<p class=\"board_links\">-</p>";
    else
      return "<a class=\"board_links\" href=\"/board?row=" + row + "&column=" + column + "\">----</a>";
  }

  private static String moveType(GameGUI game, int row, int column) {
    if(Arrays.equals(game.lastMove, new int[]{row, column}) && !GameState.finished(game.board))
      return "last_computer_";
    return "";
  }

  private static boolean displayComputerMoveLink(GameGUI game) {
    return (game.player1 instanceof MachinePlayer && game.turn == game.player1.playerValue)
        || (game.player2 instanceof MachinePlayer && game.turn == game.player2.playerValue);
  }
}