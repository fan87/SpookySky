package me.fan87.spookysky.client.utils

import me.fan87.spookysky.client.SpookySky
import me.fan87.spookysky.client.mapping.impl.Minecraft
import me.fan87.spookysky.client.mapping.impl.rendering.Framebuffer
import me.fan87.spookysky.client.mapping.impl.rendering.Gui
import me.fan87.spookysky.client.render.ShaderProgram
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.*
import org.lwjgl.opengl.GL11.*
import java.awt.Color
import javax.imageio.ImageIO
import kotlin.math.*


object RenderUtils {

    private val files = HashMap<String, Int>()

    fun confirmSetupStencilAttachment() {
        val fbo = Minecraft.getMinecraft().framebufferMc

        if (fbo != null) {
            if (fbo.depthBuffer > -1) {
                setupFBO(fbo)
                fbo.depthBuffer = -1
            }
        }
    }

    fun setupFBO(fbo: Framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer)
        val stencilDepthBuffer = EXTFramebufferObject.glGenRenderbuffersEXT()
        EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, stencilDepthBuffer)
        EXTFramebufferObject.glRenderbufferStorageEXT(
            EXTFramebufferObject.GL_RENDERBUFFER_EXT,
            EXTPackedDepthStencil.GL_DEPTH_STENCIL_EXT,
            Display.getWidth(),
            Display.getHeight()
        )
        EXTFramebufferObject.glFramebufferRenderbufferEXT(
            EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
            EXTFramebufferObject.GL_STENCIL_ATTACHMENT_EXT,
            EXTFramebufferObject.GL_RENDERBUFFER_EXT,
            stencilDepthBuffer
        )
        EXTFramebufferObject.glFramebufferRenderbufferEXT(
            EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
            EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,
            EXTFramebufferObject.GL_RENDERBUFFER_EXT,
            stencilDepthBuffer
        )
    }

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
            files[fileName] = texture
        } else {
            glBindTexture(GL_TEXTURE_2D, texture)
        }

        glPushAttrib(GL_ALL_ATTRIB_BITS)
        glEnable(GL_BLEND)
        glEnable(GL_TEXTURE_2D)
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

    fun drawRectWithShader(shader: ShaderProgram, left: Double, top: Double, right: Double, bottom: Double) {
        shader.startUsing()
        glPushMatrix()
        glTranslated(left, top, 0.0)
        drawRect(0.0, 0.0, right - left, bottom - top, 0xffffffff.toInt())
        glPopMatrix()
        shader.stopUsing()
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
        if (f2 == 1.0f) {
            glDisable(GL_DEPTH_TEST)
            glLineWidth(0.5f)
            glShadeModel(GL_SMOOTH)
            glEnable(GL_LINE_SMOOTH)
            glColor4f(f, f1, f2, f3)
            glBegin(GL_LINES)
            glVertex2d(left, bottom)
            glVertex2d(right, bottom)
            glEnd()
            glBegin(GL_LINES)
            glVertex2d(right, bottom)
            glVertex2d(right, top)
            glEnd()
            glBegin(GL_LINES)
            glVertex2d(right, top)
            glVertex2d(left, top)
            glEnd()
            glBegin(GL_LINES)
            glVertex2d(left, top)
            glVertex2d(left, bottom)
            glEnd()
        }
        glPopAttrib()
    }

    fun drawBorderedRect(left: Double, top: Double, right: Double, bottom: Double, borderWidth: Double, color: Int, borderColor: Int) {
        val xStart = min(left, right)
        val xEnd = max(left, right)
        val yStart = min(top, bottom)
        val yEnd = max(top, bottom)
        drawRect(xStart - borderWidth, yStart - borderWidth, xEnd + borderWidth, yEnd + borderWidth, borderColor)
        glTranslated(0.0, 0.0, -0.00001)
        drawRect(xStart, yStart, xEnd, yEnd, color)
        glTranslated(0.0, 0.0, 0.00001)
    }

    fun drawRoundedRect(startX: Double, startY: Double, endX: Double, endY: Double, cornerRadius: Double, color: Color) {
        drawRoundedRect0(min(startX, endX), min(startY, endY), max(startX, endX) - min(startX, endX), max(startY, endY) - min(startY, endY), cornerRadius, color)
    }

    fun drawRoundedRect0(x: Double, y: Double, width: Double, height: Double, cornerRadius: Double, color: Color) {
        drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB())
        drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB())
        drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB())
        drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color)
        drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color)
        drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color)
        drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color)
    }

    fun drawArc(x: Double, y: Double, radius: Double, startAngle: Int, endAngle: Int, color: Color) {
        glPushMatrix()
        glEnable(3042)
        glDisable(3553)
        glBlendFunc(770, 771)
        glColor4f(
            color.red / 255f,
            color.green / 255f,
            color.blue.toFloat() / 255f,
            color.alpha.toFloat() / 255f
        )
        glBegin(GL_TRIANGLE_FAN)
        glVertex3d(x, y, 0.0)
        for (i in (startAngle / 360.0 * 100).toInt()..(endAngle / 360.0 * 100).toInt()) {
            val angle = Math.PI * 2 * i / 100 + Math.toRadians(180.0)
            glVertex3d(x + sin(angle) * radius, y + cos(angle) * radius, 0.0)
        }
        glEnd()
        glEnable(3553)
        glDisable(3042)
        glPopMatrix()
    }

    fun drawCircle(x: Double, y: Double, radius: Double, howRound: Float, color: Int) {
        val f3 = (color shr 24 and 255).toFloat() / 255.0f
        val f = (color shr 16 and 255).toFloat() / 255.0f
        val f1 = (color shr 8 and 255).toFloat() / 255.0f
        val f2 = (color and 255).toFloat() / 255.0f
        glEnable(GL_BLEND)
        glDisable(GL_TEXTURE_2D)
        glColor4f(f, f1, f2, f3)
        for (i in 0 until Math.round(howRound * 360)) {
            val degree = (i * 1f / Math.round(howRound * 360) * 360 - 180).toDouble()
            val radians = Math.toRadians(degree)
            val degree2 = ((i + 1) * 1f / Math.round(howRound * 360) * 360 - 180).toDouble()
            val radians2 = Math.toRadians(degree2)
            glBegin(GL_TRIANGLE_STRIP)
            glVertex2d(x, y)
            glVertex2d(
                x + radius * Math.sin(radians),
                y + radius * Math.cos(radians)
            )
            glVertex2d(
                x + radius * Math.sin(radians2),
                y + radius * Math.cos(radians2)
            )
            glEnd()
        }
        glEnable(GL_TEXTURE_2D)
        glColor4f(1f, 1f, 1f, 1f)
    }

}