package specs;

import models.HumanPlayerCL;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HumanPlayerCLTest {

  static HumanPlayerCL player;

  @Before
  public void setUp(){
    player = new HumanPlayerCL(1);
  }

  @Test
  public void testHumanPlayerConstructor() {
    assertTrue( player != null );
    assertEquals( 1, player.playerValue );
  }

  private static void inputTest(String input) {
    int[] expectedMove = {2,1};
    InputStream stream = new ByteArrayInputStream(input.getBytes());
    int[] result = player.promptForMove(stream);
    for(int i = 0; i < expectedMove.length; i++)
      assertEquals( expectedMove[i], result[i] );
  }

  @Test
  public void testPromptForMove() {
    String input = "2 1";
    inputTest(input);
  }

  @Test
  public void testPromptForMoveWithDifferentInputFormat() {
    String input = "2\n 1";
    inputTest(input);
  }

  @Test
  public void testPromptForMoveWithCommaSeparator() {
    String input = "2, 1";
    inputTest(input);
  }

  @Test
  public void testPromptForMoveWithLetters() {
    String input = "x2efswerg1regwb";
    inputTest(input);
  }

}