package javaTTT;

import javaTTT.draw.DrawCL;

import java.io.InputStream;
import java.util.Scanner;

public class GameCL extends Game {
  
  public GameCL() {
    board = new Board();
  }

  public static void main(String args[]) {
    GameCL game = new GameCL();
    game.createPlayers();
    game.play();
  }

  public void createPlayers() {
    while(player1 == null && player2 == null){
      int gameType = gameType(System.in);
      if(gameType == 1){
        player1 = new HumanPlayerCL(PLAYER_1_VALUE);
        player2 = new HumanPlayerCL(PLAYER_2_VALUE);
      }
      else if(gameType == 2){
        player1 = new HumanPlayerCL(PLAYER_1_VALUE);
        player2 = new MachinePlayer(PLAYER_2_VALUE);
      }
      else if(gameType == 3){
        player1 = new MachinePlayer(PLAYER_1_VALUE);
        player2 = new HumanPlayerCL(PLAYER_2_VALUE);
      }
      else if(gameType == 4){
        player1 = new MachinePlayer(PLAYER_1_VALUE);
        player2 = new MachinePlayer(PLAYER_2_VALUE);
      }
    }
    player1.setName(playerName(player1, 1));
    player2.setName(playerName(player2, 2));
  }

  public void play() {
    System.out.println(DrawCL.draw(this));
    while( !GameState.finished(board) ) {
      takeTurn(player1);
      if( !GameState.finished(board) )
        takeTurn(player2);
    }
  }

  protected void takeTurn(Player player) {
    int[] move = player.move(board);
    while(invalid(move)){
      move = player.move(board);
    }
    board.setCellValue( move, player.playerValue );
    System.out.println(DrawCL.draw(this));
  }

  private String playerName(Player player, int playerNumber) {
    String name;
    if(player instanceof HumanPlayerCL){
      name = promptForPlayerName(System.in, playerNumber);
    }else{
      name = "TicTacTobot300" + (playerNumber-1);
    }
    return name;
  }

  private String promptForPlayerName(InputStream stream, int playerNumber) {
    Scanner scanner = new Scanner(stream);
    System.out.print("Enter a name for Player " + playerNumber + ": ");
    return scanner.next();
  }

  private int gameType(InputStream stream) {
    Scanner scanner = new Scanner(stream);
    System.out.println("PLEASE ENTER A GAME TYPE");
    System.out.println("1 - 'Human Vs Human'");
    System.out.println("2 - 'Human Vs Computer'");
    System.out.println("3 - 'Computer Vs Human'");
    System.out.println("4 - 'Computer Vs Computer'");
    System.out.print("Your Choice: ");
    return scanner.nextInt();
  }
}
