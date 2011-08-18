package views;

import models.GameCL;
import models.GameState;
import models.Player;

public class DrawCL extends Draw {

  public static String draw(GameCL game) {
    String drawnBoard = "";
    for(int i = 0; i < game.board.dimension; i++){
      for(int j = 0; j < game.board.dimension; j++){
        drawnBoard += "[" + marker(game.board, i, j) + "]";
      }
      drawnBoard += "\n";
    }

    if(GameState.finished(game.board))
      drawnBoard += gameOverMessage(game);
    return drawnBoard;
  }

  public static String gameOverMessage(GameCL game) {
    Player[] players = {game.player1, game.player2};
    if(GameState.hasWinner(game.board)){
      for(Player player : players){
        if(GameState.winner(game.board) == player.playerValue)
          return "Congratulations to " + player.name + ". You win!";
      }
    }
    return "Cat's Game. You both lose :( ";
  }
}
