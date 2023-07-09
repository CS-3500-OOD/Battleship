package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractPlayerTest {
  private Player playerOne;
  private ArrayList<Coord> hits;
  private ArrayList<Coord> misses;
  private ArrayList<Coord> sentShots;
  private HashMap<ShipType, Integer> specifications;
  private String boardArr;
  private AutoPlayer autoPlayer;
  private ManualPlayer playerTwo;
  private String autoBoard;
  private ArrayList<Coord> shots1;
  private ArrayList<Coord> shots2;
  private ArrayList<Coord> shots3;
  private ArrayList<Coord> shots4;
  private ArrayList<Coord> shots5;


  @BeforeEach
  public void init() {

    this.hits = new ArrayList<Coord>();
    this.hits.add(new Coord(0, 0));
    this.hits.add(new Coord(1, 0));
    this.hits.add(new Coord(2, 0));

    this.misses = new ArrayList<Coord>();
    this.misses.add(new Coord(1, 1));
    this.misses.add(new Coord(2, 2));
    this.misses.add(new Coord(4, 0));

    this.sentShots = new ArrayList<Coord>();
    this.sentShots.addAll(this.hits);
    this.sentShots.addAll(this.misses);

    this.specifications = new HashMap<ShipType, Integer>();
    this.specifications.put(ShipType.SUBMARINE, 1);
    this.specifications.put(ShipType.DESTROYER, 1);
    this.specifications.put(ShipType.BATTLESHIP, 1);
    this.specifications.put(ShipType.CARRIER, 1);


    this.boardArr = "SSSS00\n"
        + "000000\n"
        + "000SSS\n"
        + "0SSSSS\n"
        + "000000\n"
        + "SSSSSS\n\n";


    this.playerOne = new ManualPlayer("player one", new CurrentRoundStats(), new Random(1));
    this.playerOne.setup(6, 6, specifications);
    this.playerTwo = new ManualPlayer("auto", new CurrentRoundStats(), new Random(2));
    this.autoBoard = "0S00S0\n"
        + "0S00SS\n"
        + "0S0SSS\n"
        + "0S0SSS\n"
        + "0S0SS0\n"
        + "000SS0\n\n";
    this.playerTwo.setup(6, 6, specifications);
    this.autoPlayer = new AutoPlayer("auto", new Random(2));
    this.autoPlayer.setup(6, 6, this.specifications);
    this.shots1 = new ArrayList<Coord>();
    this.shots1.add(new Coord(1, 0));
    this.shots1.add(new Coord(4, 0));
    this.shots1.add(new Coord(3, 0));
    this.shots1.add(new Coord(0, 0));

    this.shots2 = new ArrayList<Coord>();
    this.shots2.add(new Coord(2, 5));
    this.shots2.add(new Coord(3, 3));
    this.shots2.add(new Coord(2, 1));
    this.shots2.add(new Coord(1, 3));

    this.shots3 = new ArrayList<Coord>();
    this.shots3.add(new Coord(0, 3));
    this.shots3.add(new Coord(0, 4));
    this.shots3.add(new Coord(5, 1));
    this.shots3.add(new Coord(0, 5));

    this.shots4 = new ArrayList<Coord>();
    this.shots4.add(new Coord(4, 5));
    this.shots4.add(new Coord(2, 4));
    this.shots4.add(new Coord(2, 2));
    this.shots4.add(new Coord(1, 4));
  }

  @Test
  public void testReportDamage() {
    assertEquals(this.boardArr, this.playerOne.toString());
    assertEquals(this.autoBoard, this.playerTwo.toString());
    assertEquals(this.shots1, this.autoPlayer.takeShots());
    assertEquals(this.shots2, this.autoPlayer.takeShots());
    assertEquals(this.shots3, this.autoPlayer.takeShots());
    assertEquals(this.shots4, this.autoPlayer.takeShots());
    assertEquals(this.hits, this.playerOne.reportDamage(this.sentShots));
  }


  @Test
  public void testName() {
    assertEquals("player one", this.playerOne.name());
  }

}