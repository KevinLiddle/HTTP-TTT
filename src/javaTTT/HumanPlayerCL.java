package javaTTT;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HumanPlayerCL extends Player {

  public HumanPlayerCL(int playerValue){
    super(playerValue);
  }

  public int[] move(Board board) {
    System.out.print(name + ", please enter a move (i.e.: 1,2): ");
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

}
