package cn.stateofwar.sowr.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import cn.stateofwar.sowr.References;

/**
 * The basic configuration system. All configurations are stored and proceeded
 * by the format of <b><i>block.index=value</i></b>.
 */
public class Config {

	/** Stored configurations. */
	private static LinkedHashMap<String, LinkedHashMap<String, String>> configs = new LinkedHashMap<>();

	/** The configuration storage file. */
	private static Ini file;

	/**
	 * Initialize the default configurations and load user preferences.
	 */
	public static void init() {
		DefaultConfig.initDefault();
		configs.putAll(DefaultConfig.defaults);
		try {
			Utils.createFile(References.CONFIG_FILE_NAME, false);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
		try {
			file = new Ini(new File(References.CONFIG_FILE_NAME));
			readAllConfig(file);
		} catch (IOException e) {
			try {
				Utils.createFile(References.CONFIG_FILE_NAME, true);
			} catch (IOException e1) {
				System.out.println(e.getLocalizedMessage());
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Get a configuration from a block and an index.
	 * 
	 * @param block Block of the configuration.
	 * 
	 * @param index Index of the configuration.
	 * 
	 * @return The configuration value.
	 */
	public static String get(String block, String index) {
		if (configs.containsKey(block))
			if (configs.get(block).containsKey(index))
				return configs.get(block).get(index);
		return DefaultConfig.get(block, index);
	}

	/**
	 * Get a configuration from only an index.
	 * 
	 * @param index Index of the configuration.
	 * 
	 * @return The configuration value.
	 * 
	 * @deprecated If multiple blocks contain identical indices, it will only load
	 *             the first one it found. It also has efficiency issues.
	 */
	@Deprecated
	public static String get(String index) {
		for (Entry<String, LinkedHashMap<String, String>> entry : configs.entrySet())
			if (entry.getValue().containsKey(index))
				return entry.getValue().get(index);
		return DefaultConfig.get(index);
	}

	/**
	 * Set a configuration.
	 * 
	 * @param block Block of the configuration.
	 * 
	 * @param index Index of the configuration.
	 * 
	 * @param value The configuration value.
	 */
	public static void set(String block, String index, String value) {
		LinkedHashMap<String, String> temp = new LinkedHashMap<>();
		temp.putAll(configs.get(block));
		temp.put(index, value);
		configs.put(block, temp);
	}

	/**
	 * Save all configurations to the file.
	 */
	public static void abrogate() {
		putAllConfig(configs);
	}

	/**
	 * Read all configurations from the file.
	 * 
	 * @param ini The configuration file.
	 */
	private static void readAllConfig(Ini ini) {
		Set<Entry<String, Section>> sections = ini.entrySet();
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
	 * Put all the configurations to the ini file.
	 * 
	 * @param conf The configurations to save.
	 */
	private static void putAllConfig(LinkedHashMap<String, LinkedHashMap<String, String>> conf) {
		for (Entry<String, LinkedHashMap<String, String>> entry : conf.entrySet())
			for (Entry<String, String> entry2 : entry.getValue().entrySet())
				file.put(entry.getKey(), entry2.getKey(), entry2.getValue());
		try {
			file.store();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Default configurations of the program.
	 */
	private static final class DefaultConfig {

		/** Stored configurations. */
		private static LinkedHashMap<String, LinkedHashMap<String, String>> defaults = new LinkedHashMap<>();

		/**
		 * Initialize the default configurations.
		 */
		private static void initDefault() {
			LinkedHashMap<String, String> general = new LinkedHashMap<>();
			general.put("Language", "EN_US");
			defaults.put("General", general);

			LinkedHashMap<String, String> gui = new LinkedHashMap<>();
			gui.put("Window Width", "1366");
			gui.put("Window Height", "768");
			gui.put("Full Screen", "False");
			gui.put("Vertical Sync", "True");
			gui.put("Max FPS", "60");
			defaults.put("GUI", gui);
		}

		/**
		 * Get a configuration from a block and an index.
		 * 
		 * @param block Block of the configuration.
		 * 
		 * @param index Index of the configuration.
		 * 
		 * @return The configuration value.
		 */
		private static String get(String block, String index) {
			if (defaults.containsKey(block))
				if (defaults.get(block).containsKey(index))
					return defaults.get(block).get(index);
			throw new RuntimeException("Failed to find [" + block + '.' + index + "] field in default configurations.");
		}

		/**
		 * Get a configuration from only an index.
		 * 
		 * @param index Index of the configuration.
		 * 
		 * @return The configuration value.
		 * 
		 * @deprecated If multiple blocks contain identical indices, it will only load
		 *             the first one it found. It also has efficiency issues.
		 */
		@Deprecated
		private static String get(String index) {
			for (Entry<String, LinkedHashMap<String, String>> entry : defaults.entrySet())
				if (entry.getValue().containsKey(index))
					return entry.getValue().get(index);
			throw new RuntimeException("Failed to find [unknown." + index + "] field in default configurations.");
		}

	}
}
