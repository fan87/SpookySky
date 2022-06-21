package me.fan87.spookysky.client.utils

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.rendering.Gui
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.ARBFramebufferObject
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL12
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

object RenderUtils {

    private val files = HashMap<String, Int>()

    fun drawTexturedRect(fileName: String, left: Double, top: Double, textureStartX: Float, textureStartY: Float, right: Double, bottom: Double, textureEndX: Float, textureEndY: Float) {
        var texture: Int? = files[fileName]
        if (texture == null) {
            val image = ImageIO.read(SpookySky.getResourceAsStream(fileName))
            texture = glGenTextures()
            glBindTexture(GL_TEXTURE_2D, texture)
            glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE.toFloat())
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR.toFloat())
            glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR.toFloat())
            val iArray = IntArray(image.getHeight() * image.getWidth())
            image.getRGB(0, 0, image.getWidth(), image.getHeight(), iArray, 0, image.getWidth())
            val createIntBuffer = BufferUtils.createIntBuffer(image.getHeight() * image.getWidth())
            for (i in iArray) {
                createIntBuffer.put(i)
            }
            createIntBuffer.flip()
            glTexImage2D(
                GL_TEXTURE_2D,
                0,
                GL_RGBA,
                image.getWidth(),
                image.getHeight(),
                0,
                GL12.GL_BGRA,
                GL12.GL_UNSIGNED_INT_8_8_8_8_REV,
                createIntBuffer
            )
            ARBFramebufferObject.glGenerateMipmap(GL_TEXTURE_2D)
            println(texture)
            files[fileName] = texture
        } else {
            glBindTexture(GL_TEXTURE_2D, texture)
        }

        glPushAttrib(GL_ALL_ATTRIB_BITS)
        glEnable(GL_BLEND)
        glEnable(GL_TEXTURE_2D)
        glColor4f(1f, 1f, 1f, 1f)
        glBegin(GL_QUADS)
        glTexCoord2d(textureStartX.toDouble(), textureEndY.toDouble())
        glVertex2d(left, bottom)
        glTexCoord2d(textureEndX.toDouble(), textureEndY.toDouble())
        glVertex2d(right, bottom)
        glTexCoord2d(textureEndX.toDouble(), textureStartY.toDouble())
        glVertex2d(right, top)
        glTexCoord2d(textureStartX.toDouble(), textureStartY.toDouble())
        glVertex2d(left, top)
        glEnd()
        glPopAttrib()
    }

    fun drawGradient(left: Int, top: Int, right: Int, bottom: Int, startColor: Long, endColor: Long) {
        Gui().drawGradient(left, top, right, bottom, startColor, endColor)
    }

    fun drawRect(left: Double, top: Double, right: Double, bottom: Double, color: Int) {
        var left = left
        var top = top
        var right = right
        var bottom = bottom
        if (left < right) {
            val i: Double = left
            left = right
            right = i
        }

        if (top < bottom) {
            val j: Double = top
            top = bottom
            bottom = j
        }
        glPushAttrib(GL_ALL_ATTRIB_BITS)
        glEnable(GL_BLEND)
        glDisable(GL_TEXTURE_2D)
        val f3 = (color shr 24 and 255).toFloat() / 255.0f
        val f = (color shr 16 and 255).toFloat() / 255.0f
        val f1 = (color shr 8 and 255).toFloat() / 255.0f
        val f2 = (color shr 0 and 255).toFloat() / 255.0f
        glColor4f(f, f1, f2, f3)
        glBegin(GL_QUADS)
        glVertex2d(left, bottom)
        glVertex2d(right, bottom)
        glVertex2d(right, top)
        glVertex2d(left, top)
        glEnd()
        glPopAttrib()
    }

}