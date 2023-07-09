package cs3500.pa03.model;

import java.util.ArrayList;

/**
 * represents a manual players stats for the current round (dependency injection)
 */
public class CurrentRoundStats {
  private ArrayList<Coord> curShots;
  private int numManualShots;

  public CurrentRoundStats() {
    this.curShots = new ArrayList<Coord>();
    this.numManualShots = 1;
  }

  /**
   * @return the number of shots player currently has
   */
  public int getNumManualShots() {
    return this.numManualShots;
  }

  /**
   * @param shots to be added to the manual players current list of shots
   */
  public void addShots(ArrayList<Coord> shots) {
    this.curShots.addAll(shots);
  }

  /**
   * removes everything from the current list of shots
   */
  public void removeAllShots() {
    this.curShots.removeAll(this.curShots);
  }

  /**
   * @param numShots the number of shots the player can currently take this round
   */
  public void setNumManualShots(int numShots) {
    this.numManualShots = numShots;
  }

  /**
   * @return the number of shots the player can currently take this round
   */
  public ArrayList<Coord> getShots() {
    return this.curShots;
  }

}
