
import model.Coord;
import model.GameResult;
import model.ShipType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * The View for this game
 */
public class View {
  private Scanner sc;
  private Appendable appendable;
  private int height;
  private int width;

  /**
   * @param readable reads user input
   * @param appendable outputs to user
   */
  public View(Readable readable, Appendable appendable) {
    this.sc = new Scanner(readable);
    this.appendable = appendable;
    this.height = 0;
    this.width = 0;
  }

  /**
   * welcomes the user to the game
   *
   * @throws IOException if there is an issue appending to the appendable
   */
  public void startGame() throws IOException {
    this.appendable.append("Hello! Welcome to the OOD BattleSalvo Game!\n"
        + "Please enter a valid height and width below:\n");
    int y = sc.nextInt();
    int x = sc.nextInt();
    while (x < 6 || x > 15 || y < 6 || y > 15) {
      this.appendable.append("Uh Oh! You've entered invalid dimensions.\n"
          + "Please remember that the height and width of the game must be in the range [6, 15].\n"
          + " Try again\n");
      y = sc.nextInt();
      x = sc.nextInt();
    }
    this.height = y;
    this.width = x;
  }

  /**
   * @return the height of the view board
   */
  public int getHeight() {
    return this.height;
  }

  /**
   * @return the width of the view board
   */
  public int getWidth() {
    return this.width;
  }

  /**
   * @return max fleet size of both Players
   */
  public int maxFleet() {
    if (this.width < this.height) {
      return this.width;
    } else {
      return this.height;
    }
  }


  /**
   * Instructs user to enter their fleet specifications
   *
   * @throws IOException if there is an error appending to appendable
   */
  public HashMap<ShipType, Integer> fleetSelection() throws IOException {
    this.appendable.append("Please enter your fleet in the order\n"
        + "[Carrier, Battleship, Destroyer, Submarine].\n"
        + "Remember, your fleet may not exceed size " + this.maxFleet() + ".\n"
        + "Your fleet must of one of each ship type\n");
    HashMap<ShipType, Integer> fleet = new HashMap<ShipType, Integer>();
    int numCarrier = sc.nextInt();
    int numBattleship = sc.nextInt();
    int numDestroyer = sc.nextInt();
    int numSubmarine = sc.nextInt();
    fleet.put(ShipType.CARRIER, numCarrier);
    fleet.put(ShipType.BATTLESHIP, numBattleship);
    fleet.put(ShipType.DESTROYER, numDestroyer);
    fleet.put(ShipType.SUBMARINE, numSubmarine);
    return fleet;
  }

  /**
   * @param maxShots the player can take
   * @return a list of shot coordinates
   * @throws IOException if there is an error appending to the appendable
   */
  public ArrayList<Coord> takeShots(int maxShots) throws IOException {
    this.appendable.append("Top left corner is origin (0, 0).\nPlease enter " + maxShots
        + " shots:\n");
    ArrayList<Coord> newShots = new ArrayList<Coord>();
    for (int i = 0; i < maxShots; i += 1) {
      int x = sc.nextInt();
      int y = sc.nextInt();
      Coord coord = new Coord(x, y);
      newShots.add(coord);
    }
    return newShots;
  }


  /**
   * @param board to be displayed
   * @throws IOException if there is an error appending to the appendable
   */
  public void displayOpponentBoard(String board) throws IOException {
    this.appendable.append("Opponent Board:\n");
    this.appendable.append(board);
  }

  /**
   * @param board manual player board to be displayed
   * @throws IOException if there is an error appending to the appendable
   */
  public void displayPlayerBoard(String board) throws IOException {
    this.appendable.append("Your Board:\n");
    this.appendable.append(board);
  }

  /**
   * @param gameResult end result of the game
   * @throws IOException if there is an error appending to the appendable
   */
  public void endGame(GameResult gameResult) throws IOException {
    if (gameResult == GameResult.DRAW) {
      this.appendable.append("Game Over. You tied.");
    } else if (gameResult == GameResult.LOSS) {
      this.appendable.append("Game Over. You lost.");
    } else {
      this.appendable.append("Game Over. You won!");
    }
  }

}
