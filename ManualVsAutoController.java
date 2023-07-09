package cs3500.pa03.controller;

import cs3500.pa03.model.AutoPlayer;
import cs3500.pa03.model.Coord;
import cs3500.pa03.model.CurrentRoundStats;
import cs3500.pa03.model.GameResult;
import cs3500.pa03.model.ManualPlayer;
import cs3500.pa03.model.Player;
import cs3500.pa03.model.ShipType;
import cs3500.pa03.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * A Game with a Manual Player vs an AI Player
 */
public class ManualVsAutoController implements GameController {
  private View view;
  private ManualPlayer manualPlayer;
  private Player autoPlayer;
  private CurrentRoundStats currentRoundStats;
  private Random manualPlayerRandom;
  private Random autoPlayerRandom;
  private int numAutoShots;

  /**
   * @param readable   to read user input
   * @param appendable to display output
   */
  public ManualVsAutoController(Readable readable, Appendable appendable) {
    this.view = new View(readable, appendable);
    this.currentRoundStats = new CurrentRoundStats();
    this.manualPlayerRandom = new Random();
    this.autoPlayerRandom = new Random();
    this.numAutoShots = 1;
  }

  /**
   * @param readable   to read user input
   * @param appendable to display output
   */
  public ManualVsAutoController(Readable readable, Appendable appendable, Random manualPlayerRandom,
                                Random autoPlayerRandom) {
    this.view = new View(readable, appendable);
    this.currentRoundStats = new CurrentRoundStats();
    this.manualPlayerRandom = manualPlayerRandom;
    this.autoPlayerRandom = autoPlayerRandom;
    this.numAutoShots = 1;
  }


  /**
   * Asks the user for their name, and creates a manual Player and AI Player
   *
   * @throws IOException if there is an issue appending to the appendable
   */
  private void createPlayers() throws IOException {
    this.manualPlayer =
        new ManualPlayer(this.view.getPlayerName(), this.currentRoundStats, manualPlayerRandom);
    this.autoPlayer = new AutoPlayer("Auto", autoPlayerRandom);
  }

  /**
   * The entire iteration of the game
   *
   * @throws IOException if there is an issue appending to the appendable
   */
  public void playGame() throws IOException {
    this.view.startGame();
    this.createPlayers();
    HashMap<ShipType, Integer> fleet = this.fleetSelection();
    this.manualPlayer.setup(this.view.getHeight(), this.view.getWidth(), fleet);
    this.autoPlayer.setup(this.view.getHeight(), this.view.getWidth(), fleet);
    this.numAutoShots = this.currentRoundStats.getNumManualShots();
    this.view.displayOpponentBoard(this.autoPlayer.toString());
    this.view.displayPlayerBoard(this.manualPlayer.toString());
    while (this.currentRoundStats.getNumManualShots() > 0 && this.numAutoShots > 0) {
      this.takeShots();
    }
    this.endGame();
  }


  private HashMap<ShipType, Integer> fleetSelection() throws IOException {
    HashMap<ShipType, Integer> fleet = this.view.fleetSelection();
    while (!this.validFleet(fleet)) {
      fleet = this.view.fleetSelection();
    }
    return fleet;
  }


  private boolean validFleet(HashMap<ShipType, Integer> fleet) {
    int numCarrier = fleet.get(ShipType.CARRIER);
    int numBattleship = fleet.get(ShipType.BATTLESHIP);
    int numDestroyer = fleet.get(ShipType.DESTROYER);
    int numSubmarine = fleet.get(ShipType.SUBMARINE);
    return numCarrier + numBattleship + numDestroyer + numSubmarine < this.view.maxFleet()
        && numCarrier > 0 && numBattleship > 0 && numDestroyer > 0 && numSubmarine > 0;
  }


  /**
   * The Players go back and forth taking shots at each other
   *
   * @throws IOException if there is an issue appending to the appendable
   */
  private void takeShots() throws IOException {
    ArrayList<Coord> shotsToCheck = this.view.takeShots(this.currentRoundStats.getNumManualShots());
    while (!this.manualPlayer.validShots(shotsToCheck)) {
      shotsToCheck = this.view.takeShots(this.currentRoundStats.getNumManualShots());
    }
    this.currentRoundStats.addShots(shotsToCheck);
    List<Coord> manualShots = this.manualPlayer.takeShots();
    this.autoPlayer.reportDamage(manualShots);
    List<Coord> playerShots = this.autoPlayer.takeShots();
    this.numAutoShots = playerShots.size();
    this.manualPlayer.reportDamage(playerShots);
    this.view.displayOpponentBoard(this.autoPlayer.toString());
    this.view.displayPlayerBoard(this.manualPlayer.toString());
    this.currentRoundStats.removeAllShots();
  }

  /**
   * Displays the final game outcome
   *
   * @throws IOException if there is an issue appending to the appendable
   */
  private void endGame() throws IOException {
    if (this.currentRoundStats.getNumManualShots() <= 0
        && this.numAutoShots <= 0) {
      this.view.endGame(GameResult.DRAW);
    } else if (this.currentRoundStats.getNumManualShots() <= 0
        && this.numAutoShots > 0) {
      this.view.endGame(GameResult.LOSS);
    } else {
      this.view.endGame(GameResult.WIN);
    }
  }


}
