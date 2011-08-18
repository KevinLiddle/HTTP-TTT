package models;

public class GameGUI extends Game {

  public String gameType;
  public String[] validGameTypes = {"/HumanVsHuman", "/HumanVsComputer", "/ComputerVsHuman", "/ComputerVsComputer"};
  public int turn;

  public GameGUI(String _gameType) {
    board = new Board();
    gameType = _gameType;
    turn = 1;
    createPlayers();
  }

  public synchronized void takeTurn(int[] move) {
    Player player;
    if(turn == player1.playerValue)
      player = player1;
    else
      player = player2;
    player.setMove(move);
    if(valid(player.move(board))){
      board.setCellValue( player.move(board), player.playerValue );
      turn *= -1;
    }
  }

  public synchronized void createPlayers() {
    if(validGameType(gameType)){
      if(gameType.equals("/HumanVsHuman")){
        player1 = new HumanPlayerGUI(PLAYER_1_VALUE);
        player2 = new HumanPlayerGUI(PLAYER_2_VALUE);
      }
      else if(gameType.equals("/HumanVsComputer")){
        player1 = new HumanPlayerGUI(PLAYER_1_VALUE);
        player2 = new MachinePlayer(PLAYER_2_VALUE);
      }
      else if(gameType.equals("/ComputerVsHuman")){
        player1 = new MachinePlayer(PLAYER_1_VALUE);
        player2 = new HumanPlayerGUI(PLAYER_2_VALUE);
      }
      else if(gameType.equals("/ComputerVsComputer")){
        player1 = new MachinePlayer(PLAYER_1_VALUE);
        player2 = new MachinePlayer(PLAYER_2_VALUE);
      }
    }
    player1.setName("Player 1");
    player2.setName("Player 2");
  }

  private boolean valid(int[] move) {
    return !invalid(move);
  }

  private boolean validGameType(String gameType) {
    for(String type : validGameTypes){
      if(gameType.equals(type))
        return true;
    }
    return false;
  }
}
