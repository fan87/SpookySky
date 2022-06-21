package me.fan87.spookysky.client.utils


import me.fan87.spookysky.client.SpookySky
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL11
import org.lwjgl.util.vector.Vector2f
import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.ceil


/**
 * Made by MemesValkyrie for Faint.
 *
 *
 * Just a little fontrenderer for minecraft I wrote. Should work with any font size
 * without any graphical glitches, but because of this setup takes forever. Feel free to make any edits.
 *
 *
 * Created by Zeb on 12/19/2016.
 */
class CFontRenderer @JvmOverloads private constructor(
    /**
     * The font to be drawn.
     */
    val font: Font, characterCount: Int = 16 * 16 * 16 * 16, fractionalMetrics: Boolean = true
) {

    fun ceiling_double_int(value: Double): Int {
        return ceil(value).toInt()
    }

    /**
     * If fractional metrics should be used in the font renderer.
     */
    private var fractionalMetrics = false

    /**
     * All the character data information (x = type  y = unicode index)
     */
    private val characterData: Array<Array<CharacterData?>>

    /**
     * All the color codes used in minecraft.
     */
    private val colorCodes = IntArray(32)

    private constructor(font: Font, fractionalMetrics: Boolean) : this(font, 16 * 16 * 16 * 16, fractionalMetrics) {}

    init {
        this.fractionalMetrics = fractionalMetrics
        characterData = Array(3) {
            arrayOfNulls(
                characterCount
            )
        }
        for (i in 0..255) {
            characterData[0][i] = getCharacterData(0, i.toChar())
        }
        // Generates all the character textures.
    }

    private fun getCharacterData(type: Int, character: Char): CharacterData? {
        // So it won't need to prerender for like 10 minutes
        if (characterData[type][character.code] != null) return characterData[type][character.code]

        // Quickly generates the colors.
        generateColors()

        // Changes the type of the font to the given type.
        val font = font.deriveFont(type)

        // An image just to get font data.
        val utilityImage = BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)

        // The graphics of the utility image.
        val utilityGraphics = utilityImage.graphics as Graphics2D

        // Sets the font of the utility image to the font.
        utilityGraphics.font = font

        // The font metrics of the utility image.
        val fontMetrics = utilityGraphics.fontMetrics

        // The character at the current index.
        // The width and height of the character according to the font.
        val characterBounds = fontMetrics.getStringBounds(character.toString() + "", utilityGraphics)

        // The width of the character texture.
        val width = characterBounds.width.toFloat() + 2 * MARGIN

        // The height of the character texture.
        val height = characterBounds.height.toFloat()

        // The image that the character will be rendered to.
        var textWidth: Int = ceiling_double_int(width.toDouble())
        var textHeight: Int = ceiling_double_int(height.toDouble())
        textWidth = Math.max(1, textWidth)
        textHeight = Math.max(1, textHeight)
        val characterImage = BufferedImage(textWidth, textHeight, BufferedImage.TYPE_INT_ARGB)
        // The graphics of the character image.
        val graphics = characterImage.graphics as Graphics2D

        // Sets the font to the input font/
        graphics.font = font

        // Sets the color to white with no alpha.
        graphics.color = Color(255, 255, 255, 0)

        // Fills the entire image with the color above, makes it transparent.
        graphics.fillRect(0, 0, characterImage.width, characterImage.height)

        // Sets the color to white to draw the character.
        graphics.color = Color.WHITE

        // Enables anti-aliasing so the font doesn't have aliasing.
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
        graphics.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            if (fractionalMetrics) RenderingHints.VALUE_FRACTIONALMETRICS_ON else RenderingHints.VALUE_FRACTIONALMETRICS_OFF
        )

        // Draws the character.
        graphics.drawString(character.toString() + "", MARGIN, fontMetrics.ascent)

        // Generates a new texture id.
        val textureId: Int = GL11.glGenTextures()
        // Allocates the texture in opengl.
        createTexture(textureId, characterImage)

        // Initiates the character data and stores it in the data array.
        val characterData1: CharacterData =
            CharacterData(characterImage.width.toFloat(), characterImage.height.toFloat(), textureId)
        characterData[type][character.code] = characterData1
        return characterData1
    }

    /**
     * Uploads the opengl texture.
     *
     * @param textureId The texture id to upload to.
     * @param image     The image to upload.
     */
    private fun createTexture(textureId: Int, image: BufferedImage) {
        // Array of all the colors in the image.
        val pixels = IntArray(image.width * image.height)

        // Fetches all the colors in the image.
        image.getRGB(0, 0, image.width, image.height, pixels, 0, image.width)

        // Buffer that will store the texture data.
        val buffer = BufferUtils.createByteBuffer(image.width * image.height * 4) //4 for RGBA, 3 for RGB

        // Puts all the pixel data into the buffer.
        for (y in 0 until image.height) {
            for (x in 0 until image.width) {

                // The pixel in the image.
                val pixel = pixels[y * image.width + x]

                // Puts the data into the byte buffer.
                buffer.put((pixel shr 16 and 0xFF).toByte())
                buffer.put((pixel shr 8 and 0xFF).toByte())
                buffer.put((pixel and 0xFF).toByte())
                buffer.put((pixel shr 24 and 0xFF).toByte())
            }
        }

        // Flips the byte buffer, not sure why this is needed.
        buffer.flip()

        // Binds the opengl texture by the texture id.
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId)

        // Sets the texture parameter stuff.
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST)
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST)

        // Uploads the texture to opengl.
        GL11.glTexImage2D(
            GL11.GL_TEXTURE_2D,
            0,
            GL11.GL_RGBA,
            image.width,
            image.height,
            0,
            GL11.GL_RGBA,
            GL11.GL_UNSIGNED_BYTE,
            buffer
        )

        // Binds the opengl texture 0. (Unbind)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }

    /**
     * Renders the given string.
     *
     * @param text  The text to be rendered.
     * @param x     The x position of the text.
     * @param y     The y position of the text.
     * @param color The color of the text.
     */
    fun drawString(text: String, x: Float, y: Float, color: Int) {
        renderString(text, x, y, color, false)
    }

    fun drawString(text: String, x: Float, y: Float, color: Int, shadow: Boolean) {
        if (shadow) {
            drawStringWithShadow(text, x, y, color)
        } else {
            drawString(text, x, y, color)
        }
    }

    /**
     * Renders the given string.
     *
     * @param text  The text to be rendered.
     * @param x     The x position of the text.
     * @param y     The y position of the text.
     * @param color The color of the text.
     */
    fun drawStringWithShadow(text: String, x: Float, y: Float, color: Int) {
        GL11.glTranslated(0.5, 0.5, 0.0)
        renderString(text, x, y, color, true)
        GL11.glTranslated(-0.5, -0.5, 0.0)
        renderString(text, x, y, color, false)
    }

    fun drawCenteredStringWithShadow(text: String, x: Float, y: Float, color: Int) {
        drawStringWithShadow(text, x - getStringWidth(text) / 2f, y, color)
    }

    fun drawCenteredString(text: String, x: Float, y: Float, color: Int) {
        drawString(text, x - getStringWidth(text) / 2f, y, color)
    }

    /**
     * Renders the given string.
     *
     * @param text   The text to be rendered.
     * @param x      The x position of the text.
     * @param y      The y position of the text.
     * @param shadow If the text should be rendered with the shadow color.
     * @param color  The color of the text.
     */
    private fun renderString(text: String, x: Float, y: Float, color: Int, shadow: Boolean) {
        // Returns if the text is empty.
        var x = x
        var y = y
        if (text.length == 0) return

        // Pushes the matrix to store gl values.
        GL11.glPushMatrix()

        // Scales down to make the font look better.
        GL11.glScaled(0.5, 0.5, 1.0)

        // Removes half the margin to render in the right spot.
        x -= (MARGIN / 2).toFloat()
        y -= (MARGIN / 2).toFloat()

        // Adds 0.5 to x and y.
        x += 0.5f
        y += 0.5f

        // Doubles the position because of the scaling.
        x *= 2f
        y *= 2f

        // The character texture set to be used. (Regular by default)
        var type = Font.PLAIN

        // Booleans to handle the style.
        var underlined = false
        var strikethrough = false
        var obfuscated = false

        // The length of the text used for the draw loop.
        val length = text.length

        // The multiplier.
        val multiplier = (if (shadow) 4 else 1).toFloat()
        val a = (color shr 24 and 255).toFloat() / 255f
        val r = (color shr 16 and 255).toFloat() / 255f
        val g = (color shr 8 and 255).toFloat() / 255f
        val b = (color and 255).toFloat() / 255f
        GL11.glColor4f(r / multiplier, g / multiplier, b / multiplier, a)

        // Loops through the text.
        for (i in 0 until length) {
            // The character at the index of 'i'.
            var character = text[i]

            // The previous character.
            val previous = if (i > 0) text[i - 1] else '.'

            // Continues if the previous color was the color invoker.
            if (previous == COLOR_INVOKER) continue

            // Sets the color if the character is the color invoker and the character index is less than the length.
            if (character == COLOR_INVOKER && i < length) {

                // The color index of the character after the current character.
                var index = "0123456789abcdefklmnor".indexOf(text.lowercase()[i + 1])

                // If the color character index is of the normal color invoking characters.
                if (index < 16) {
                    // Resets all the styles.
                    obfuscated = false
                    strikethrough = false
                    underlined = false

                    // Sets the character data to the regular type.
                    type = Font.PLAIN

                    // Clamps the index just to be safe in case an odd character somehow gets in here.
                    if (index < 0 || index > 15) index = 15

                    // Adds 16 to the color index to get the darker shadow color.
                    if (shadow) index += 16

                    // Gets the text color from the color codes array.
                    val textColor = colorCodes[index]

                    // Sets the current color.
                    GL11.glColor4d(
                        (textColor shr 16) / 255.0,
                        (textColor shr 8 and 255) / 255.0,
                        (textColor and 255) / 255.0,
                        a.toDouble()
                    )
                } else if (index == 16) obfuscated =
                    true else if (index == 17) // Sets the character data to the bold type.
                    type = Font.BOLD else if (index == 18) strikethrough = true else if (index == 19) underlined =
                    true else if (index == 20) // Sets the character data to the italics type.
                    type = Font.ITALIC else if (index == 21) {
                    // Resets the style.
                    obfuscated = false
                    strikethrough = false
                    underlined = false

                    // Sets the character data to the regular type.
                    type = Font.PLAIN

                    // Sets the color to white
                    GL11.glColor4d(
                        1.0 * if (shadow) 0.25 else 1.0,
                        1.0 * if (shadow) 0.25 else 1.0,
                        1.0 * if (shadow) 0.25 else 1.0,
                        a.toDouble()
                    )
                }
            } else {
                // Continues to not crash!
                if (character.code > characterData[0].size - 1) continue

                // Sets the character to a random char if obfuscated is enabled.
                if (obfuscated) character = (character.code + RANDOM_OFFSET).toChar()

                // Draws the character.
                drawChar(character, type, x, y)

                // The character data for the given character.
                val charData = getCharacterData(type, character)

                // Draws the strikethrough line if enabled.
                if (strikethrough) drawLine(
                    Vector2f(0f, charData!!.height / 2f), Vector2f(
                        charData.width, charData.height / 2f
                    ), 3f
                )

                // Draws the underline if enabled.
                if (underlined) drawLine(
                    Vector2f(0f, charData!!.height - 15), Vector2f(
                        charData.width, charData.height - 15
                    ), 3f
                )

                // Adds to the offset.
                x += charData!!.width - 2 * MARGIN
            }
        }

        // Restores previous values.
        GL11.glPopMatrix()

        // Sets the color back to white so no odd rendering problems happen.
        GL11.glColor4d(1.0, 1.0, 1.0, 1.0)
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }

    val height: Int
        get() = getHeight("Ay@|").toInt()

    // Alias for getHeight
    fun getStringHeight(text: String): Int {
        return getHeight(text).toInt()
    }

    // Alias for getWidth
    fun getStringWidth(text: String): Int {
        return getWidth(text).toInt()/2 - text.length * 2
    }

    /**
     * Gets the width of the given text.
     *
     * @param text The text to get the width of.
     * @return The width of the given text.
     */
    fun getWidth(text: String): Float {

        // The width of the string.
        var width = 0f

        // The character texture set to be used. (Regular by default)
        var type = Font.PLAIN

        // The length of the text.
        val length = text.length

        // Loops through the text.
        for (i in 0 until length) {
            // The character at the index of 'i'.
            val character = text[i]

            // The previous character.
            val previous = if (i > 0) text[i - 1] else '.'

            // Continues if the previous color was the color invoker.
            if (previous == COLOR_INVOKER) continue

            // Sets the color if the character is the color invoker and the character index is less than the length.
            if (character == COLOR_INVOKER && i < length) {

                // The color index of the character after the current character.
                val index = "0123456789abcdefklmnor".indexOf(text.lowercase()[i + 1])
                if (index == 17) // Sets the character data to the bold type.
                    type = Font.BOLD else if (index == 20) // Sets the character data to the italics type.
                    type = Font.ITALIC else if (index == 21) // Sets the character data to the regular type.
                    type = Font.PLAIN
            } else {
                // Continues to not crash!
                if (character.code > 255) continue

                // The character data for the given character.
                val charData = getCharacterData(type, character)

                // Adds to the offset.
                width += charData!!.width - 2 * MARGIN / 2
            }
        }

        // Returns the width.
        return width + MARGIN / 2
    }

    /**
     * Gets the height of the given text.
     *
     * @param text The text to get the height of.
     * @return The height of the given text.
     */
    fun getHeight(text: String): Float {

        // The height of the string.
        var height = 0f

        // The character texture set to be used. (Regular by default)
        var type = Font.PLAIN

        // The length of the text.
        val length = text.length

        // Loops through the text.
        for (i in 0 until length) {
            // The character at the index of 'i'.
            val character = text[i]

            // The previous character.
            val previous = if (i > 0) text[i - 1] else '.'

            // Continues if the previous color was the color invoker.
            if (previous == COLOR_INVOKER) continue

            // Sets the color if the character is the color invoker and the character index is less than the length.
            if (character == COLOR_INVOKER && i < length) {

                // The color index of the character after the current character.
                val index = "0123456789abcdefklmnor".indexOf(text.lowercase()[i + 1])
                if (index == 17) // Sets the character data to the bold type.
                    type = Font.BOLD else if (index == 20) // Sets the character data to the italics type.
                    type = Font.ITALIC else if (index == 21) // Sets the character data to the regular type.
                    type = Font.PLAIN
            } else {
                // Continues to not crash!
                if (character.code > 255) continue

                // The character data for the given character.
                val charData = getCharacterData(type, character)

                // Sets the height if its bigger.
                height = Math.max(height, charData!!.height)
            }
        }

        // Returns the height.
        return height / 2 - MARGIN / 2
    }

    /**
     * Draws the character.
     *
     * @param character     The character to be drawn.
     * @param type          Char type, can be 0 to 2, or Font.PLAIN, Font.BOLD, Font.ITALIC
     */
    private fun drawChar(character: Char, type: Int, x: Float, y: Float) {
        // The char data that stores the character data.
        val charData = getCharacterData(type, character)

        // Binds the character data texture.
        charData!!.bind()
        GL11.glPushMatrix()

        // Enables blending.
        GL11.glEnable(GL11.GL_BLEND)

        // Sets the blending function.
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA)

        // Begins drawing the quad.
        GL11.glBegin(GL11.GL_QUADS)
        run {

            // Maps out where the texture should be drawn.
            GL11.glTexCoord2f(0f, 0f)
            GL11.glVertex2d(x.toDouble(), y.toDouble())
            GL11.glTexCoord2f(0f, 1f)
            GL11.glVertex2d(x.toDouble(), (y + charData.height).toDouble())
            GL11.glTexCoord2f(1f, 1f)
            GL11.glVertex2d((x + charData.width).toDouble(), (y + charData.height).toDouble())
            GL11.glTexCoord2f(1f, 0f)
            GL11.glVertex2d((x + charData.width).toDouble(), y.toDouble())
        }
        // Ends the quad.
        GL11.glEnd()
//        println(x.toString() + ", " + y + ", " +  (x + charData.width) + ", " + (y + charData.height))
//        RenderUtils.drawRect(x.toDouble(), y.toDouble(), (x + charData.width).toDouble(), y + charData.height.toDouble(), 0xffffffff.toInt())
        GL11.glDisable(GL11.GL_BLEND)
        GL11.glPopMatrix()

        // Binds the opengl texture by the texture id.
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0)
    }

    /**
     * Draws a line from start to end with the given width.
     *
     * @param start The starting point of the line.
     * @param end   The ending point of the line.
     * @param width The thickness of the line.
     */
    private fun drawLine(start: Vector2f, end: Vector2f, width: Float) {
        // Disables textures so we can draw a solid line.
        GL11.glDisable(GL11.GL_TEXTURE_2D)

        // Sets the width.
        GL11.glLineWidth(width)

        // Begins drawing the line.
        GL11.glBegin(GL11.GL_LINES)
        run {
            GL11.glVertex2f(start.x, start.y)
            GL11.glVertex2f(end.x, end.y)
        }
        // Ends drawing the line.
        GL11.glEnd()

        // Enables texturing back on.
        GL11.glEnable(GL11.GL_TEXTURE_2D)
    }

    /**
     * Generates all the colors.
     */
    private fun generateColors() {
        // Iterates through 32 colors.
        for (i in 0..31) {
            // Not sure what this variable is.
            val thingy = (i shr 3 and 1) * 85

            // The red value of the color.
            var red = (i shr 2 and 1) * 170 + thingy

            // The green value of the color.
            var green = (i shr 1 and 1) * 170 + thingy

            // The blue value of the color.
            var blue = (i shr 0 and 1) * 170 + thingy

            // Increments the red by 85, not sure why does this in minecraft's font renderer.
            if (i == 6) red += 85

            // Used to make the shadow darker.
            if (i >= 16) {
                red /= 4
                green /= 4
                blue /= 4
            }

            // Sets the color in the color code at the index of 'i'.
            colorCodes[i] = red and 255 shl 16 or (green and 255 shl 8) or (blue and 255)
        }
    }

    /**
     * Class that holds the data for each character.
     */
    internal inner class CharacterData(
        /**
         * The width of the character.
         */
        var width: Float,
        /**
         * The height of the character.
         */
        var height: Float,
        /**
         * The id of the character texture.
         */
        private val textureId: Int
    ) {
        /**
         * Binds the texture.
         */
        fun bind() {
            // Binds the opengl texture by the texture id.
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId)
        }
    }

    data class FontRendererData(val fontLocation: String, val fontSize: Int)

    companion object {
        /**
         * The margin on each texture.
         */
        private const val MARGIN = 4

        /**
         * The character that invokes color in a string when rendered.
         */
        private const val COLOR_INVOKER = '\u00a7'

        /**
         * The random offset in obfuscated text.
         */
        private const val RANDOM_OFFSET = 1

        val cachedFontRenderer = HashMap<FontRendererData, CFontRenderer>()

        fun getFontRenderer(fontLocation: String, fontSize: Int): CFontRenderer {
            val fontRendererData = FontRendererData(fontLocation, fontSize)
            return cachedFontRenderer[fontRendererData]
                ?: CFontRenderer(Font.createFont(0, SpookySky.getResourceAsStream(fontLocation)).deriveFont(Font.PLAIN, fontSize.toFloat()), 16 * 16 * 16 * 16, true).also { cachedFontRenderer[fontRendererData] = it }
        }
    }
}
