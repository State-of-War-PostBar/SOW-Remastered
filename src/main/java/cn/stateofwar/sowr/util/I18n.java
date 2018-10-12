package cn.stateofwar.sowr.util;

import java.io.IOException;
import java.util.HashMap;
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
	private static final List<String> LEGAL_VALUES = Stream.of("EN_US", "ZH_CN", "ZH_TW").collect(Collectors.toList());

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
				System.err.println(e.getLocalizedMessage());
				e.printStackTrace();
			}

		locale = Locales.parseLocale(Config.get("General", "Language"));
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
	public static Locales getLocale() {
		return locale;
	}

	/**
	 * Get the name of the current language locale.
	 * 
	 * @return Name of the current locale.
	 */
	public static String getLocaleName() {
		return locale.getName();
	}

	/**
	 * Switch the current language locale.
	 * 
	 * @param _locale Name of the new locale.
	 */
	public static void switchLocale(String _locale) {
		if (LEGAL_VALUES.contains(_locale))
			locale = Locales.parseLocale(_locale);
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

		EN_US("EN_US"), ZH_CN("ZH_CN"), ZH_TW("ZH_TW");

		/** Name of the locale. */
		private String name;

		/** All the translation keys and results. */
		private Map<String, String> translations = new HashMap<>();

		private Locales(String _name) {
			if (I18n.LEGAL_VALUES.contains(_name))
				name = _name;
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
			List<String> translations_read = Utils.getResourceAsStrings("sowr/language/" + name + ".lang");

			for (String s : translations_read)
				if (s.indexOf(PARSER) != -1) {
					int index = s.indexOf(PARSER);
					translations.put(s.substring(0, index), s.substring(index + 1));
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
		public static Locales parseLocale(String name) {
			for (Locales locale : Locales.values())
				if (locale.name.equals(name))
					return locale;
			return EN_US;
		}
	}

}
