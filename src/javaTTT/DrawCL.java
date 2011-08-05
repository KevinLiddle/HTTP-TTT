package javaTTT;

public class DrawCL extends Draw {

  public static String draw(Board board) {
    String drawnBoard = "";
    for(int i = 0; i < board.dimension; i++){
      for(int j = 0; j < board.dimension; j++){
        drawnBoard += "[" + marker(board, i, j) + "]";
      }
      drawnBoard += "\n";
    }
    return drawnBoard;
  }
}
