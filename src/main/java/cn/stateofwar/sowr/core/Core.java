package cn.stateofwar.sowr.core;

import cn.stateofwar.sowr.util.Logger;

/** The core of game engine. */
public class Core {

  private static final Logger LOGGER = new Logger("Core");

  /** Current state of the game. */
  public static GameState state;

  /** The central high precision timer. */
  public static Timer timer;

  /** If the game is running. */
  private static boolean running;

  /** Start the game loop and rendering engine. */
  public static void start() {
    LOGGER.info("The game is starting.");

    state = new GameState();
    state.enter();

    running = true;
    timer = new Timer();
    timer.init();

    while (running) {
      state.input();

      state.update();

      state.render();

      timer.update();
    }
  }

  /** Clear up the game engine. */
  public static void abrogate() {
    LOGGER.info("The game is shut down.");
  }

  /**
   * Check if the game is running.
   *
   * @return If the game is running.
   */
  public static boolean isRunning() {
    return running;
  }

  /** Stop the game engine. */
  public static void stop() {
    running = false;
  }

}
