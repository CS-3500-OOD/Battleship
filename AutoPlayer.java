package cs3500.pa03.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * An AI Player
 */
public class AutoPlayer extends AbstractPlayer {

  public AutoPlayer(String name, Random random) {
    super(name, random);
  }

  /**
   * @param height         the height of the board, range: [6, 15] inclusive
   * @param width          the width of the board, range: [6, 15] inclusive
   * @param specifications a map of ship type to the number of occurrences each ship should
   *                       appear on the board
   * @return The Ships on this Board
   */
  public ArrayList<Ship> setup(int height, int width, Map<ShipType, Integer> specifications) {
    this.board = new OpponentBoard(height, width, specifications.get(ShipType.CARRIER),
        specifications.get(ShipType.BATTLESHIP), specifications.get(ShipType.DESTROYER),
        specifications.get(ShipType.SUBMARINE), this.random);
    return this.board.getShips();
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots() {
    ArrayList<Coord> newShots = new ArrayList<Coord>();
    int y = this.random.nextInt(this.board.getWidth());
    int x = this.random.nextInt(this.board.getHeight());
    Coord coord = new Coord(x, y);
    for (int shots = 0; shots < this.board.numShots(); shots += 1) {
      while (this.shotsFired.contains(coord)) {
        x = this.random.nextInt(this.board.getWidth());
        y = this.random.nextInt(this.board.getHeight());
        coord = new Coord(x, y);
      }
      newShots.add(coord);
      this.shotsFired.add(coord);
    }
    return newShots;
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
    return damage;
  }

}
