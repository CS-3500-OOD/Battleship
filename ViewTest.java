package cs3500.pa03.view;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa03.model.Coord;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.OpponentBoard;
import cs3500.pa03.model.PlayerBoard;
import cs3500.pa03.model.ShipType;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

class ViewTest {
  private String output;
  private Appendable appendable;
  private View view;
  private PlayerBoard playerBoard;
  private OpponentBoard opponentBoard;

  @Test
  public void testStartGame() throws IOException {
    String input = "5 6\n6 5\n16 6\n6 16\n10 10\n 1 2 1 1\n"
        + "0 0\n1 1\n2 2\n3 3\n4 4\n5 5\n6 6\n7 7\n8 8\n9 9\n";
    StringReader stringReader = new StringReader(input);
    String output = "Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width below:\n"
        + "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game must be in the range [6, 15].\n"
        + " Try again\n"
        + "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game must be in the range [6, 15].\n"
        + " Try again\n"
        + "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game must be in the range [6, 15].\n"
        + " Try again\n"
        + "Uh Oh! You've entered invalid dimensions.\n"
        + "Please remember that the height and width of the game must be in the range [6, 15].\n"
        + " Try again\n";
    StringBuilder actualOutput = new StringBuilder();
    View view =
        new View(stringReader, actualOutput);
    view.startGame();
    assertEquals(output, actualOutput.toString());
    assertEquals(10, view.getHeight());
    assertEquals(10, view.getWidth());
    assertEquals(10, view.maxFleet());

    output = output + "Please enter your fleet in the order\n"
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + 10 + ".\n"
        + "Your fleet must of one of each ship type\n";
    HashMap<ShipType, Integer> fleet = new HashMap<ShipType, Integer>();
    fleet.put(ShipType.CARRIER, 1);
    fleet.put(ShipType.BATTLESHIP, 2);
    fleet.put(ShipType.DESTROYER, 1);
    fleet.put(ShipType.SUBMARINE, 1);
    assertEquals(fleet, view.fleetSelection());
    assertEquals(output, actualOutput.toString());

    ArrayList<Coord> sentShots = new ArrayList<Coord>();
    sentShots.add(new Coord(0, 0));
    sentShots.add(new Coord(1, 1));
    sentShots.add(new Coord(2, 2));
    sentShots.add(new Coord(3, 3));
    sentShots.add(new Coord(4, 4));
    sentShots.add(new Coord(5, 5));
    sentShots.add(new Coord(6, 6));
    sentShots.add(new Coord(7, 7));
    sentShots.add(new Coord(8, 8));
    sentShots.add(new Coord(9, 9));
    assertEquals(sentShots, view.takeShots(10));
    output = output + "Top left corner is origin (0, 0).\nPlease enter 10 shots:\n";
    assertEquals(output, actualOutput.toString());
  }

  @Test
  public void testMaxFleet() throws IOException {
    String input = "7 6\n";
    StringReader stringReader = new StringReader(input);
    StringBuilder actualOutput = new StringBuilder();
    View view = new View(stringReader, actualOutput);
    view.startGame();
    assertEquals(6, view.maxFleet());
  }

  @Test
  public void testEndGame() throws IOException {
    StringBuilder actualOutput = new StringBuilder();
    View view = new View(new StringReader(""), actualOutput);
    String output = "Game Over. You tied.";
    view.endGame(GameResult.DRAW);
    assertEquals(output, actualOutput.toString());

    StringBuilder actualOutput2 = new StringBuilder();
    View view2 = new View(new StringReader(""), actualOutput2);
    String output2 = "Game Over. You lost.";
    view2.endGame(GameResult.LOSS);
    assertEquals(output2, actualOutput2.toString());

    StringBuilder actualOutput3 = new StringBuilder();
    View view3 = new View(new StringReader(""), actualOutput3);
    String output3 = "Game Over. You won!";
    view3.endGame(GameResult.WIN);
    assertEquals(output3, actualOutput3.toString());

  }

  @Test
  public void testEnterName() throws IOException {
    String input = "\nplayer one\n";
    StringReader stringReader = new StringReader(input);
    this.output = "Please enter your name\n";
    this.appendable = new StringBuilder();
    this.view = new View(stringReader, this.appendable);
    assertEquals("player one", view.getPlayerName());
    assertEquals(this.output, this.appendable.toString());
  }

  @Test
  public void testDisplayYourBoard() throws IOException {
    this.playerBoard = new PlayerBoard(6, 6, 1, 1, 1, 3, new Random(1));
    String[] row1 = {"S", "S", "S", "S", "0", "0"};
    String[] row2 = {"S", "0", "0", "0", "0", "0"};
    String[] row3 = {"S", "0", "0", "S", "S", "S"};
    String[] row4 = {"S", "S", "S", "S", "S", "S"};
    String[] row5 = {"0", "0", "S", "S", "S", "0"};
    String[] row6 = {"S", "S", "S", "S", "S", "S"};
    String[][] boardArr = new String[][] {row1, row2, row3, row4, row5, row6};
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < boardArr.length; row += 1) {
      for (int col = 0; col < boardArr[row].length; col += 1) {
        sb.append(boardArr[row][col]);
      }
      sb.append("\n");
    }
    sb.append("\n");
    this.output = "Your Board:\n" + sb;
    this.appendable = new StringBuilder();
    this.view = new View(new StringReader(""), this.appendable);
    this.view.displayPlayerBoard(this.playerBoard.toString());
    assertEquals(this.output, this.appendable.toString());
  }

  @Test
  public void testOpponentBoard() throws IOException {

    this.opponentBoard = new OpponentBoard(6, 6, 1, 1, 1, 3, new Random(1));

    String[] row1 = {"0", "0", "0", "0", "0", "0"};


    String[][] boardArr = new String[][] {row1, row1, row1, row1, row1, row1};

    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < boardArr.length; row += 1) {
      for (int col = 0; col < boardArr[row].length; col += 1) {
        sb.append(boardArr[row][col]);
      }
      sb.append("\n");
    }
    sb.append("\n");
    this.output = "Opponent Board:\n" + sb;
    this.appendable = new StringBuilder();
    this.view = new View(new StringReader(""), this.appendable);
    this.view.displayOpponentBoard(opponentBoard.toString());
    assertEquals(this.output, this.appendable.toString());
  }

}