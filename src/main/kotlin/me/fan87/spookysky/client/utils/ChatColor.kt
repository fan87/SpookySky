package me.fan87.spookysky.client.utils

import org.jetbrains.annotations.Contract
import java.util.regex.Pattern

const val COLOR_CHAR1 = '\u00A7'

/**
 * All supported color values for chat
 *
 * Source: [ChatColor.java](https://hub.spigotmc.org/stash/projects/SPIGOT/repos/bukkit/browse/src/main/java/org/bukkit/ChatColor.java)
 * Please contact me if you are the author of it and would like to remove this file from this repository
 */
enum class ChatColor(
    /**
     * Gets the char value associated with this color
     *
     * @return A char value of this color code
     */
    val char: Char, private val intCode: Int,
    /**
     * Checks if this code is a format code as opposed to a color code.
     *
     * @return whether this ChatColor is a format code
     */
    val isFormat: Boolean = false
) {
    /**
     * Represents black
     */
    BLACK('0', 0x00),

    /**
     * Represents dark blue
     */
    DARK_BLUE('1', 0x1),

    /**
     * Represents dark green
     */
    DARK_GREEN('2', 0x2),

    /**
     * Represents dark blue (aqua)
     */
    DARK_AQUA('3', 0x3),

    /**
     * Represents dark red
     */
    DARK_RED('4', 0x4),

    /**
     * Represents dark purple
     */
    DARK_PURPLE('5', 0x5),

    /**
     * Represents gold
     */
    GOLD('6', 0x6),

    /**
     * Represents gray
     */
    GRAY('7', 0x7),

    /**
     * Represents dark gray
     */
    DARK_GRAY('8', 0x8),

    /**
     * Represents blue
     */
    BLUE('9', 0x9),

    /**
     * Represents green
     */
    GREEN('a', 0xA),

    /**
     * Represents aqua
     */
    AQUA('b', 0xB),

    /**
     * Represents red
     */
    RED('c', 0xC),

    /**
     * Represents light purple
     */
    LIGHT_PURPLE('d', 0xD),

    /**
     * Represents yellow
     */
    YELLOW('e', 0xE),

    /**
     * Represents white
     */
    WHITE('f', 0xF),

    /**
     * Represents magical characters that change around randomly
     */
    MAGIC('k', 0x10, true),

    /**
     * Makes the text bold.
     */
    BOLD('l', 0x11, true),

    /**
     * Makes a line appear through the text.
     */
    STRIKETHROUGH('m', 0x12, true),

    /**
     * Makes the text appear underlined.
     */
    UNDERLINE('n', 0x13, true),

    /**
     * Makes the text italic.
     */
    ITALIC('o', 0x14, true),

    /**
     * Resets all previous chat colors or formats.
     */
    RESET('r', 0x15);

    companion object {
        /**
         * The special character which prefixes all chat colour codes. Use this if
         * you need to dynamically convert colour codes from your custom format.
         */
        const val COLOR_CHAR = '\u00A7'
        const val RAINBOW_COLOR_CHAR = '\uE069'

        private val STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + COLOR_CHAR.toString() + "[0-9A-FK-ORX]")
        private val BY_ID: MutableMap<Int, ChatColor> = HashMap()
        private val BY_CHAR: MutableMap<Char, ChatColor> = HashMap()

        /**
         * Gets the color represented by the specified color code
         *
         * @param code Code to check
         * @return Associative [org.bukkit.ChatColor] with the given code,
         * or null if it doesn't exist
         */
        fun getByChar(code: Char): ChatColor? {
            return BY_CHAR[code]
        }

        /**
         * Gets the color represented by the specified color code
         *
         * @param code Code to check
         * @return Associative [org.bukkit.ChatColor] with the given code,
         * or null if it doesn't exist
         */
        fun getByChar(code: String): ChatColor? {
            if (code.length == 0) {
                throw IllegalArgumentException("Code must have at least one char")
            }
            return BY_CHAR[code[0]]
        }

        /**
         * Strips the given message of all color codes
         *
         * @param input String to strip of color
         * @return A copy of the input string, without any coloring
         */
        @Contract("!null -> !null; null -> null")
        fun stripColor(input: String?): String? {
            return if (input == null) {
                null
            } else STRIP_COLOR_PATTERN.matcher(input).replaceAll("")
        }

        /**
         * Translates a string using an alternate color code character into a
         * string that uses the internal ChatColor.COLOR_CODE color code
         * character. The alternate color code character will only be replaced if
         * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
         *
         * @param altColorChar The alternate color code character to replace. Ex: &amp;
         * @param textToTranslate Text containing the alternate color code character.
         * @return Text containing the ChatColor.COLOR_CODE color code character.
         */
        fun translateAlternateColorCodes(altColorChar: Char, textToTranslate: String): String {
            val b = textToTranslate.toCharArray()
            for (i in 0 until b.size - 1) {
                if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                    b[i] = COLOR_CHAR
                    b[i + 1] = b[i + 1].lowercaseChar()
                }
            }
            return String(b)
        }

        /**
         * Gets the ChatColors used at the end of the given input string.
         *
         * @param input Input string to retrieve the colors from.
         * @return Any remaining ChatColors to pass onto the next line.
         */
        fun getLastColors(input: String): String {
            var result = ""
            val length = input.length

            // Search backwards from the end as it is faster
            for (index in length - 1 downTo -1 + 1) {
                val section = input[index]
                if (section == COLOR_CHAR && index < length - 1) {
                    val c = input[index + 1]
                    val color = getByChar(c)
                    if (color != null) {
                        result = color.toString() + result

                        // Once we find a color or reset we can stop searching
                        if (color.isColor || color == RESET) {
                            break
                        }
                    }
                }
            }
            return result
        }

        init {
            for (color in values()) {
                BY_ID[color.intCode] = color
                BY_CHAR[color.char] = color
            }
        }
    }


    private val toString: String
    override fun toString(): String {
        return toString
    }

    /**
     * Checks if this code is a color code as opposed to a format code.
     *
     * @return whether this ChatColor is a color code
     */
    val isColor: Boolean
        get() = !isFormat && this != RESET

    init {
        toString = String(charArrayOf(COLOR_CHAR1, char))
    }

}