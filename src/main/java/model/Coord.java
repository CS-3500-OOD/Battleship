package model;

import java.util.Objects;

/**
 * Represents an xy-coordinate
 */
public class Coord {
  private int xcoord;
  private int ycoord;

  public Coord(int xcoord, int ycoord) {
    this.xcoord = xcoord;
    this.ycoord = ycoord;
  }

  /**
   * @return x-coordinate
   */
  public int getX() {
    return this.xcoord;
  }

  /**
   * @return y-coordinate
   */
  public int getY() {
    return this.ycoord;
  }

  /**
   * @param that object
   * @return whether the object is a Coordinate with the same x and y as this Coordinate
   */
  @Override
  public boolean equals(Object that) {
    if (!(that instanceof Coord)) {
      return false;
    } else {
      return this.xcoord == ((Coord) that).xcoord && this.ycoord == ((Coord) that).ycoord;
    }
  }

  /**
   * @return this Coordinate's hashcode, which is determined by the hashcodes of its x and y
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.xcoord) + Objects.hash(this.ycoord);
  }

}
