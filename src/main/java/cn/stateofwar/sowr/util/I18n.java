package cn.stateofwar.sowr.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * I-n-t-e-r-n-a-t-i-o-n-a-l-i-z-a-t-i-o-n.
 */
public class I18n {

	private static final char PARSER = '=';

	/** Current language preference of the program. */
	private static Locale current_locale;

	/**
	 * Initialize all the translations.
	 */
	public static void init() {
		try {
			for (Locale l : Locale.values())
				l.readValues();
		} catch (IOException e) {
			System.err.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		current_locale = Locale.parseLocale(Config.get("General", "Language"));
	}

	/**
	 * Reveal a translation.
	 * 
	 * @param key Translation key.
	 * 
	 * @return Translation result.
	 */
	public static String t(String key) {
		return current_locale.reveal(key);
	}

	/**
	 * Get the current language locale.
	 * 
	 * @return The current locale.
	 */
	public static Locale getLocale() {
		return current_locale;
	}

	/**
	 * Switch the current language locale.
	 * 
	 * @param _locale Name of the new locale.
	 */
	public static void switchLocale(String locale) {
		current_locale = Locale.parseLocale(locale);
	}

	/**
	 * Language translations locale.
	 */
	public static enum Locale {

		EN_US("EN_US"), ZH_CN("ZH_CN"), ZH_TW("ZH_TW");

		/** Name of the locale. */
		private String name;

		/** All the translation keys and results. */
		private Map<String, String> translations = new HashMap<>();

		/**
		 * Create a language locale.
		 * 
		 * @param _name Name of the locale.
		 */
		private Locale(String _name) {
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
			List<String> values = Utils.getResourceAsStrings("sowr/language/" + name + ".lang");

			for (String s : values) {
				int parser_index = s.indexOf(PARSER);
				if (parser_index != -1)
					translations.put(s.substring(0, parser_index), s.substring(parser_index + 1));
			}
		}

		/**
		 * Reveal a translation.
		 * 
		 * @param key Translation key.
		 * 
		 * @return Translation result. If no translation available, the key itself will
		 *         be returned.
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
		 * @return The locale parsed.If the name is not valid, default locale
		 *         <i>(English - United States)</i> will be selected.
		 */
		public static Locale parseLocale(String _name) {
			for (Locale locale : Locale.values())
				if (locale.getName().equals(_name))
					return locale;
			return EN_US;
		}

	}

}
