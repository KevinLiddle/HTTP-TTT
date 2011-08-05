package javaTTT;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayerCL extends Player {

  int[] move;

  public HumanPlayerCL(int playerValue, String playerName){
    super(playerValue, playerName);
  }

  public int[] move() {
    System.out.print(playerName + ", please enter a move (i.e.: 1,2): ");
    setMove(promptForMove(System.in));
    return this.move;
  }

  public int[] promptForMove(InputStream stream) {
    int[] move = new int[2];
    Scanner scanner = new Scanner(stream).useDelimiter("[ abcdefghijklmnopqrstuvwxyz\n,./!@#$%^&*()-+=|;:'\"<>?]+");
    int i = 0;
    while( i < 2 && scanner.hasNext() ){
      try{
        move[i] = scanner.nextInt();
        i++;
      }
      catch(InputMismatchException e) {
        scanner.next();
      }
    }
    return move;
  }

  public void setMove(int[] move) {
    this.move = move;
  }

}
