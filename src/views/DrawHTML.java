package views;

import models.GameGUI;
import models.GameState;
import models.MachinePlayer;
import models.Player;

public class DrawHTML extends Draw {
  
  public static String draw(GameGUI game) {
    String drawnBoard = "<html><head>" +
      "<title>Tic Tac Toe</title>" +
      "<link rel=\"stylesheet\" href=\"game.css\" type=\"text/css\" />" +
      "</head><body><table border=\"2\" bordercolor=\"#32A5F5\">\n";
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
      drawnBoard += "<br /><a id=\"button\" href=\"/ComputerMove\">Computer Move</a>";
    }
    return drawnBoard + "<br /><a id=\"link\" class=\"home\" href=\"/\">Home</a></body></html>";
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
               "<a id=\"link\" href=\"" + game.gameType + "\">Rematch</a>";
    return message;
  }

  private static String markerHTML(GameGUI game, int row, int column) {
    String value = marker(game.board, row, column);
    if(value != " ")
      return "<p>" + value + "</p>";
    else if(GameState.finished(game.board) || computerPlayerTurn(game))
      return "<p id=\"board_links\">-</p>";
    else
      return "<a id=\"board_links\" href=\"/board?row=" + row + "&column=" + column + "\">----</a>";
  }

  private static boolean displayComputerMoveLink(GameGUI game) {
    return (game.player1 instanceof MachinePlayer && game.turn == game.player1.playerValue)
        || (game.player2 instanceof MachinePlayer && game.turn == game.player2.playerValue);
  }

  private static boolean computerPlayerTurn(GameGUI game) {
    for(Player player : new Player[] {game.player1, game.player2}){
      if(player.playerValue == game.turn && player instanceof MachinePlayer)
        return true;
    }
    return false;
  }
}