package javaTTT;

import javaTTT.draw.DrawCL;

import java.io.InputStream;
import java.util.Scanner;

public class GameCL extends Game {

  public MovesCL moves;
  
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

  private String promptForPlayerName(InputStream stream, int i) {
    Scanner scanner = new Scanner(stream);
    System.out.print("Enter a name for Player " + i + ": ");
    return scanner.next();
  }

  public void play() {
    System.out.println(DrawCL.draw(this));
    while( !GameState.finished(moves.board) ) {
      takeTurn(player1);
      if( !GameState.finished(moves.board) )
        takeTurn(player2);
    }
  }

  private void takeTurn(Player player) {
    moves.makeMove(player);
    System.out.println(DrawCL.draw(this));
  }

  public static void main(String args[]) {
    GameCL game = new GameCL();
    game.createPlayers();
    game.play();
  }
}
