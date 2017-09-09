package com.sowpb.sow.process;

public class SOWThreads {

	public static ISOWThread tRender = new ThreadRendering();

	public static void callAll() {
		tRender.run();
	}

}
