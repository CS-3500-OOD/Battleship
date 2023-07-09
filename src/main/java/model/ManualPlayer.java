package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A manual (human) player
 */
public class ManualPlayer extends AbstractPlayer {
  private CurrentRoundStats currentRoundStats;

  public ManualPlayer(String name, CurrentRoundStats currentRoundStats, Random random) {
    super(name, random);
    this.currentRoundStats = currentRoundStats;
  }

  /**
   * creates this Player's board according to their fleet specifications
   *
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return the Ships on this Player's Boards
   */
  public ArrayList<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board = new PlayerBoard(height, width, specifications.get(ShipType.CARRIER),
        specifications.get(ShipType.BATTLESHIP),
        specifications.get(ShipType.DESTROYER), specifications.get(ShipType.SUBMARINE),
        this.random);
    this.currentRoundStats.setNumManualShots(this.board.numShots());
    return this.board.getShips();
  }

  /**
   * @param opponentShotsOnBoard the opponent's shots on this player's board
   * @return the list of coordinates that successfully hit this board
   */
  public List<Coord> reportDamage(List<Coord> opponentShotsOnBoard) {
    ArrayList<Coord> damage = new ArrayList<Coord>();
    for (Coord shot : opponentShotsOnBoard) {
      if (this.board.hitShip(shot)) {
        damage.add(shot);
      }
    }
    ArrayList<Coord> missed = new ArrayList<Coord>();
    for (Coord shot : opponentShotsOnBoard) {
      if (!damage.contains(shot)) {
        missed.add(shot);
      }
    }
    this.board.successfulHits(damage);
    this.board.unsuccessfulHits(missed);
    this.currentRoundStats.setNumManualShots(this.board.numShots());
    return damage;
  }

  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots() {
    return this.currentRoundStats.getShots();
  }

  /**
   * @param sentShots list of coordinates this player is shooting
   * @return whether any of the shots are not valid coords on the grid
   */
  public boolean validShots(ArrayList<Coord> sentShots) {

    for (Coord c : sentShots) {
      if (this.shotsFired.contains(c) || c.getX() < 0 || c.getX() >= this.board.getWidth()
          || c.getY() < 0 || c.getY() >= this.board.getHeight()) {
        return false;
      }
    }
    this.shotsFired.addAll(this.currentRoundStats.getShots());
    return true;
  }
}
