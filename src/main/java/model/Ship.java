package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Ship gamepiece
 */
public class Ship {
  private ShipType type;
  private HashMap<Coord, State> coords;

  /**
   * @param type  ShipType
   * @param bow   front coordinate
   * @param stern back coordinate
   */
  public Ship(ShipType type, Coord bow, Coord stern) {
    this.type = type;
    this.coords = new HashMap<Coord, State>();
    if (bow.getX() == stern.getX()) {
      for (int y = bow.getY(); y < stern.getY(); y += 1) {
        this.coords.put(new Coord(bow.getX(), y), State.SAFE);
      }
    } else {
      for (int x = bow.getX(); x < stern.getX(); x += 1) {
        this.coords.put(new Coord(x, bow.getY()), State.SAFE);
      }
    }
  }

  /**
   * @return whether or not this ship is sunk
   */
  public boolean isSunk() {
    for (Map.Entry<Coord, State> coord : this.coords.entrySet()) {
      if (coord.getValue() != State.HIT) {
        return false;
      }
    }
    return true;
  }

  /**
   * @param shot directed towards this ship
   * @return whether or not the shot hit the ship
   */
  public boolean hitShip(Coord shot) {
    if (this.coords.containsKey(shot)) {
      this.coords.put(shot, State.HIT);
      return true;
    }
    return false;
  }

}
