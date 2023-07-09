import java.io.IOException;

/**
 * A Game Controller
 */
public interface GameController {

  /**
   * the entire game loop
   *
   * @throws IOException if there is an issue appending to the appendable
   */
  public void playGame() throws IOException;

}
