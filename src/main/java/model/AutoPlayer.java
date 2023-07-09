package model;

import java.util.*;

/**
 * An AI Player
 */
public class AutoPlayer extends AbstractPlayer {
  ArrayList<Coord> queue;
  int numShots; // represents all the coordinates with ship

  public AutoPlayer(String name, Random random) {
    super(name, random);
    this.numShots = 0;
    this.queue = new ArrayList<Coord>();
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
    this.initQueue();
    return this.board.getShips();
  }


  /**
   * Returns this player's shots on the opponent's board. The number of shots returned should
   * equal the number of ships on this player's board that have not sunk.
   *
   * @return the locations of shots on the opponent's board
   */
  public List<Coord> takeShots() {
    // creates a list for the player's shots
    ArrayList<Coord> newShots = new ArrayList<Coord>();

    int numRemaining = (this.board.getWidth() * this.board.getHeight()) - this.shotsFired.size();
    // will change the number of allowed shots based on the number of spots
    // left on the opponents board
    int numNewShots = Math.min(numRemaining, this.board.numShots());

    // continues to generate shots until the number of shots
    // is the number of ships on the board
    // where algorithm will be located
    for (int shots = 0; shots < numNewShots; shots += 1) {
      Coord coord = queue.get(0);
      newShots.add(coord);
      this.queue.remove(coord);
      this.shotsFired.add(coord);
    }

    // returns a list of randomly generated shots
    return newShots;
  }

  /**
   * generates a list of total possible coords on the current game board's ship
   * and places them into a "queue"
   */
  private void initQueue() {
    // initializing all the possible coordinates on the board
    // and adds them to the queue list
    for (int y = 0; y < this.board.getHeight(); y += 1) {
      for (int x = 0; x < this.board.getWidth(); x += 1) {
        this.queue.add(new Coord(x, y));
      }
    }
    Collections.shuffle(this.queue, this.random);
  }

  /**
   * moves a coordinate higher up the priority queue if a shot has been hit nearby
   *
   * @param coord the coordinate that will be removed
   */
  private void moveToPriority(Coord coord) {
    // coordinate will be removed once its been shot
    this.queue.remove(coord);
    // brings the proceding coordinate higher up the queue
    this.queue.add(0, coord);
  }

  /**
   * checks if the given coordinate is "valid enough" to be placed
   *
   * @param coord the coordinate that will be checked
   * @return boolean to determine whether or not the coordinate is valid
   */
  private boolean validCoord(Coord coord) {
    // checks and ensures that the coordinate is within bounds and
    // also checks if the shot has been shot before
    return coord.getX() >= 0 && coord.getX() < this.board.getWidth() && coord.getY() >= 0
            && coord.getY() < this.board.getHeight() && !shotsFired.contains(coord);
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

  /**
   * Reports to this player what shots in their previous volley returned from takeShots()
   * successfully hit an opponent's ship.
   *
   * @param shotsThatHitOpponentShips the list of shots that successfully hit the opponent's ships
   */
  public void successfulHits(List<Coord> shotsThatHitOpponentShips) {
    this.board.successfulHits(shotsThatHitOpponentShips);

    // for every coordinate that has hit the opponent's ship
    for (Coord coord : shotsThatHitOpponentShips) {
      // goes to the coordinate that is 3 to the right of the original coordinate
      // starts at a unique coordinate
      if (this.validCoord(new Coord(coord.getX() + 1, coord.getY()))) {
        this.moveToPriority(new Coord(coord.getX() + 1, coord.getY()));
      }

      // goes to the coordinate that is 3 to the left of the original coordinate
      // starts at a unique coordinate
      if (this.validCoord(new Coord(coord.getX() - 1, coord.getY()))) {
        this.moveToPriority(new Coord(coord.getX() - 1, coord.getY()));
      }

      // goes to the coordinate that is 3 coordinates above of the original coordinate
      // starts at a unique coordinate
      if (this.validCoord(new Coord(coord.getX(), coord.getY() + 1))) {
        this.moveToPriority(new Coord(coord.getX(), coord.getY() + 1));
      }

      // goes to the coordinate that is 3 coordinates below of the original coordinate
      // starts at a unique coordinate
      if (this.validCoord(new Coord(coord.getX(), coord.getY() - 1))) {
        this.moveToPriority(new Coord(coord.getX(), coord.getY() - 1));
      }
    }
  }

}
