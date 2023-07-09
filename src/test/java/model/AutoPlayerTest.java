package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AutoPlayerTest {
  private AutoPlayer autoPlayer;
  private HashMap<ShipType, Integer> specifications;
  String boardString;
  ArrayList<Coord> sentShots;

  @BeforeEach
  public void init() {
    this.autoPlayer = new AutoPlayer("auto", new Random(2));
    this.specifications = new HashMap<ShipType, Integer>();
    this.specifications.put(ShipType.CARRIER, 1);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.DESTROYER, 1);
    this.specifications.put(ShipType.SUBMARINE, 2);
    this.boardString = "0000000\n"
        + "0000000\n"
        + "0000000\n"
        + "0000000\n"
        + "0000000\n"
        + "0000000\n\n";

    this.sentShots = new ArrayList<Coord>();
    this.sentShots.add(new Coord(5, 3));
    this.sentShots.add(new Coord(4, 4));
    this.sentShots.add(new Coord(2, 5));
    this.sentShots.add(new Coord(5, 1));
    this.sentShots.add(new Coord(4, 0));

    this.autoPlayer.setup(6, 7, this.specifications);

  }

  @Test
  public void testSetup() {
    assertEquals(this.boardString, this.autoPlayer.toString());
  }

  @Test
  public void testTakeShots() {
    assertEquals(this.sentShots, this.autoPlayer.takeShots());
  }


}