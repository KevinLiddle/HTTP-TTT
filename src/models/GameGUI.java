package models;

public class GameGUI extends Game {

  public String gameType;
  public String[] validGameTypes = {"/HumanVsHuman", "/HumanVsComputer", "/ComputerVsHuman", "/ComputerVsComputer"};
  public int turn;
  public int[] lastMove;

  public GameGUI(String _gameType) {
    board = new Board();
    gameType = _gameType;
    turn = 1;
    createPlayers();
  }

  public synchronized void takeTurn(int[] move) {
    Player player = findPlayerByTurn();
    player.setMove(move);
    if(valid(player.move(board))){
      if(player instanceof MachinePlayer)
        lastMove = player.move(board);
      playTurn(player.move(board), player.playerValue);
      if((player = findPlayerByTurn()) instanceof MachinePlayer){
        lastMove = player.move(board);
        playTurn(lastMove, player.playerValue);
      }
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
        takeTurn(null);
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

  private Player findPlayerByTurn() {
    if(turn == player1.playerValue)
      return player1;
    else
      return player2;
  }

  private void playTurn(int[] move, int playerValue) {
    board.setCellValue(move, playerValue);
    turn *= -1;
  }
}
