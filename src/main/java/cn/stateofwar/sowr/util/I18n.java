package cn.stateofwar.sowr.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Internationalization of the program.
 */
public class I18n {

	/** The parser to separate translation keys and translation results. */
	private static final String PARSER = "=";

	/** The current language preference of the program. */
	private static Locales locale;

	/** All the languages this program supports. */
	private static final ArrayList<String> LEGAL_VAL = new ArrayList<>() {
		private static final long serialVersionUID = 233666998L;
		{
			add("EN_US");
			add("ZH_CN");
		}
	};

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
	 * @param Name of the new locale.
	 */
	public static void switchLoc(String loc) {
		if (LEGAL_VAL.contains(loc))
			locale = Locales.parseLoc(loc);
		else
			throw new IllegalArgumentException("No such locale!");
	}

	/**
	 * Reveal a translation.
	 * 
	 * @param key The translation key.
	 * 
	 * @return The translation result.
	 */
	public static String t(String key) {
		return locale.reveal(key);
	}

	/**
	 * Language translations.
	 */
	public static enum Locales {

		EN_US("EN_US"), ZH_CN("ZH_CN");

		/** Name of the locale. */
		private String name;

		/** All the translation keys and results. */
		private Map<String, String> translations = new HashMap<>();

		private Locales() {
		}

		Locales(String x) {
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
		 */
		private void readValues() throws IOException {
			List<String> trans = new ArrayList<>();
			trans = Utils.getResAsStrings("sowr/languages/" + name + ".lang");
			for (String x : trans)
				if (x.indexOf(PARSER) != -1) {
					int index = x.indexOf(PARSER);
					translations.put(x.substring(0, index), x.substring(index + 1));
				}
		}

		/**
		 * Reveal a translation.
		 * 
		 * @param key The translation key.
		 * 
		 * @return The translation result.
		 */
		private String reveal(String key) {
			if (translations.containsKey(key))
				return translations.get(key);
			return key;
		}

		/**
		 * Parse a locale from a name. If the value is not valid, default <i>(English -
		 * United States)</i> will be selected.
		 */
		public static Locales parseLoc(String x) {
			for (Locales loc : Locales.values())
				if (loc.name.equals(x))
					return loc;
			return EN_US;
		}
	}

}
