package model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShipTest {
  private Ship submarine;
  private ArrayList<Coord> subCoords;
  private Ship destroyer;
  private HashMap<Coord, State> destroyCoords;
  private ArrayList<Coord> sentShots;
  private ArrayList<Coord> hitShots;

  @BeforeEach
  public void init() {
    this.subCoords = new ArrayList<Coord>();
    this.subCoords.add(new Coord(0, 0));
    this.subCoords.add(new Coord(0, 1));
    this.subCoords.add(new Coord(0, 2));
    this.submarine = new Ship(ShipType.SUBMARINE, new Coord(0, 0), new Coord(0, 3));
    this.sentShots = new ArrayList<Coord>();
    this.sentShots.add(new Coord(0, 2));
    this.sentShots.add(new Coord(0, 0));
    this.sentShots.add(new Coord(0, 1));
  }

  @Test
  public void testHitShip() {
    assertFalse(this.submarine.isSunk());
    assertTrue(this.submarine.hitShip(new Coord(0, 0)));
    assertFalse(this.submarine.hitShip((new Coord(1, 1))));
    assertTrue(this.submarine.hitShip(new Coord(0, 1)));
    assertTrue(this.submarine.hitShip(new Coord(0, 2)));
    assertTrue(this.submarine.isSunk());
  }


}