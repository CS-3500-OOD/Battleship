import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the main driver of this project.
 */
public class Driver {
  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws IOException {
    ManualVsAutoController
        manualController = new ManualVsAutoController(new InputStreamReader(System.in), System.out);
    manualController.playGame();
  }
}