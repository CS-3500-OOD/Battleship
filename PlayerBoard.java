package cs3500.pa03.model;

import java.util.Random;

/**
 * The Board of a Player of this Game
 */
public class PlayerBoard extends AbstractBoard {

  public PlayerBoard(int height, int width, int numCarrier, int numBattleship, int numDestroyer,
                     int numSubmarine, Random random) {
    super(height, width, numCarrier, numBattleship, numDestroyer, numSubmarine, random);
  }

  /**
   * Appropriately sets the 2d string representation of this board. Does not display ship locations
   */
  public void toStringArr() {
    for (int row = 0; row < this.board.length; row += 1) {
      for (int col = 0; col < this.board[row].length; col += 1) {
        Coord coord = new Coord(col, row);
        if (this.hitShots.contains(coord)) {
          this.board[row][col] = "H";
        } else if (this.missedShots.contains(coord)) {
          this.board[row][col] = "M";
        } else if (this.shipCoords.contains(coord)) {
          this.board[row][col] = "S";
        } else {
          this.board[row][col] = "0";
        }
      }
    }
  }

}
