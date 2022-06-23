package me.fan87.spookysky.client.render

import me.fan87.spookysky.client.SpookySky
import org.lwjgl.opengl.GL11
import org.lwjgl.opengl.GL20
import java.io.InputStream
import kotlin.system.exitProcess


abstract class ShaderProgram  {
    private val cachedUniform = HashMap<String, Int>()
    private val programHandle: Int
    private val shaders: MutableList<Int> = ArrayList()

    init {
        programHandle = GL20.glCreateProgram()
        loadShader()
        createdShaders.add(this)
    }

    protected abstract fun loadShaderSource(): ShaderSourceBundle
    private fun unloadShader() {
        for (shader in shaders) {
            GL20.glDetachShader(programHandle, shader)
            GL20.glDeleteShader(shader)
        }
    }

    private fun loadShader() {
        shaders.clear()
        val shaderGroup = loadShaderSource()
        var shaderId = loadShader(shaderGroup.fragmentShader.source, GL20.GL_FRAGMENT_SHADER)
        GL20.glAttachShader(programHandle, shaderId)
        shaders.add(shaderId)
        shaderId = loadShader(shaderGroup.vertexShader.source, GL20.GL_VERTEX_SHADER)
        GL20.glAttachShader(programHandle, shaderId)
        shaders.add(shaderId)
        GL20.glLinkProgram(programHandle)
        GL20.glValidateProgram(programHandle)
        initUniform()
    }

    fun reloadShader() {
        unloadShader()
        loadShader()
    }

    fun startUsing() {
        GL20.glUseProgram(programHandle)
        updateUniform()
    }

    fun stopUsing() {
        GL20.glUseProgram(0)
    }

    fun destroy() {
        unloadShader()
        GL20.glDeleteProgram(programHandle)
        createdShaders.remove(this)
    }

    abstract fun updateUniform()
    abstract fun initUniform()
    protected fun registerUniform(name: String): Int {
        val uniformLocation = GL20.glGetUniformLocation(programHandle, name)
        cachedUniform[name] = uniformLocation
        return uniformLocation
    }

    fun getUniform(name: String): Int {
        return cachedUniform[name] ?: registerUniform(name)
    }

    class ShaderSourceBundle() {
        val vertexShader = ShaderSource()
            .fromPath("shaders/vertex.vert")
        val fragmentShader: ShaderSource = ShaderSource()


        inner class ShaderSource() {
            var source: String = ""
            fun fromInputStream(inputStream: InputStream): ShaderSource {
                return fromSource(String(inputStream.readBytes()))
            }

            fun fromSource(source: String): ShaderSource {
                this.source = source
                return this
            }

            fun fromPath(resourcePath: String): ShaderSource {
                return fromInputStream(SpookySky.getResourceAsStream(resourcePath))
            }

            fun end(): ShaderSourceBundle {
                return this@ShaderSourceBundle
            }
        }
    }

    companion object {
        val createdShaders: MutableList<ShaderProgram> = ArrayList()
        private fun loadShader(source: String?, type: Int): Int {
            val shaderId = GL20.glCreateShader(type)
            GL20.glShaderSource(shaderId, source)
            GL20.glCompileShader(shaderId)
            if (GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
                println(GL20.glGetShaderInfoLog(shaderId, 1000))
                System.err.println("Failed to compile shader!")
                exitProcess(-1)
            }
            return shaderId
        }
    }
}
