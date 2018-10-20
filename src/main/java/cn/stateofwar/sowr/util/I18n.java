package cn.stateofwar.sowr.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class I18n {

	private static final String PARSER = "=";

	private static Locale current_locale;

	public static void init() {
		for (Locale l : Locale.values())
			try {
				l.readValues();
			} catch (IOException e) {
				System.err.println(e.getLocalizedMessage());
				e.printStackTrace();
			}

		current_locale = Locale.parseLocale(Config.get("General", "Language"));
	}

	public static String t(String key) {
		return current_locale.reveal(key);
	}

	public static Locale getLocale() {
		return current_locale;
	}

	public static String getLocaleName() {
		return current_locale.getName();
	}

	public static void switchLocale(String locale) {
		current_locale = Locale.parseLocale(locale);
	}

	public static enum Locale {

		EN_US("EN_US"), ZH_CN("ZH_CN"), ZH_TW("ZH_TW");

		private String name;

		private Map<String, String> translations = new HashMap<>();

		private Locale(String _name) {
			name = _name;
		}

		public String getName() {
			return name;
		}

		private void readValues() throws IOException {
			List<String> values = Utils.getResourceAsStrings("sowr/language/" + name + ".lang");

			for (String s : values) {
				int parser_index = s.indexOf(PARSER);
				if (parser_index != -1)
					translations.put(s.substring(0, parser_index), s.substring(parser_index + 1));
			}
		}

		private String reveal(String key) {
			if (translations.containsKey(key))
				return translations.get(key);
			return key;
		}

		public static Locale parseLocale(String _name) {
			for (Locale locale : Locale.values())
				if (locale.name.equals(_name))
					return locale;
			return EN_US;
		}
	}

}
