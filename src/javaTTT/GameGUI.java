package javaTTT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GameGUI extends Game {

  public String gameType;
  public MovesGUI moves;

  public GameGUI() {
    moves = new MovesGUI();
    gameType = "HumanVsHuman";
    createPlayers();
  }

  public synchronized void takeTurn(int[] move) {
    if(moves.turn == player1.playerValue){
      player1.setMove(move);
      moves.makeMove(player1);
    }
    else if(moves.turn == player2.playerValue){
      player2.setMove(move);
      moves.makeMove(player2);
    }
  }

  public synchronized void createPlayers() {
    player1 = new HumanPlayerGUI(PLAYER_1_VALUE, "Player 1");
    player2 = new HumanPlayerGUI(PLAYER_2_VALUE, "Player 2");
  }

  public synchronized int[] parseMoveRequest(String request) {
    int[] move = new int[2];
    Pattern pattern = Pattern.compile("\\d");
    Matcher matcher = pattern.matcher(request);

    int i = 0;
    while(matcher.find()){
      move[i] = Integer.parseInt(matcher.group());
      i++;
    }
    return move;
  }
}
