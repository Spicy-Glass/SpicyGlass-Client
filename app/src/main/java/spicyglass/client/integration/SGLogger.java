package spicyglass.client.integration;

import java.util.logging.Logger;

public final class SGLogger {
    private static Logger LOGGER = null;
    private static Logger getLogger() {
        if(LOGGER == null)
            LOGGER = Logger.getLogger("Spicy Glass");
        return LOGGER;
    }

    /**
     * Print info to the log.
     * @param string
     * String to format, see https://www.javatpoint.com/java-string-format for the placeholders
     * @param args
     * Values to replace the placeholders in the string with
     */
    public static void info(String string, Object... args) {
        getLogger().info(String.format(string, args));
    }

    /**
     * Print a warning to the log.
     * @param string
     * String to format, see https://www.javatpoint.com/java-string-format for the placeholders
     * @param args
     * Values to replace the placeholders in the string with
     */
    public static void warn(String string, Object... args) {
        getLogger().warning(String.format(string, args));
    }

    /**
     * Print an error to the log.
     * @param string
     * String to format, see https://www.javatpoint.com/java-string-format for the placeholders
     * @param args
     * Values to replace the placeholders in the string with
     */
    public static void error(String string, Object... args) {
        getLogger().severe(String.format(string, args));
    }
}
