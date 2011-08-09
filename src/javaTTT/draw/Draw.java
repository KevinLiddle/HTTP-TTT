package javaTTT.draw;

import javaTTT.Board;

public abstract class Draw {

  public static String marker(Board board, int row, int column){
    int value = board.cellValueAt(new int[] {row, column});
    if(value == 1)
      return "X";
    else if(value == -1)
      return "O";
    else return " ";
  }
}
