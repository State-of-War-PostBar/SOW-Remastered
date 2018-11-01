package cn.stateofwar.sowr.main;

import cn.stateofwar.sowr.core.Core;
import cn.stateofwar.sowr.util.Logger;

public class Main {

  private static final Logger LOGGER = new Logger("Main");

  public static String[] arguments;

  public static void main(String[] args) {
    arguments = args;

    LOGGER.init();

    LOGGER.info("Welcome to the new State of War!");
    LOGGER.info("Initializing the program...");
    Trim.init();

    Core.start();

    Core.abrogate();

    LOGGER.info("Shutting down client...");
    Trim.abrogate();
    LOGGER.info("The client is shut down. Thank you for playing!");
  }

}
