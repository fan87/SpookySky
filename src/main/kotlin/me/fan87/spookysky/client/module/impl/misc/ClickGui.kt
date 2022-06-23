package me.fan87.spookysky.client.module.impl.misc

import me.fan87.spookysky.client.events.EventHandler
import me.fan87.spookysky.client.events.events.*
import me.fan87.spookysky.client.module.Category
import me.fan87.spookysky.client.module.Module
import me.fan87.spookysky.client.module.settings.Setting
import me.fan87.spookysky.client.module.settings.impl.*
import me.fan87.spookysky.client.utils.*
import org.lwjgl.input.Keyboard
import org.lwjgl.input.Mouse
import org.lwjgl.opengl.GL11
import us.ihmc.euclid.matrix.RotationMatrix
import java.awt.Color
import kotlin.math.*


class ClickGui: Module("ClickGui", "A gui that allows you to manage every module in the client", Category.MISC, true) {
    var startYaw: Float = 0f


    var distance = 0.0
    var scale = 0.0
    
    val pitch = DoubleSetting("Pitch", "The angle of the ClickGui to be rendered", -30.0, -90.0, 90.0)
    val distanceSetting = DoubleSetting("Distance", "The distance (Block) between you and ClickGui", 0.3, 0.1, 4.0)
    val scaleSetting = DoubleSetting("Scale", "The scale of the ClickGui", 1.0, 0.1, 10.0)

    val xOffset = DoubleSetting("X Offset", "The X position it's gonna be rendered at", 5.0, 0.0, 10.0)

    var currentXOffset = 0.0
    
    init {
        key.value = Keyboard.KEY_INSERT
    }

    override fun onEnable() {
        startYaw = mc.thePlayer?.rotationYaw?.plus(0f) ?:0f
        distance = distanceSetting.value
        scale = scaleSetting.value
        pitch.value = -mc.thePlayer!!.rotationPitch.toDouble()
        hidden = false
        currentXOffset = xOffset.value
    }

    override fun onDisable() {

    }

    @EventHandler
    fun onLeftClick(event: ClickMouseEvent) {
        val mousePos = getMousePosition()
        if (onClick(0, mousePos.x, mousePos.y)) {
            mc.gameSettings!!.keyBindAttack.unpresskey()
            event.cancelled = true
        }
    }
    @EventHandler
    fun onRightClick(event: RightClickMouseEvent) {
        val mousePos = getMousePosition()
        if (onClick(1, mousePos.x, mousePos.y)) {
            mc.gameSettings!!.keyBindUseItem.unpresskey()
            event.cancelled = true
        }
    }

    @EventHandler
    fun onTick(event: ClientTickEvent) {
        mc.gameSettings!!.keyBindAttack.unpresskey()
    }

    @EventHandler
    fun onRender(event: PostRender3DEvent) {
        val player = mc.thePlayer!!
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glPushMatrix()
        GL11.glLoadIdentity()
        mc.entityRenderer!!.orientCamera(event.partialTicks)
        GL11.glTranslated(0.0, player.getEyeHeight().toDouble(), 0.0)


        GL11.glRotated(-startYaw % 360.0, 0.0, 1.0, 0.0)
        GL11.glRotated(-pitch.value, 1.0, 0.0, 0.0)
        GL11.glScaled(scale*1.0, scale*1.0, 1.0)
        GL11.glTranslated(-0.5, -0.5, distance)
        GL11.glEnable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_BLEND)

        render()
        GL11.glPopMatrix()
        GL11.glPopAttrib()
    }

    var grabbing = false
    var grabYaw = 0f
    var grabPitch = 0.0
    var hidden = false

    fun onClick(button: Int, posX: Double, posY: Double): Boolean {
        if (clickGrab(button)) return true
        if (hidden) return false
        if (categoriesClick(button, posX, posY)) return true
        return false
    }

    fun render() {
        renderGrab()
        if (hidden) return
        renderCursor()
        renderCategories()
    }

    var selectedCategory: Category? = null

    val grabAnimation = TwoWayAnimationTimer(250)

    fun renderGrab() {
        var left = currentXOffset/10.0
        var top = 0.6 + 0.053
        var right = left + 0.05
        var bottom = top + 0.05
        if (isInSection(left, top, right, bottom) || grabbing) {
            grabAnimation.inAnimation()
        } else {
            grabAnimation.outAnimation()
        }
        GL11.glColor4f(1f, 1f, 1f, grabAnimation.getValue(1.0f, 0.7f))
        RenderUtils.drawTexturedRect("clickgui/${if (grabbing) "selected" else "unselected"}/Base.png", left, top, 1f, 1f, right, bottom, 0f, 0f)

        if (grabbing) {
            startYaw = mc.thePlayer!!.rotationYaw - grabYaw
            pitch.value = -mc.thePlayer!!.rotationPitch - grabPitch
        }
    }

    fun clickGrab(button: Int): Boolean {
        if (grabbing) {
            grabbing = false
            return true
        } else {
            val left = currentXOffset/10.0
            val top = 0.6 + 0.053
            val right = left + 0.05
            val bottom = top + 0.05
            if (isInSection(left, top, right, bottom)) {
                if (button == 1) {
                    hidden = !hidden
                } else {
                    grabbing = true
                    grabYaw = mc.thePlayer!!.rotationYaw - startYaw
                    grabPitch = -mc.thePlayer!!.rotationPitch - pitch.value
                }
                return true
            }
        }
        return false
    }

    val categoriesAnimation = DefaultHashMap<Category, TwoWayAnimationTimer>({TwoWayAnimationTimer(250)})

    fun renderCategories() {
        for (value in Category.values().withIndex()) {
            var left = currentXOffset/10.0
            var top = 0.6 + value.index * -0.053
            var right = left + 0.05
            var bottom = top + 0.05
            if (isInSection(left, top, right, bottom) || value.value == selectedCategory) {
                categoriesAnimation[value.value].inAnimation()
            } else {
                categoriesAnimation[value.value].outAnimation()
            }
            GL11.glColor4f(1f, 1f, 1f, categoriesAnimation[value.value].getValue(1.0f, 0.7f))
            RenderUtils.drawTexturedRect("clickgui/${if (value.value == selectedCategory) "selected" else "unselected"}/${value.value.displayName}.png", left, top, 1f, 1f, right, bottom, 0f, 0f)
            if (value.value == selectedCategory) {
                val color = Color(0xECAD17)
                GL11.glColor4f(color.red/255f, color.green/255f, color.blue/255f, 1f)
                val pointedPosX = left - 0.017
                val pointedPosY = top + (bottom - top) / 2.0
                val renderPointingLeftTriangle = renderPointingLeftTriangle(pointedPosX, pointedPosY)
                renderModules(pointedPosX - renderPointingLeftTriangle - 0.005, pointedPosY)
            }
        }
    }
    val modulesAnimation = DefaultHashMap<Module, TwoWayAnimationTimer>({TwoWayAnimationTimer(250)})

    var selectedModule: Module? = null

    fun renderModules(midX: Double, midY: Double) {
        val regularFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 30)
        val modules = spookySky.modulesManager.modules.filter { it.category == selectedCategory }
        val moduleDisplayHeight = 0.03
        val moduleDisplayWidth = 0.13
        val moduleMargin = 0.002
        val moduleTextPaddingLeft = 0.01
        val lineHeightHalf = ((moduleDisplayHeight + moduleMargin) * modules.size - moduleDisplayHeight) / 2
        RenderUtils.drawRect(midX + 0.002, midY + lineHeightHalf, midX + 0.003, midY - lineHeightHalf, 0xfffffffff.toInt())
        val allStartY = midY - (modules.size * (moduleDisplayHeight + moduleMargin)).let { if (modules.isNotEmpty()) it - moduleMargin else it }/2.0
        for (entry in modules.withIndex()) {
            val module = entry.value
            val index = entry.index
            val startX = midX
            val startY = allStartY + index * (moduleDisplayHeight + moduleMargin)
            val endX = midX - moduleDisplayWidth
            val endY = startY + moduleDisplayHeight
            var color: Int
            if (module == selectedModule) {
                renderSettings(endX - 0.006, endY - moduleDisplayHeight / 2.0)
            }
            if (module.toggled) {
                modulesAnimation[module].inAnimation()
                color = 0xEBA601;

            } else {
                if (isInSection(startX, startY, endX, endY)) {
                    modulesAnimation[module].inAnimation()
                } else {
                    modulesAnimation[module].outAnimation()
                }
                color = 0xFFFFFF;
            }
            val finalColor = (modulesAnimation[module].getValue(1.0f, 0.7f)*255).toInt() shl 24 or color
            RenderUtils.drawRect(startX, startY, endX, endY, finalColor)
            GL11.glEnable(GL11.GL_TEXTURE_2D)
            startDrawString((startX - moduleTextPaddingLeft), (startY + moduleDisplayHeight/2.0))
            regularFont.drawVerticallyCenteredString(module.name, 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
            GL11.glDisable(GL11.GL_TEXTURE_2D)
        }
    }

    fun moduleClick(button: Int, posX: Double, posY: Double, midX: Double, midY: Double): Boolean {
        val regularFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 30)
        val modules = spookySky.modulesManager.modules.filter { it.category == selectedCategory }
        val moduleDisplayHeight = 0.03
        val moduleDisplayWidth = 0.13
        val moduleMargin = 0.002
        val allStartY = midY - (modules.size * (moduleDisplayHeight + moduleMargin)).let { if (modules.isNotEmpty()) it - moduleMargin else it }/2.0


        for (entry in modules.withIndex()) {
            val module = entry.value
            val index = entry.index
            val startX = midX
            val startY = allStartY + index * (moduleDisplayHeight + moduleMargin)
            val endX = midX - moduleDisplayWidth
            val endY = startY + moduleDisplayHeight
            if (module == selectedModule) {
                val settingsClick = settingsClick(endX - 0.006, endY - moduleDisplayHeight / 2.0, button, posX, posY)
                if (settingsClick) return true
            }
            if (isInSection(startX, startY, endX, endY)) {
                if (button == 0) {
                    module.toggled = !module.toggled
                } else {
                    if (module.settings.isNotEmpty()) {
                        if (selectedModule == module) {
                            selectedModule = null
                        } else {
                            selectedModule = module
                        }
                    }
                }
                return true
            }

        }
        return false
    }


    fun categoriesClick(button: Int, posX: Double, posY: Double): Boolean {
        for (value in Category.values().withIndex()) {
            val left = currentXOffset/10.0
            val top = 0.6 + value.index * -0.053
            val right = left + 0.05
            val bottom = top + 0.05
            if (isInSection(left, top, right, bottom)) {
                if (selectedCategory == value.value) {
                    selectedCategory = null
                } else {
                    selectedCategory = value.value
                }
                return true
            }
            if (selectedCategory == value.value) {
                val pointedPosX = left - 0.017
                val pointedPosY = top + (bottom - top) / 2.0
                val renderPointingLeftTriangle = dryRenderPointingLeftTriangle(pointedPosX, pointedPosY)
                if (moduleClick(button, posX, posY, pointedPosX - renderPointingLeftTriangle - 0.005, pointedPosY)) {
                    return true
                }
            }
        }
        return false
    }

    fun dryRenderPointingLeftTriangle(pointedPosX: Double, pointedPosY: Double): Double {
        return 0.015
    }

    fun renderPointingLeftTriangle(pointedPosX: Double, pointedPosY: Double): Double {
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_CULL_FACE)
        GL11.glBegin(GL11.GL_POLYGON)
        GL11.glVertex2d(pointedPosX - 0.015, pointedPosY + 0.005)
        GL11.glVertex2d(pointedPosX - 0.015, pointedPosY - 0.005)
        GL11.glVertex2d(pointedPosX, pointedPosY)
        GL11.glEnd()
        GL11.glEnable(GL11.GL_CULL_FACE)
        return 0.015
    }

    fun renderCursor() {
//        GL11.glTranslated(0.0, 0.0, -0.001)
//        var mousePosition = getMousePosition()
//        RenderUtils.drawRect(0.0, 0.0, 1.0, 1.0, 0x88000000.toInt())
//        RenderUtils.drawRect(mousePosition.x-0.0025, mousePosition.y-0.0025, mousePosition.x+0.0025, mousePosition.y+0.0025, 0xffffffff.toInt())
    }

    fun getRelativeYawPitch(): Vector2d {
        val matrix = RotationMatrix(
            Math.toRadians((mc.thePlayer!!.rotationYaw.toDouble() - 180 - startYaw)%360),
            Math.toRadians(mc.thePlayer!!.rotationPitch.toDouble()),
            0.0)
        matrix.prependPitchRotation(Math.toRadians(-pitch.value.toDouble()))
        return Vector2d(Math.toDegrees(matrix.yaw), Math.toDegrees(matrix.pitch))
    }
    fun getMousePosition(): Vector2d {
        val relativeYawPitch = getRelativeYawPitch()
        val rotationYaw = relativeYawPitch.x
        val rotationPitch = relativeYawPitch.y
        var x = tan(Math.toRadians(-rotationYaw)) * distance
        val yDistance = sqrt(x*x + distance*distance)
        var finalY = tan(Math.toRadians(-rotationPitch)) * yDistance
        return Vector2d(x/scale + 0.5, finalY/scale + 0.5)
    }

    fun drawString(font: CFontRenderer, string: String, x: Double, y: Double, color: Long) {
        font.drawString(string, 0f, 0f, color.toInt())
    }

    fun startDrawString(x: Double, y: Double) {
        GL11.glPushMatrix()
        GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS)
        GL11.glTranslated(x, y, 0.0)
        GL11.glScaled(0.001, 0.001, 1.0)
        GL11.glRotated(180.0, 0.0, 0.0, 1.0)
        GL11.glTranslated(0.0, 0.0, -0.01)
    }

    fun endDrawString() {
        GL11.glPopAttrib()
        GL11.glPopMatrix()
    }

    @EventHandler
    fun onCrossHair(event: CrossHairEvent) {
//        event.cancelled = true
    }

    fun isInSection(startX: Double, startY: Double, endX: Double, endY: Double): Boolean {
        val mousePosition = getMousePosition()
        return mousePosition.x in min(startX, endX)..max(startX, endX) && mousePosition.y in min(startY, endY)..max(startY, endY)
    }


    val cachedSettingsRenderer = HashMap<Setting<*>, SettingRenderer<*>>()

    fun renderSettings(midX: Double, midY: Double) {
        GL11.glColor4f(1f, 1f, 1f, 1f)
        val value = renderPointingLeftTriangle(midX, midY)
        val settings = selectedModule!!.settings
        val settingDisplayHeight = 0.034
        val settingDisplayWidth = 0.25
        val settingMargin = 0.004
        val settingPadding = 0.01
        val allStartY = midY - (settings.size * (settingDisplayHeight + settingMargin)).let { if (settings.isNotEmpty()) it - settingMargin else it }/2.0
        for (entry in settings.withIndex()) {
            val setting = entry.value
            val index = entry.index
            val startX = midX - value - 0.005
            val startY = allStartY + index * (settingDisplayHeight + settingMargin)
            val endX = midX - settingDisplayWidth
            val endY = startY + settingDisplayHeight
            RenderUtils.drawRoundedRect(startX, startY, endX, endY, 0.003, Color.WHITE)
            var renderer = cachedSettingsRenderer[setting]
            if (renderer == null) {
                if (setting is DoubleSetting) {
                    renderer = DoubleSettingRenderer(setting)
                }
                if (setting is IntSetting) {
                    renderer = IntSettingRenderer(setting)
                }
                if (setting is BooleanSetting) {
                    renderer = BooleanSettingRenderer(setting)
                }
                if (setting is EnumSetting) {
                    renderer = EnumSettingRenderer(setting)
                }
                if (setting is StringEnumSetting) {
                    renderer = StringEnumSettingRenderer(setting)
                }
                if (setting is KeySetting) {
                    renderer = KeySettingRenderer(setting)
                }
                if (renderer != null) {
                    cachedSettingsRenderer[setting] = renderer
                }
            }

            renderer?.renderSetting(startX - settingPadding, startY, endX - settingPadding, endY)
        }

    }

    fun settingsClick(midX: Double, midY: Double, button: Int, posX: Double, posY: Double): Boolean {
        val value = dryRenderPointingLeftTriangle(midX, midY)
        val settings = selectedModule!!.settings
        val settingDisplayHeight = 0.034
        val settingDisplayWidth = 0.25
        val settingMargin = 0.004
        val settingPadding = 0.01
        val allStartY = midY - (settings.size * (settingDisplayHeight + settingMargin)).let { if (settings.isNotEmpty()) it - settingMargin else it }/2.0
        for (entry in settings.withIndex()) {
            val setting = entry.value
            val index = entry.index
            val startX = midX - value - 0.005
            val startY = allStartY + index * (settingDisplayHeight + settingMargin)
            val endX = midX - settingDisplayWidth
            val endY = startY + settingDisplayHeight

            var renderer = cachedSettingsRenderer[setting]
            if (renderer == null) {
                if (setting is DoubleSetting) {
                    renderer = DoubleSettingRenderer(setting)
                }
                if (setting is IntSetting) {
                    renderer = IntSettingRenderer(setting)
                }
                if (setting is BooleanSetting) {
                    renderer = BooleanSettingRenderer(setting)
                }
                if (setting is EnumSetting) {
                    renderer = EnumSettingRenderer(setting)
                }
                if (setting is StringEnumSetting) {
                    renderer = StringEnumSettingRenderer(setting)
                }
                if (setting is KeySetting) {
                    renderer = KeySettingRenderer(setting)
                }
                if (renderer != null) {
                    cachedSettingsRenderer[setting] = renderer
                }
            }

            if (renderer?.onMouseClick(startX - settingPadding, startY, endX - settingPadding, endY, button, posX, posY) == true) {
                return true
            }
        }
        return false
    }

    var keyEvent: ((event: KeyEvent)->Unit)? = null

    @EventHandler
    fun onKey(event: KeyEvent) {
        keyEvent?.let { it(event) }
        keyEvent = null
    }

    inner abstract class SettingRenderer<T: Setting<*>>(val setting: T) {
        val regularFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 30)
        fun renderSetting(startX: Double, startY: Double, endX: Double, endY: Double) {
            val width = endX - startX
            val height = endY - startY
            startDrawString(startX, (startY + height/2.0))
            regularFont.drawVerticallyCenteredString(setting.name, 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
            GL11.glTranslated(0.0, 0.0, -0.0001)
            render(startX, startY, endX, endY, width, height)
            GL11.glTranslated(0.0, 0.0, 0.0001)
        }
        abstract fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double)
        abstract fun onMouseClick(startX: Double, startY: Double, endX: Double, endY: Double, button: Int, posX: Double, posY: Double): Boolean
    }

    inner class DoubleSettingRenderer(setting: DoubleSetting): SettingRenderer<DoubleSetting>(setting) {
        override fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double) {
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = min(startX + width * 0.4, startX + width * 0.75);
            val sliderEnd = max(startX + width * 0.4, startX + width * 0.75);
            RenderUtils.drawRoundedRect(sliderStart, startY + height/2.0 - 0.001, sliderEnd, startY + height/2.0 + 0.001, 0.001, Color(0x767676))
            startDrawString(sliderStart, startY + height/2.0)
            valueFont.drawVerticallyCenteredString("${round(setting.value*10.0)/10.0}", 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
            if (Mouse.isButtonDown(0)) {
                if (isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)) {
                    val mousePosition = getMousePosition()
                    val progress = 1.0 - ((mousePosition.x - sliderStart) / (sliderEnd - sliderStart)).coerceAtMost(1.0)
                    setting.value = setting.minValue + (((setting.maxValue - setting.minValue + 1)) * progress)
                }
            }
            GL11.glTranslated(0.0, 0.0, -0.0001)
            RenderUtils.drawCircle(sliderStart + (1.0 - (setting.value - setting.minValue)*1.0/(setting.maxValue - setting.minValue))*(sliderEnd-sliderStart), startY + height/2.0, 0.004, 0.1f, 0xffEBA601.toInt())
            GL11.glTranslated(0.0, 0.0, 0.0001)
        }


        override fun onMouseClick(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            button: Int,
            posX: Double,
            posY: Double
        ): Boolean {
            val width = endX - startX
            val height = endY - startY
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = startX + width * 0.4
            val sliderEnd = startX + width * 0.75
            val mousePosition = getMousePosition()
            return isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)
        }
    }
    inner class StringEnumSettingRenderer(setting: StringEnumSetting): SettingRenderer<StringEnumSetting>(setting) {
        override fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double) {
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = min(startX + width * 0.4, startX + width * 0.8);
            val sliderEnd = max(startX + width * 0.4, startX + width * 0.8);
            startDrawString(sliderEnd, startY + height/2.0)
            valueFont.drawVerticallyCenteredString(setting.value, 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
        }


        override fun onMouseClick(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            button: Int,
            posX: Double,
            posY: Double
        ): Boolean {
            val width = endX - startX
            val height = endY - startY
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = startX + width * 0.4
            val sliderEnd = startX + width * 0.8
            val mousePosition = getMousePosition()
            if (isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)) {
                val nextIndex = (setting.values.indexOf(setting.value) + 1) % (setting.values.size - 1)
                setting.value = setting.values[nextIndex]
                return true
            }
            return false
        }
    }
    inner class EnumSettingRenderer(setting: EnumSetting<*>): SettingRenderer<EnumSetting<*>>(setting) {
        override fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double) {
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = min(startX + width * 0.4, startX + width * 0.8);
            val sliderEnd = max(startX + width * 0.4, startX + width * 0.8);
            startDrawString(sliderEnd, startY + height/2.0)
            valueFont.drawVerticallyCenteredString(setting.value.name, 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
        }


        override fun onMouseClick(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            button: Int,
            posX: Double,
            posY: Double
        ): Boolean {
            val width = endX - startX
            val height = endY - startY
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = startX + width * 0.4
            val sliderEnd = startX + width * 0.8
            val mousePosition = getMousePosition()
            if (isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)) {
                val nextIndex = (setting.values.indexOf(setting.value) + 1) % (setting.values.size - 1)
                (setting as Setting<Any>).value = setting.values[nextIndex]
                return true
            }
            return false
        }
    }
    inner class BooleanSettingRenderer(setting: BooleanSetting): SettingRenderer<BooleanSetting>(setting) {
        override fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double) {
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = min(startX + width * 0.4, startX + width * 0.8);
            val sliderEnd = max(startX + width * 0.4, startX + width * 0.8);
            startDrawString(sliderEnd, startY + height/2.0)
            valueFont.drawVerticallyCenteredString(setting.value.toString(), 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
        }


        override fun onMouseClick(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            button: Int,
            posX: Double,
            posY: Double
        ): Boolean {
            val width = endX - startX
            val height = endY - startY
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = startX + width * 0.4
            val sliderEnd = startX + width * 0.8
            val mousePosition = getMousePosition()
            if (isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)) {
                setting.value = !setting.value
                return true
            }
            return false
        }
    }
    inner class KeySettingRenderer(setting: KeySetting): SettingRenderer<KeySetting>(setting) {

        var lastDisplayed = System.currentTimeMillis()

        var textDisplay: String? = null
        override fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double) {
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = min(startX + width * 0.4, startX + width * 0.8);
            val sliderEnd = max(startX + width * 0.4, startX + width * 0.8);
            startDrawString(sliderEnd, startY + height/2.0)
            valueFont.drawVerticallyCenteredString(textDisplay?:Keyboard.getKeyName(setting.value), 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
            lastDisplayed = System.currentTimeMillis()
        }


        override fun onMouseClick(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            button: Int,
            posX: Double,
            posY: Double
        ): Boolean {
            val width = endX - startX
            val height = endY - startY
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = startX + width * 0.4
            val sliderEnd = startX + width * 0.8
            val mousePosition = getMousePosition()
            if (isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)) {
                textDisplay = "..."
                keyEvent = keyEvent@{
                    if (System.currentTimeMillis() - lastDisplayed > 500) {
                        textDisplay = null
                        return@keyEvent
                    }
                    if (it.key == Keyboard.KEY_BACK) {
                        textDisplay = null
                        return@keyEvent
                    }
                    if (it.key == Keyboard.KEY_DELETE) {
                        textDisplay = null
                        setting.value = 0
                        return@keyEvent
                    }
                    textDisplay = null
                    setting.value = it.key
                }
                return true
            }
            return false
        }
    }
    inner class IntSettingRenderer(setting: IntSetting): SettingRenderer<IntSetting>(setting) {
        override fun render(startX: Double, startY: Double, endX: Double, endY: Double, width: Double, height: Double) {
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = min(startX + width * 0.4, startX + width * 0.75);
            val sliderEnd = max(startX + width * 0.4, startX + width * 0.75);
            RenderUtils.drawRoundedRect(sliderStart, startY + height/2.0 - 0.001, sliderEnd, startY + height/2.0 + 0.001, 0.001, Color(0x767676))
            startDrawString(sliderStart, startY + height/2.0)
            valueFont.drawVerticallyCenteredString("${setting.value}", 0f, 0f, 0xff6B6B6B.toInt())
            endDrawString()
            if (Mouse.isButtonDown(0)) {
                if (isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)) {
                    val mousePosition = getMousePosition()
                    val progress = 1.0 - ((mousePosition.x - sliderStart) / (sliderEnd - sliderStart)).coerceAtMost(1.0)
                    setting.value = setting.minValue + round((setting.maxValue - setting.minValue + 1) * progress).toInt()
                }
            }
            GL11.glTranslated(0.0, 0.0, -0.0001)
            RenderUtils.drawCircle(sliderStart + (1.0 - (setting.value - setting.minValue)*1.0/(setting.maxValue - setting.minValue))*(sliderEnd-sliderStart), startY + height/2.0, 0.004, 0.1f, 0xffEBA601.toInt())
            GL11.glTranslated(0.0, 0.0, 0.0001)
        }


        override fun onMouseClick(
            startX: Double,
            startY: Double,
            endX: Double,
            endY: Double,
            button: Int,
            posX: Double,
            posY: Double
        ): Boolean {
            val width = endX - startX
            val height = endY - startY
            val valueFont = CFontRenderer.getFontRenderer("Jura-SemiBold.ttf", 25)
            val sliderStart = startX + width * 0.4
            val sliderEnd = startX + width * 0.75
            val mousePosition = getMousePosition()
            return isInSection(sliderStart - 0.1, startY, sliderEnd + 0.1, endY)
        }
    }

}