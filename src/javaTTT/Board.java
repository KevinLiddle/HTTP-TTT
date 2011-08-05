package javaTTT;

public class Board {

  public int dimension;
  int[][] cellValues;

  public Board() {
    dimension = 3;
    cellValues = new int[dimension][dimension];
  }

  public Board(int dim){
    dimension = dim;
    cellValues = new int[dimension][dimension];
  }

  public int cellValueAt(int[] cell){
    return cellValues[cell[0]][cell[1]];
  }

  public void setCellValue(int[] cell, int value) {
    cellValues[cell[0]][cell[1]] = value;
  }

  public void clear() {
    for(int i=0; i < dimension; i++){
      for(int j=0; j < dimension; j++){
        int [] cell = {i,j};
        setCellValue(cell, 0);
      }
    }
  }
}
