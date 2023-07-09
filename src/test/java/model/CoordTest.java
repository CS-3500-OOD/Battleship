package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CoordTest {
  private Coord origin;

  @BeforeEach
  public void init() {
    this.origin = new Coord(0, 0);
  }

  @Test
  public void testEquals() {
    assertFalse(
        new Coord(0, 0).equals(new Ship(ShipType.CARRIER, new Coord(0, 0), new Coord(0, 6))));
    assertFalse(new Coord(0, 0).equals(new Coord(0, 1)));
    assertFalse(new Coord(0, 0).equals(new Coord(1, 0)));
    assertTrue(new Coord(0, 0).equals(new Coord(0, 0)));
    assertTrue(origin.equals(origin));
  }

  @Test
  public void testHashCode() {
    assertTrue(origin.hashCode() == new Coord(0, 0).hashCode());
    assertTrue(origin.hashCode() == origin.hashCode());
  }
}