package javaTTT;

import java.io.InputStream;
import java.util.Scanner;

public class GameCL {

  Player player1;
  Player player2;
  MovesCL moves;
  static final int PLAYER_1_VALUE = 1;
  static final int PLAYER_2_VALUE = -1;

  public GameCL() {
    moves = new MovesCL();
  }

  public void createPlayers() {
    String[] names = playerNames();
    player1 = new HumanPlayerCL(PLAYER_1_VALUE, names[0]);
    player2 = new HumanPlayerCL(PLAYER_2_VALUE, names[1]);
  }

  private String[] playerNames() {
    String[] names = new String[2];
    for(int i = 0; i < names.length; i++)
      names[i] = promptForPlayerName(System.in, i + 1);
    return names;
  }

  public String promptForPlayerName(InputStream stream, int i) {
    Scanner scanner = new Scanner(stream);
    System.out.print("Enter a name for Player " + i + ": ");
    return scanner.next();
  }

  public void play() {
    System.out.println(DrawCL.draw(moves.board));
    while( !GameState.finished(moves.board) ) {
      takeTurn(player1);
      if( !GameState.finished(moves.board) ){
        takeTurn(player2);
      }
    }
    gameOverMessage();
  }

  private void gameOverMessage() {
    Player[] players = {player1, player2};
    if(GameState.hasWinner(moves.board)){
      for(Player player : players){
        if(GameState.winner(moves.board) == player.playerValue())
          System.out.println("Congratulations to " + player.playerName() + ". You win!");
      }
    }
    else
      System.out.println("Cat's Game. You both lose :( ");
  }

  private void takeTurn(Player player) {
    moves.makeMove(player);
    System.out.println(DrawCL.draw(moves.board));
  }

  public static void main(String args[]) {
    GameCL game = new GameCL();
    game.createPlayers();
    game.play();
  }
}
