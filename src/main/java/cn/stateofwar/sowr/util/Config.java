package cn.stateofwar.sowr.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import cn.stateofwar.sowr.References;

public class Config {

	private static LinkedHashMap<String, LinkedHashMap<String, String>> configs = new LinkedHashMap<>();

	private static Ini file;

	public static void init() {
		DefaultConfig.initDefault();
		configs.putAll(DefaultConfig.defaults);
		Utils.createFile(References.CONFIG_FILE_NAME, false);
		try {
			file = new Ini(new File(References.CONFIG_FILE_NAME));
			readAllConfig(file);
		} catch (Exception e) {
			Utils.createFile(References.CONFIG_FILE_NAME, true);
		}
	}

	public static String get(String block, String index) {
		if (configs.containsKey(block))
			if (configs.get(block).containsKey(index))
				return configs.get(block).get(index);
		return DefaultConfig.get(block, index);
	}

	/**
	 * Get a configuration from an index. <br />
	 * If multiple blocks contain a same index, it will load the first one it finds.
	 */
	@Deprecated
	public static String get(String index) {
		for (Entry<String, LinkedHashMap<String, String>> entry : configs.entrySet())
			if (entry.getValue().containsKey(index))
				return entry.getValue().get(index);
		return DefaultConfig.get(index);
	}

	public static void set(String block, String index, String value) {
		LinkedHashMap<String, String> temp = new LinkedHashMap<>();
		temp.putAll(configs.get(block));
		temp.put(index, value);
		configs.put(block, temp);
	}

	public static void abrogate() {
		putAllConfig(configs);
	}

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

	private static void putAllConfig(LinkedHashMap<String, LinkedHashMap<String, String>> conf) {
		for (Entry<String, LinkedHashMap<String, String>> entry : conf.entrySet())
			for (Entry<String, String> entry2 : entry.getValue().entrySet())
				file.put(entry.getKey(), entry2.getKey(), entry2.getValue());
		try {
			file.store();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final class DefaultConfig {

		private static LinkedHashMap<String, LinkedHashMap<String, String>> defaults = new LinkedHashMap<>();

		static void initDefault() {

			LinkedHashMap<String, String> gui = new LinkedHashMap<>();
			gui.put("Window Width", "1280");
			gui.put("Window Height", "768");
			gui.put("Full Screen", "false");
			gui.put("Vertical Sync", "true");
			gui.put("Max FPS", "60");
			defaults.put("GUI", gui);

		}

		static String get(String block, String index) {
			if (defaults.containsKey(block))
				if (defaults.get(block).containsKey(index))
					return defaults.get(block).get(index);
			throw new RuntimeException("Failed to find [" + block + '.' + index + "] field in default configurations.");
		}

		@Deprecated
		static String get(String index) {
			for (Entry<String, LinkedHashMap<String, String>> entry : defaults.entrySet())
				if (entry.getValue().containsKey(index))
					return entry.getValue().get(index);
			throw new RuntimeException("Failed to find [(unknown)." + index + "] field in default configurations.");
		}

	}
}
