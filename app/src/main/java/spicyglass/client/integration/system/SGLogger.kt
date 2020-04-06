package spicyglass.client.integration.system

import java.util.logging.Logger

/**
 * A simple class to easily handle logging.
 */
object SGLogger {
    private var LOGGER: Logger? = null
    private val logger: Logger
        get() {
            if (LOGGER == null)
                LOGGER = Logger.getLogger("Spicy Glass")
            return LOGGER!!
        }

    /**
     * Print info to the log.
     * @param string
     * String to format, see https://www.javatpoint.com/java-string-format for the placeholders
     * @param args
     * Values to replace the placeholders in the string with
     */
    @JvmStatic
    fun info(string: String, vararg args: Any) {
        logger.info("[Spicy Glass] "+String.format(string, *args))
    }

    /**
     * Print a warning to the log.
     * @param string
     * String to format, see https://www.javatpoint.com/java-string-format for the placeholders
     * @param args
     * Values to replace the placeholders in the string with
     */
    @JvmStatic
    fun warn(string: String, vararg args: Any) {
        logger.warning("[Spicy Glass] "+String.format(string, *args))
    }

    /**
     * Print an error to the log.
     * @param string
     * String to format, see https://www.javatpoint.com/java-string-format for the placeholders
     * @param args
     * Values to replace the placeholders in the string with
     */
    @JvmStatic
    fun error(string: String, vararg args: Any) {
        logger.severe("[Spicy Glass] "+String.format(string, *args))
    }
}