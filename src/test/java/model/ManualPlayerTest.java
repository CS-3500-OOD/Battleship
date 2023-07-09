package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ManualPlayerTest {
  private ManualPlayer playerOne;

  private HashMap<ShipType, Integer> specifications;
  private CurrentRoundStats currentRoundStats;
  private ArrayList<Coord> sentShots;

  @BeforeEach
  public void init() {
    this.currentRoundStats = new CurrentRoundStats();
    this.playerOne = new ManualPlayer("player one", this.currentRoundStats, new Random(1));
    this.specifications = new HashMap<ShipType, Integer>();
    this.specifications.put(ShipType.CARRIER, 1);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.DESTROYER, 1);
    this.specifications.put(ShipType.SUBMARINE, 1);
    this.playerOne.setup(6, 6, this.specifications);
    this.sentShots = new ArrayList<Coord>();
  }

  @Test
  public void testTakeShots() {
    this.sentShots.add(new Coord(1, 0));
    this.sentShots.add(new Coord(4, 0));
    this.sentShots.add(new Coord(2, 3));
    this.sentShots.add(new Coord(5, 5));
    this.sentShots.add(new Coord(6, 4));
    this.currentRoundStats.addShots(this.sentShots);
    assertEquals(this.sentShots, this.playerOne.takeShots());
  }

  @Test
  public void testValidShots() {
    this.sentShots.add(new Coord(1, 0));
    this.sentShots.add(new Coord(4, 0));
    this.sentShots.add(new Coord(2, 3));
    this.sentShots.add(new Coord(5, 5));
    this.sentShots.add(new Coord(6, 4));
    this.currentRoundStats.addShots(this.sentShots);
    assertFalse(this.playerOne.validShots(this.sentShots));
    this.sentShots.removeAll(this.sentShots);
    this.currentRoundStats.removeAllShots();
    this.sentShots.add(new Coord(0, 0));
    this.currentRoundStats.addShots(this.sentShots);
    assertTrue(this.playerOne.validShots(this.sentShots));
    assertFalse(this.playerOne.validShots(this.sentShots));
    this.sentShots.removeAll(this.sentShots);
    this.currentRoundStats.removeAllShots();
    this.sentShots.add(new Coord(-1, 0));
    this.currentRoundStats.addShots(this.sentShots);
    assertFalse(this.playerOne.validShots(this.sentShots));
    this.sentShots.removeAll(this.sentShots);
    this.currentRoundStats.removeAllShots();
    this.sentShots.add(new Coord(0, -1));
    this.currentRoundStats.addShots(this.sentShots);
    assertFalse(this.playerOne.validShots(this.sentShots));
    this.currentRoundStats.removeAllShots();
    this.sentShots.add(new Coord(4, 6));
    this.currentRoundStats.addShots(this.sentShots);
    assertFalse(this.playerOne.validShots(this.sentShots));

    this.sentShots.removeAll(this.sentShots);
    this.currentRoundStats.removeAllShots();
    this.sentShots.add(new Coord(0, 8));
    this.currentRoundStats.addShots(this.sentShots);
    assertFalse(this.playerOne.validShots(this.sentShots));
  }
}