package cn.stateofwar.sowr.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Internationalization for the program.
 */
public class I18n {

	/** Parser to separate translation keys and results in language files. */
	private static final String PARSER = "=";

	/** All the languages this program supports. */
	private static final List<String> LEGAL_VAL = Stream.of("EN_US", "ZH_CN").collect(Collectors.toList());

	/** Current language preference of the program. */
	private static Locales locale;

	/**
	 * Initialize the translations.
	 */
	public static void init() {
		for (Locales l : Locales.values())
			try {
				l.readValues();
			} catch (IOException e) {
				e.printStackTrace();
			}
		locale = Locales.parseLoc(Config.get("General", "Language"));
	}

	/**
	 * Reveal a translation.
	 * 
	 * @param key Translation key.
	 * 
	 * @return Translation result.
	 */
	public static String t(String key) {
		return locale.reveal(key);
	}

	/**
	 * Get the current language locale.
	 * 
	 * @return The current locale.
	 */
	public static Locales getLoc() {
		return locale;
	}

	/**
	 * Get the name of the current language locale.
	 * 
	 * @return Name of the current locale.
	 */
	public static String getLocName() {
		return locale.getName();
	}

	/**
	 * Switch the current language locale.
	 * 
	 * @param loc Name of the new locale.
	 */
	public static void switchLoc(String loc) {
		if (LEGAL_VAL.contains(loc))
			locale = Locales.parseLoc(loc);
		else {
			locale = Locales.EN_US;
			Logger.PUBLIC_LOGGER.error(
					"Attemption of selecting a language locale that doesn't exist. Default language locale (English-United States) is selected.");
		}
	}

	/**
	 * Language translations.
	 */
	public static enum Locales {

		EN_US("EN_US"), ZH_CN("ZH_CN");

		/** Name of the locale. */
		private String name;

		/** All the translation keys and results. */
		private Map<String, String> translations = new LinkedHashMap<>();

		private Locales() {
		}

		private Locales(String x) {
			if (I18n.LEGAL_VAL.contains(x))
				name = x;
		}

		/**
		 * Get the name of the locale.
		 * 
		 * @return Name of the locale.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Read all the translations from the language files.
		 * 
		 * @throws IOException
		 */
		private void readValues() throws IOException {
			List<String> trans = new ArrayList<>();
			trans = Utils.getResAsStrings("sowr/language/" + name + ".lang");
			for (String x : trans)
				if (x.indexOf(PARSER) != -1) {
					int index = x.indexOf(PARSER);
					translations.put(x.substring(0, index), x.substring(index + 1));
				}
		}

		/**
		 * Reveal a translation. If there is no translation available it will return the
		 * key itself.
		 * 
		 * @param key Translation key.
		 * 
		 * @return Translation result.
		 */
		private String reveal(String key) {
			if (translations.containsKey(key))
				return translations.get(key);
			return key;
		}

		/**
		 * Parse a locale from a name.
		 * 
		 * @param name Name of the locale.
		 * 
		 * @return The locale parsed.If the value is not valid, default <i>(English -
		 *         United States)</i> will be selected.
		 */
		public static Locales parseLoc(String name) {
			for (Locales loc : Locales.values())
				if (loc.name.equals(name))
					return loc;
			return EN_US;
		}
	}

}
