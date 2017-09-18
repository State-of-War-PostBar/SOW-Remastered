package com.sowpb.sow.util;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.LinkedHashMap;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

public class Config {

	/**
	 * The configurations read from the ini file.
	 */
	private static LinkedHashMap<String, LinkedHashMap<String, String>> configs = new LinkedHashMap<>();

	/**
	 * The ini file.
	 */
	private static Ini file;

	public static void init() {
		DefaultConfig.initDefault();
		configs.putAll(DefaultConfig.defaults);
		Utils.createFile("SOW-Config.ini", false);
		try {
			file = new Ini(new File("SOW-Config.ini"));
			readAllConfig(file);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.createFile("SOW-Config.ini", true);
			return;
		}
	}

	public static void abrogate() {
		putConfigs(configs);
	}

	/**
	 * Read configurations from the ini file.
	 */
	private static void readAllConfig(Ini iniFile) {
		Set<Entry<String, Section>> sections = iniFile.entrySet();
		for (Entry<String, Section> e : sections) {
			Section section = e.getValue();

			LinkedHashMap<String, String> sectionValues = new LinkedHashMap<>();

			Set<Entry<String, String>> values = section.entrySet();
			for (Entry<String, String> e2 : values) {
				sectionValues.put(e2.getKey(), e2.getValue());
				configs.put(e.getKey(), sectionValues);
			}
		}
	}

	/**
	 * Get a configuration.
	 * 
	 * @param block
	 *            The block that the configuration is in.
	 * @param index
	 *            The index of the configuration in the block.
	 */
	public static String get(String block, String index) {
		if (configs.containsKey(block))
			if (configs.get(block).containsKey(index))
				return configs.get(block).get(index);
		return DefaultConfig.get(block, index);
	}

	/**
	 * Get a configuration directly by an index. This method has lower
	 * efficiency than declaring the block.
	 * 
	 * @param index
	 *            The index of the configuration in the ini.
	 */
	public static String get(String index) {
		for (Entry<String, LinkedHashMap<String, String>> entry : configs.entrySet())
			if (entry.getValue().containsKey(index))
				return entry.getValue().get(index);
			else
				return DefaultConfig.get(index);

		return DefaultConfig.get(index);
	}

	/**
	 * Set a configuration. Please specify the block of the configuration.
	 * 
	 * @param block
	 *            The block of the configuration.
	 * @param index
	 *            The index of the configuration.
	 * @param value
	 *            The value of the configuration.
	 */
	public static void set(String block, String index, String value) {
		LinkedHashMap<String, String> temp = new LinkedHashMap<>();
		temp.putAll(configs.get(block));
		temp.put(index, value);
		configs.put(block, temp);
	}

	/**
	 * Put the configurations in the ini file.
	 * 
	 * @throws IOException
	 */
	private static void putConfigs(LinkedHashMap<String, LinkedHashMap<String, String>> conf) {
		for (Entry<String, LinkedHashMap<String, String>> entry : conf.entrySet())
			for (Entry<String, String> entry2 : entry.getValue().entrySet())
				file.put(entry.getKey(), entry2.getKey(), entry2.getValue());

		try {
			file.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Default configurations of the game system.
	 */
	// Yes, it's hard coding. Will consider storing the default configurations
	// inside a resource file of archive later.
	private static final class DefaultConfig {
		static LinkedHashMap<String, LinkedHashMap<String, String>> defaults = new LinkedHashMap<>();

		static void initDefault() {

			LinkedHashMap<String, String> gui = new LinkedHashMap<>();
			gui.put("WinWidth", "1280");
			gui.put("WinHeight", "1024");
			gui.put("FullScreen", "false");
			gui.put("WideScreen", "false");
			gui.put("VSync", "false");
			gui.put("MaxFPS", "60");

			defaults.put("GUI", gui);
		}

		static String get(String block, String index) {
			if (defaults.containsKey(block))
				if (defaults.get(block).containsKey(index))
					return defaults.get(block).get(index);

			throw new RuntimeException("No such field in default configurations.");
		}

		static String get(String index) {
			for (Entry<String, LinkedHashMap<String, String>> entry : defaults.entrySet())
				if (entry.getValue().containsKey(index))
					return entry.getValue().get(index);

			throw new RuntimeException("No such field in default configurations.");
		}

	}
}
