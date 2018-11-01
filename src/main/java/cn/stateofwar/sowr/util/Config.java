package cn.stateofwar.sowr.util;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.ini4j.Ini;
import org.ini4j.Profile.Section;

import cn.stateofwar.sowr.References;

/** A basic configuration system. */
public class Config {

  /** Loaded configurations. */
  private static LinkedHashMap<String, LinkedHashMap<String, String>> configs =
      new LinkedHashMap<>();

  /** The configuration storage file. */
  private static Ini config_file;

  /** Initialize default configurations and load user preferences. */
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

  /**
   * Get a configuration.
   *
   * @param block Block of the configuration.
   * @param index Index of the configuration.
   */
  public static String get(String block, String index) {
    if (configs.containsKey(block))
      if (configs.get(block).containsKey(index)) return configs.get(block).get(index);
    return DefaultConfig.get(block, index);
  }

  /**
   * Set a configuration.
   *
   * @param block Block of the configuration.
   * @param index Index of the configuration.
   * @param value Value to set.
   */
  public static void set(String block, String index, String value) {
    LinkedHashMap<String, String> temp = new LinkedHashMap<>();
    temp.putAll(configs.get(block));
    temp.put(index, value);
    configs.put(block, temp);
  }

  /** Clean up the configuration system and store user preferences. */
  public static void abrogate() {
    putAllConfig(configs);
  }

  /** Read all the configurations from the storage file. */
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

  /**
   * Put all configurations to the storage file.
   *
   * @param configs Configurations to save.
   */
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

  /** Default configurations of the program. */
  private static final class DefaultConfig {

    /** Saved default configurations. */
    private static LinkedHashMap<String, LinkedHashMap<String, String>> defaults =
        new LinkedHashMap<>();

    /** Initialize default configurations. */
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

    /**
     * Get a configuration.
     *
     * @param block Block of the configuration.
     * @param index Index of the configuration.
     * @throws IllegalArgumentException Attemption to look for a default configuration that does not
     *     exist.
     */
    private static final String get(String block, String index) {
      if (defaults.containsKey(block))
        if (defaults.get(block).containsKey(index)) return defaults.get(block).get(index);

      throw new IllegalArgumentException(
          "Failed to find [" + block + '.' + index + "] field in default configurations.");
    }
  }

}
