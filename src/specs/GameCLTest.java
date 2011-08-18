package specs;

import models.GameCL;
import models.HumanPlayerCL;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static junit.framework.Assert.assertEquals;

public class GameCLTest {

  GameCL game;

  @Before
  public void setUp() {
    game = new GameCL();
    game.player1 = new HumanPlayerCL(1);
    game.player2 = new HumanPlayerCL(-1);
  }

  @Test
  public void movesSetOnBoard() {
    int[] cell = {2, 1};
    takeTurnStub(game.player1);

    assertEquals(1, game.board.cellValueAt(cell));
  }

  @Test
  public void movesAreValidated() {
    int[] move1 = {2,1};
    int[] move2 = {1,1};
    takeTurnStub(game.player1);
    takeTurnStub(game.player2);

    assertEquals(1, game.board.cellValueAt(move1));
    assertEquals(-1, game.board.cellValueAt(move2));
  }


  private void takeTurnStub(Player player) {
    HumanPlayerCL humanPlayer = (HumanPlayerCL) player;
    String[] input = {"2\n1", "3, 0", "-1,2", "1\n1"};
    InputStream stream = new ByteArrayInputStream(input[0].getBytes());
    int[] move = humanPlayer.promptForMove(stream);
    int i = 1;
    while( game.invalid(move) ){
      stream = new ByteArrayInputStream(input[i].getBytes());
      move = humanPlayer.promptForMove(stream);
      i++;
    }
    game.board.setCellValue( move, humanPlayer.playerValue);
  }

}
