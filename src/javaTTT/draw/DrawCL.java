package javaTTT.draw;

import javaTTT.GameCL;
import javaTTT.GameState;
import javaTTT.Player;

public class DrawCL extends Draw {

  public static String draw(GameCL game) {
    String drawnBoard = "";
    for(int i = 0; i < game.moves.board.dimension; i++){
      for(int j = 0; j < game.moves.board.dimension; j++){
        drawnBoard += "[" + marker(game.moves.board, i, j) + "]";
      }
      drawnBoard += "\n";
    }

    if(GameState.finished(game.moves.board))
      drawnBoard += gameOverMessage(game);
    return drawnBoard;
  }

  public static String gameOverMessage(GameCL game) {
    Player[] players = {game.player1, game.player2};
    if(GameState.hasWinner(game.moves.board)){
      for(Player player : players){
        if(GameState.winner(game.moves.board) == player.playerValue())
          return "Congratulations to " + player.playerName() + ". You win!";
      }
    }
    return "Cat's Game. You both lose :( ";
  }
}
