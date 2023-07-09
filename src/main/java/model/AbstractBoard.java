package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Game Board
 */
public abstract class AbstractBoard implements Board {
  String[][] board;
  int height;
  int width;
  ArrayList<Coord> shipCoords;
  ArrayList<Ship> ships;
  ArrayList<Coord> hitShots;
  ArrayList<Coord> missedShots;
  Random rand;

  AbstractBoard(int height, int width, int numCarrier, int numBattleship, int numDestroyer,
                int numSubmarine, Random rand) {
    this.rand = rand;
    this.height = height;
    this.width = width;
    this.shipCoords = new ArrayList<Coord>();
    this.ships = new ArrayList<Ship>();
    this.hitShots = new ArrayList<Coord>();
    this.missedShots = new ArrayList<Coord>();
    this.board = new String[height][width];
    this.placeCarriers(numCarrier);
    this.placeBattleship(numBattleship);
    this.placeDestroyer(numDestroyer);
    this.placeSubmarine(numSubmarine);
    this.toStringArr();
  }

  /**
   * @param numCarrier to be placed
   */
  private void placeCarriers(int numCarrier) {
    for (int i = 0; i < numCarrier; i += 1) {
      int x = this.rand.nextInt(this.width);
      int y = this.rand.nextInt(this.height);
      boolean orientation = this.rand.nextBoolean();
      Placement placement = Placement.HORIZONTAL;
      if (orientation == true) {
        placement = Placement.VERTICAL;
      }
      Coord coord = new Coord(x, y);
      while (!this.canPlaceShip(coord, 6, placement)) {
        x = this.rand.nextInt(this.width);
        y = this.rand.nextInt(this.height);
        coord = new Coord(x, y);
        orientation = this.rand.nextBoolean();
        placement = Placement.HORIZONTAL;
        if (orientation == true) {
          placement = Placement.VERTICAL;
        }
      }
      this.placeShip(coord, 6, placement, ShipType.CARRIER);
    }
  }

  /**
   * @param numBattleship to be placed
   */
  private void placeBattleship(int numBattleship) {
    for (int i = 0; i < numBattleship; i += 1) {
      int x = this.rand.nextInt(width);
      int y = this.rand.nextInt(height);
      boolean orientation = this.rand.nextBoolean();
      Placement placement = Placement.HORIZONTAL;
      if (orientation == true) {
        placement = Placement.VERTICAL;
      }
      Coord coord = new Coord(x, y);
      while (!this.canPlaceShip(coord, 5, placement)) {
        x = this.rand.nextInt(width);
        y = this.rand.nextInt(height);
        coord = new Coord(x, y);
        orientation = this.rand.nextBoolean();
        placement = Placement.HORIZONTAL;
        if (orientation == true) {
          placement = Placement.VERTICAL;
        }
      }
      this.placeShip(coord, 5, placement, ShipType.BATTLESHIP);
    }
  }

  /**
   * @param numDestroyer to be placed
   */
  private void placeDestroyer(int numDestroyer) {
    for (int i = 0; i < numDestroyer; i += 1) {
      int x = this.rand.nextInt(width);
      int y = this.rand.nextInt(height);
      boolean orientation = this.rand.nextBoolean();
      Placement placement = Placement.HORIZONTAL;
      if (orientation == true) {
        placement = Placement.VERTICAL;
      }
      Coord coord = new Coord(x, y);
      while (!this.canPlaceShip(coord, 4, placement)) {
        x = this.rand.nextInt(width);
        y = this.rand.nextInt(height);
        coord = new Coord(x, y);
        orientation = this.rand.nextBoolean();
        placement = Placement.HORIZONTAL;
        if (orientation == true) {
          placement = Placement.VERTICAL;
        }
      }
      this.placeShip(coord, 4, placement, ShipType.DESTROYER);
    }
  }

  /**
   * @param numSubmarine to be placed
   */
  private void placeSubmarine(int numSubmarine) {
    for (int i = 0; i < numSubmarine; i += 1) {
      int x = this.rand.nextInt(width);
      int y = this.rand.nextInt(height);
      boolean orientation = this.rand.nextBoolean();
      Placement placement = Placement.HORIZONTAL;
      if (orientation == true) {
        placement = Placement.VERTICAL;
      }
      Coord coord = new Coord(x, y);
      while (!this.canPlaceShip(coord, 3, placement)) {
        x = this.rand.nextInt(width);
        y = this.rand.nextInt(height);
        coord = new Coord(x, y);
        orientation = this.rand.nextBoolean();
        placement = Placement.HORIZONTAL;
        if (orientation == true) {
          placement = Placement.VERTICAL;
        }
      }
      this.placeShip(coord, 3, placement, ShipType.SUBMARINE);
    }
  }

  /**
   * @param coord     of bow
   * @param size      of ship
   * @param placement orientation
   * @return whether the ship of the given size can be placed at the given coordinate of
   *         the stern at the given orientation
   */
  private boolean canPlaceShip(Coord coord, int size, Placement placement) {
    if (placement == Placement.HORIZONTAL) {
      if (coord.getX() + size > this.width) {
        return false;
      }
      for (int x = coord.getX(); x < coord.getX() + size; x += 1) {
        if (this.shipCoords.contains(new Coord(x, coord.getY()))) {
          return false;
        }
      }
    } else {
      if (coord.getY() + size > this.height) {
        return false;
      }
      for (int y = coord.getY(); y < coord.getY() + size; y += 1) {
        if (this.shipCoords.contains(new Coord(coord.getX(), y))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * @param coord     of ship bow
   * @param size      of ship
   * @param placement orientation
   * @param type      of ship to be placed
   */
  private void placeShip(Coord coord, int size, Placement placement, ShipType type) {
    if (placement == Placement.HORIZONTAL) {
      for (int x = coord.getX(); x < coord.getX() + size; x += 1) {
        this.shipCoords.add(new Coord(x, coord.getY()));
      }
      this.ships.add(new Ship(type, coord, new Coord(coord.getX() + size, coord.getY())));
    } else {
      for (int y = coord.getY(); y < coord.getY() + size; y += 1) {
        this.shipCoords.add(new Coord(coord.getX(), y));
      }
      this.ships.add(new Ship(type, coord, new Coord(coord.getX(), coord.getY() + size)));
    }
  }

  /**
   * @return the board formatted as a string to be displayed in the terminal
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < this.height; row += 1) {
      for (int col = 0; col < this.width; col += 1) {
        sb.append(this.board[row][col]);
      }
      sb.append("\n");
    }
    sb.append("\n");
    return sb.toString();
  }

  /**
   * @return whether or not all Ships on this Board are sunk
   */
  public boolean allSunk() {
    for (Ship ship : this.ships) {
      if (!ship.isSunk()) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param shot directed towards this Board
   * @return whether or not the shot hit a Ship on this Board
   */
  public boolean hitShip(Coord shot) {
    for (Ship s : this.ships) {
      if (s.hitShip(shot)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Adds the given list of shots to this Boards ArrayList of missed shots
   *
   * @param misses shots that did not hit this Board
   */
  public void unsuccessfulHits(ArrayList<Coord> misses) {
    this.missedShots.addAll(misses);
    this.board = this.getBoard();
  }

  /**
   * Adds the given list of shots to this Boards' ArrayList of hit shots
   *
   * @param successfulHits that hit this Board
   */
  public void successfulHits(List<Coord> successfulHits) {
    this.hitShots.addAll(successfulHits);
    this.board = this.getBoard();
  }

  /**
   * @return the number of shots the Player with this Board can shoot
   */
  public int numShots() {
    int numShots = 0;
    for (Ship ship : this.ships) {
      if (!ship.isSunk()) {
        numShots += 1;
      }
    }
    return numShots;
  }

  /**
   * @return the list of this Board's ships
   */
  public ArrayList<Ship> getShips() {
    return this.ships;
  }

  /**
   * Appropriately represents this board as a 2d string array to the Board field
   */
  public abstract void toStringArr();

  /**
   * @return the 2d string representation of this board
   */
  public String[][] getBoard() {
    this.toStringArr();
    return this.board;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }


}
