package com.sowpb.sow.main;

import com.sowpb.sow.util.Logger;

public class Main {

	private static final Logger logger = new Logger("Main");

	public static void main(String[] args) {
		Trim.initiate();
		logger.info("Welcome to the new State of War!");
		logger.info("Initializing the program...");

		logger.info("Shutting down client...");
		Trim.abrogate();
		logger.info("The client is successfully shut down.");
	}

}
