package models;

import static java.lang.Math.abs;

public class GameState {

  static final int LEFT = 0;
  static final int RIGHT = 2;

  public static boolean finished(Board board) {
    return hasWinner(board) || full(board);
  }

  public static boolean full(Board board) {
    for(int i = 0; i < board.dimension; i++){
      for(int j = 0; j < board.dimension; j++){
        int[] cell = {i,j};
        if(board.cellValueAt(cell) == 0)
          return false;
      }
    }
    return true;
  }

  public static boolean hasWinner(Board board) {
    return abs(winner(board)) > 0;
  }

  public static int winner(Board board) {
    return HorizontalWinner(board) | VerticalWinner(board) | DiagonalWinner(board);
  }

  public static int HorizontalWinner(Board board) {
    return NonDiagonalWinner(board, "horizontal");
  }

  public static int VerticalWinner(Board board) {
    return NonDiagonalWinner(board, "vertical");
  }

  private static int NonDiagonalWinner(Board board, String direction) {
    for(int i = 0; i < board.dimension; i++){
      int consecutiveMarkers = 0;
      for(int j = 0; j < board.dimension; j++){
        consecutiveMarkers += cellValueAt( board, i, j, direction );
      }
      if(winningCombination( consecutiveMarkers, board ))
        return consecutiveMarkers/board.dimension;
    }
    return 0;
  }

  private static boolean winningCombination(int consecutiveMarkers, Board board) {
    return abs(consecutiveMarkers) == board.dimension;
  }

  public static int DiagonalWinner(Board board) {
    return DiagonalWinner(board, LEFT) | DiagonalWinner(board, RIGHT);
  }

  private static int DiagonalWinner( Board board, int startingColumn ) {
    int[] cell = { 0, startingColumn };
    int marker = board.cellValueAt( cell );
    int consecutiveMarkers = 0;
    for(int i = 0; i < board.dimension; i++){
      consecutiveMarkers += cellValueAt( board, i, startingColumn, "diagonal");
    }
    return consecutiveMarkers/board.dimension;
  }

  private static int cellValueAt(Board board, int i, int j, String direction) {
    int[] cell = new int[2];
    if(direction == "horizontal"){
      cell[0] = i;
      cell[1] = j;
    }
    else if (direction == "vertical"){
      cell[0] = j;
      cell[1] = i;
    }
    else if (direction == "diagonal"){
      cell[0] = i;
      cell[1] = abs( j - i );
    }
    else
      return 0;
    return board.cellValueAt( cell );
  }

  public static boolean empty(Board board) {
    for(int i=0; i < board.dimension; i++){
      for(int j=0; j < board.dimension; j++){
        if(board.cellValueAt(new int[] {i,j}) != 0)
          return false;
      }
    }
    return true;
  }
}
