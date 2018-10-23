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

	private static Ini config_file;

	public static void init() {
		DefaultConfig.initDefault();
		configs.putAll(DefaultConfig.defaults);

		try {
			Utils.createFile(References.CONFIG_FILE_NAME, false);
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		try {
			config_file = new Ini(new File(References.CONFIG_FILE_NAME));
			readAllConfig();
		} catch (IOException e) {
			try {
				Utils.createFile(References.CONFIG_FILE_NAME, true);
			} catch (IOException e1) {
				System.err.println(e1.getLocalizedMessage());
				e1.printStackTrace();
			}
		}
	}

	public static String get(String block, String index) {
		if (configs.containsKey(block))
			if (configs.get(block).containsKey(index))
				return configs.get(block).get(index);
		return DefaultConfig.get(block, index);
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

	private static void putAllConfig(LinkedHashMap<String, LinkedHashMap<String, String>> configs) {
		for (Entry<String, LinkedHashMap<String, String>> entry : configs.entrySet())
			for (Entry<String, String> entry2 : entry.getValue().entrySet())
				config_file.put(entry.getKey(), entry2.getKey(), entry2.getValue());

		try {
			config_file.store();
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private static void readAllConfig() {
		Set<Entry<String, Section>> sections = config_file.entrySet();

		for (Entry<String, Section> entry : sections) {
			Section section = entry.getValue();
			LinkedHashMap<String, String> section_values = new LinkedHashMap<>();
			Set<Entry<String, String>> values = section.entrySet();

			for (Entry<String, String> entry2 : values) {
				section_values.put(entry2.getKey(), entry2.getValue());
				configs.put(entry.getKey(), section_values);
			}
		}
	}

	private static final class DefaultConfig {

		private static LinkedHashMap<String, LinkedHashMap<String, String>> defaults = new LinkedHashMap<>();

		private static final void initDefault() {
			LinkedHashMap<String, String> general = new LinkedHashMap<>();
			general.put("Language", "EN_US");
			defaults.put("General", general);

			LinkedHashMap<String, String> control = new LinkedHashMap<>();
			control.put("Double Click Interval", "0.3");
			defaults.put("Control", control);

			LinkedHashMap<String, String> gui = new LinkedHashMap<>();
			gui.put("Window Width", "1280");
			gui.put("Window Height", "720");
			gui.put("Full Screen", "False");
			gui.put("Vertical Sync", "False");
			gui.put("Max FPS", "120");
			defaults.put("GUI", gui);
		}

		private static final String get(String block, String index) {
			if (defaults.containsKey(block))
				if (defaults.get(block).containsKey(index))
					return defaults.get(block).get(index);

			throw new IllegalStateException(
					"Failed to find [" + block + '.' + index + "] field in default configurations.");
		}

	}

}
