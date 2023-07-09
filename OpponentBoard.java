package cs3500.pa03.model;

import java.util.Random;

/**
 * Represents the Board of the Player's Opponent
 */
public class OpponentBoard extends AbstractBoard {

  public OpponentBoard(int height, int width, int numCarrier, int numBattleship, int numDestroyer,
                       int numSubmarine, Random rand) {
    super(height, width, numCarrier, numBattleship, numDestroyer, numSubmarine, rand);
  }


  /**
   * Appropriately sets the 2d string representation of this board. Does not display ship locations
   */
  public void toStringArr() {
    for (int row = 0; row < this.board.length; row += 1) {
      for (int col = 0; col < board[row].length; col += 1) {
        Coord coord = new Coord(col, row);
        if (this.hitShots.contains(coord)) {
          this.board[row][col] = "H";
        } else if (this.missedShots.contains(coord)) {
          this.board[row][col] = "M";
        } else {
          this.board[row][col] = "0";
        }
      }
    }
  }


}
