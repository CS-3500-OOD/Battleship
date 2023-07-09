package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;

/**
 * An interface for a game Board
 */
public interface Board {

  /**
   * @return String represenation of this Board
   */
  public String[][] getBoard();


  public boolean hitShip(Coord sentShot);

  /**
   * @return whether all boats on this Board have been sunk
   */
  public boolean allSunk();

  /**
   * @return this Board's list of ships
   */
  public ArrayList<Ship> getShips();

  /**
   * @return the number of shots the Player with this Board can throw
   */
  public int numShots();

  /**
   * @param missed shots on this Board
   *               Adds the list to this Board's list of missed shots
   */
  public void unsuccessfulHits(ArrayList<Coord> missed);

  /**
   * @return the String representation of this board as it should be displayed in the terminal
   */
  @Override
  public String toString();

  /**
   * @param shotsThatHitShip adds this list to the Board's list of hit shots
   */
  public void successfulHits(List<Coord> shotsThatHitShip);

  /**
   * Appropriately sets this Boards 2d string array field
   */
  public void toStringArr();

  public int getHeight();

  public int getWidth();

}

