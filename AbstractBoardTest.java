package cs3500.pa03.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AbstractBoardTest {
  private Ship submarine;
  private HashMap<Coord, State> subCoords;
  private ArrayList<Ship> ships;
  Board board;
  private ArrayList<Coord> sentShots;
  private String[][] boardArr;
  private PlayerBoard playerBoard;
  private ArrayList<Coord> hits;
  private ArrayList<Coord> misses;
  private String[][] boardUpdate;

  private Board opponentBoard;
  private String[][] opponentArr;
  private String[][] opponentUpdate;

  @BeforeEach
  public void init() {
    this.subCoords = new HashMap<Coord, State>();
    this.subCoords.put(new Coord(0, 0), State.SAFE);
    this.subCoords.put(new Coord(0, 1), State.SAFE);
    this.subCoords.put(new Coord(0, 2), State.HIT);
    this.submarine = new Ship(ShipType.SUBMARINE, new Coord(0, 0), new Coord(0, 2));
    this.ships = new ArrayList<Ship>();
    this.ships.add(this.submarine);
    this.sentShots = new ArrayList<Coord>();
    this.sentShots.add(new Coord(0, 0));
    this.sentShots.add(new Coord(1, 0));
    this.sentShots.add(new Coord(2, 0));
    this.sentShots.add(new Coord(3, 0));

    this.sentShots.add(new Coord(0, 1));

    this.sentShots.add(new Coord(0, 2));
    this.sentShots.add(new Coord(3, 2));
    this.sentShots.add(new Coord(4, 2));
    this.sentShots.add(new Coord(5, 2));

    this.sentShots.add(new Coord(0, 3));
    this.sentShots.add(new Coord(1, 3));
    this.sentShots.add(new Coord(2, 3));
    this.sentShots.add(new Coord(3, 3));
    this.sentShots.add(new Coord(4, 3));
    this.sentShots.add(new Coord(5, 3));

    this.sentShots.add(new Coord(2, 4));
    this.sentShots.add(new Coord(3, 4));
    this.sentShots.add(new Coord(4, 4));

    this.sentShots.add(new Coord(0, 5));
    this.sentShots.add(new Coord(1, 5));
    this.sentShots.add(new Coord(2, 5));
    this.sentShots.add(new Coord(3, 5));
    this.sentShots.add(new Coord(4, 5));
    this.sentShots.add(new Coord(5, 5));


    String[] row1 = {"S", "S", "S", "S", "0", "0"};
    String[] row2 = {"S", "0", "0", "0", "0", "0"};
    String[] row3 = {"S", "0", "0", "S", "S", "S"};
    String[] row4 = {"S", "S", "S", "S", "S", "S"};
    String[] row5 = {"0", "0", "S", "S", "S", "0"};
    String[] row6 = {"S", "S", "S", "S", "S", "S"};
    this.boardArr = new String[][] {row1, row2, row3, row4, row5, row6};

    this.playerBoard = new PlayerBoard(6, 6, 1, 1, 1, 3, new Random(1));
    this.opponentBoard = new OpponentBoard(6, 6, 1, 1, 1, 1, new Random(1));

    this.hits = new ArrayList<Coord>();
    this.hits.add(new Coord(0, 0));
    this.hits.add(new Coord(1, 0));
    this.hits.add(new Coord(2, 0));

    this.misses = new ArrayList<Coord>();
    this.misses.add(new Coord(1, 1));
    this.misses.add(new Coord(2, 2));
    this.misses.add(new Coord(4, 0));

    String[] row7 = {"H", "H", "H", "S", "M", "0"};
    String[] row8 = {"S", "M", "0", "0", "0", "0"};
    String[] row9 = {"S", "0", "M", "S", "S", "S"};
    String[] row10 = {"S", "S", "S", "S", "S", "S"};
    String[] row11 = {"0", "0", "S", "S", "S", "0"};
    String[] row12 = {"S", "S", "S", "S", "S", "S"};
    this.boardUpdate = new String[][] {row7, row8, row9, row10, row11, row12};

    String[] row13 = {"0", "0", "0", "0", "0", "0"};

    this.opponentArr = new String[][] {row13, row13, row13, row13, row13, row13};

    String[] row14 = {"H", "H", "H", "0", "M", "0"};
    String[] row15 = {"0", "M", "0", "0", "0", "0"};
    String[] row16 = {"0", "0", "M", "0", "0", "0"};
    String[] row17 = {"0", "0", "0", "0", "0", "0"};
    String[] row18 = {"0", "0", "0", "0", "0", "0"};
    String[] row19 = {"0", "0", "0", "0", "0", "0"};
    this.opponentUpdate = new String[][] {row14, row15, row16, row17, row18, row19};
  }

  @Test
  public void testAllSunk() {
    assertFalse(this.playerBoard.allSunk());
    assertEquals(6, this.playerBoard.numShots());
    for (Coord shot : this.sentShots) {
      assertTrue(this.playerBoard.hitShip(shot));
    }
    assertTrue(this.playerBoard.allSunk());
    assertEquals(0, this.playerBoard.numShots());
  }

  @Test
  public void testGetBoard() {
    for (int row = 0; row < this.boardArr.length; row += 1) {
      for (int col = 0; col < this.boardArr[row].length; col += 1) {
        assertEquals(this.boardArr[row][col], this.playerBoard.getBoard()[row][col]);
      }
    }

    for (int row = 0; row < this.opponentArr.length; row += 1) {
      for (int col = 0; col < this.opponentArr[row].length; col += 1) {
        assertEquals(this.opponentArr[row][col], this.opponentBoard.getBoard()[row][col]);
      }
    }

    for (Coord shot : this.sentShots) {
      assertTrue(this.playerBoard.hitShip(shot));
    }

    assertFalse(this.playerBoard.hitShip(new Coord(4, 0)));
  }

  @Test
  public void testToString() {
    StringBuilder sb = new StringBuilder();
    for (int row = 0; row < this.boardArr.length; row += 1) {
      for (int col = 0; col < this.boardArr[row].length; col += 1) {
        sb.append(this.boardArr[row][col]);
      }
      sb.append("\n");
    }
    sb.append("\n");
    assertEquals(sb.toString(), this.playerBoard.toString());
  }

  @Test
  public void testUpdateBoard() {
    this.playerBoard.successfulHits(this.hits);
    this.playerBoard.unsuccessfulHits(this.misses);
    for (int row = 0; row < this.boardArr.length; row += 1) {
      for (int col = 0; col < this.boardArr[row].length; col += 1) {
        assertEquals(this.boardUpdate[row][col], this.playerBoard.getBoard()[row][col]);
      }
    }

    this.opponentBoard.successfulHits(this.hits);
    this.opponentBoard.unsuccessfulHits(this.misses);

    for (int row = 0; row < this.opponentUpdate.length; row += 1) {
      for (int col = 0; col < this.opponentUpdate[row].length; col += 1) {
        assertEquals(this.opponentUpdate[row][col], this.opponentBoard.getBoard()[row][col]);
      }
    }
  }


}