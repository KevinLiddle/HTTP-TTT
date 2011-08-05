package javaTTT;

public class DrawHTML extends Draw {
  
  public static String draw(Board board) {
    String drawnBoard = "<table>\n";
    for (int i = 0; i < board.dimension; i++) {
      drawnBoard += "<tr>\n";
      for (int j = 0; j < board.dimension; j++) {
        drawnBoard += "<td>" + markerHTML(board, i, j) + "</td>\n";
      }
      drawnBoard += "</tr>\n";
    }
    drawnBoard += "</table>";
    return drawnBoard;
  }

  private static String markerHTML(Board board, int row, int column) {
    String value = marker(board, row, column);
    if(value != " ")
      return value;
    else
      return "<a href=\"/board?row=" + row + "&column=" + column + "\">-</a>";
  }
}