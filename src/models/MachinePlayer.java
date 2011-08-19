package models;

public class MachinePlayer extends Player {

  public MachinePlayer(int _playerValue) {
    super(_playerValue);
  }

  public int[] move(Board board) {
    getBestMove(board, playerValue, 0);
    return move;
  }

  private double getBestMove(Board board, int turn, int depth) {
    double[] movesScores = new double[board.openSpaces()];
    int[][] possibleMoves = new int[board.openSpaces()][2];

    if(GameState.empty(board)){
      int[] topRightCorner = {0,0};
      setMove(topRightCorner);
      return 0;
    }
    if(GameState.finished(board)){
      return (1.0/depth)*GameState.winner(board);
    }
    int openSpaceIndex = 0;
    for(int i=0; i < board.dimension; i++){
      for(int j=0; j < board.dimension; j++){
        int[] cell = {i,j};
        if(board.cellValueAt(cell) == 0){
          board.setCellValue(cell, turn);
          possibleMoves[openSpaceIndex] = cell;
          movesScores[openSpaceIndex] = getBestMove(board, -1 * turn, depth + 1);
          board.setCellValue(cell, 0);
          openSpaceIndex++;
        }
      }
    }
    setMove(possibleMoves[bestScore(movesScores, turn)]);
    return movesScores[bestScore(movesScores, turn)];
  }

  private int bestScore(double[] movesScores, int turn) {
    int bestScoreIndex = 0;
    for(int i=0; i < movesScores.length; i++){
      if(turn*movesScores[i] > turn*movesScores[bestScoreIndex])
        bestScoreIndex = i;
    }
    return bestScoreIndex;
  }
}