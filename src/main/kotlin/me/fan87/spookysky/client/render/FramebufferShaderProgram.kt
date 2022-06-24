package me.fan87.spookysky.client.render

import me.fan87.spookysky.client.mapping.impl.Minecraft
import org.lwjgl.opengl.ARBFramebufferObject.*
import org.lwjgl.opengl.Display
import org.lwjgl.opengl.GL11.*
import org.lwjgl.opengl.GL13.*
import java.nio.ByteBuffer


abstract class FramebufferShaderProgram: ShaderProgram() {

    var fbo = -1
    var texture = -1
    var rbo = -1

    var width = 0
    var height = 0
    var oldFramebuffer = -1

    override fun startUsing() {
        oldFramebuffer = glGetInteger(GL_FRAMEBUFFER_BINDING)
        checkFboSetup()
        glBindFramebuffer(GL_FRAMEBUFFER, fbo)
        glClear(GL_COLOR_BUFFER_BIT or GL_STENCIL_BUFFER_BIT or GL_DEPTH_BUFFER_BIT)
    }

    override fun stopUsing() {



        glPushAttrib(GL_ALL_ATTRIB_BITS)
        glPushMatrix()


        glEnable(GL_BLEND)
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA)

        glBindFramebuffer(GL_FRAMEBUFFER, oldFramebuffer)

        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, texture)
        super.startUsing()


        glClear(GL_DEPTH_BUFFER_BIT)

        Minecraft.getMinecraft().entityRenderer!!.setupOverlayRendering()

        glEnable(GL_TEXTURE_2D)
        glBegin(GL_QUADS);
        glTexCoord2d(0.0, 1.0);
        glVertex2d(0.0, 0.0);
        glTexCoord2d(0.0, 0.0);
        glVertex2d(0.0, height.toDouble());
        glTexCoord2d(1.0, 0.0);
        glVertex2d(width.toDouble(), height.toDouble());
        glTexCoord2d(1.0, 1.0);
        glVertex2d(width.toDouble(), 0.0);
        glEnd();
//        super.stopUsing()

        glPopMatrix()
        glPopAttrib()
    }

    private fun checkFboSetup() {
        if (width != Display.getWidth() || height != Display.getHeight() || fbo == -1) {
            setupFbo()
        }
    }

    private fun setupFbo() {
        println("Setting up new fbo")
        oldFramebuffer = glGetInteger(GL_FRAMEBUFFER_BINDING)
        if (fbo != -1) {
            glDeleteFramebuffers(fbo)
        }
        if (rbo != -1) {
            glDeleteRenderbuffers(rbo)
        }
        if (texture != -1) {
            glDeleteTextures(texture)
        }
        width = Display.getWidth()
        height = Display.getHeight()
        fbo = glGenFramebuffers()
        rbo = glGenRenderbuffers()
        texture = glGenTextures()

        // FrameBuffer
        glBindFramebuffer(GL_FRAMEBUFFER, fbo)

        // Texture
        glBindTexture(GL_TEXTURE_2D, texture)
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, null as ByteBuffer?)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0)

        // RenderBuffer
        glBindRenderbuffer(GL_RENDERBUFFER, rbo)
        glRenderbufferStorage(GL_RENDERBUFFER, GL_DEPTH24_STENCIL8, width, height)
        glFramebufferRenderbuffer(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_RENDERBUFFER, rbo)

        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE) {
            println("Framebuffer is not completed yet")
        }

        // Unbind
        glBindFramebuffer(GL_FRAMEBUFFER, oldFramebuffer)



    }

}