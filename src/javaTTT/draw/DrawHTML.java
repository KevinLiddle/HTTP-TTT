package javaTTT.draw;

import javaTTT.Board;
import javaTTT.GameGUI;
import javaTTT.GameState;

public class DrawHTML extends Draw {
  
  public static String draw(GameGUI game) {
    String drawnBoard = "<table>\n";
    for (int i = 0; i < game.moves.board.dimension; i++) {
      drawnBoard += "<tr>\n";
      for (int j = 0; j < game.moves.board.dimension; j++) {
        drawnBoard += "<td>" + markerHTML(game.moves.board, i, j) + "</td>\n";
      }
      drawnBoard += "</tr>\n";
    }
    drawnBoard += "</table>";

    if(GameState.finished(game.moves.board))
      drawnBoard += gameOverMessage(game);
    return drawnBoard;
  }

  private static String markerHTML(Board board, int row, int column) {
    String value = marker(board, row, column);
    if(value != " ")
      return value;
    else
      return "<a href=\"/board?row=" + row + "&column=" + column + "\">-</a>";
  }

  public static String gameOverMessage(GameGUI game) {
    String message = "<br />";
    if(GameState.winner(game.moves.board) == 1)
      message += "Player 1 Wins!";
    else if(GameState.winner(game.moves.board) == -1)
      message += "Player 2 Wins!";
    else
      message += "Cat's Game...";
    message += "<br />" +
               "<a href=\"/\">Home</a>" +
               "<br />" +
               "<a href=\"/" + game.gameType + "\">Rematch</a>";
    return message;
  }
}