package com.sowpb.sow.process;

import com.sowpb.sow.graphic.process.GraphMain;
import com.sowpb.sow.util.Logger;

public class ThreadRendering implements ISOWThread {

	private static final Logger logger = new Logger("Render");

	@Override
	public void run() {
		logger.info("Rendering thread thriggered.");

		GraphMain.startupGraphic();
		GraphMain.startGLoop();

		logger.info("Rendering thread is closed.");
	}

}
