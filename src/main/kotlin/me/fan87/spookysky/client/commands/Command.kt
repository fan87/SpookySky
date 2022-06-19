package me.fan87.spookysky.client.commands

import me.fan87.spookysky.client.SpookySky

abstract class Command(
    val name: String,
    val description: String,
    val argumentsUsage: String,
    vararg val aliases: String
) {

    internal var args: Array<String>? = null

    protected val spookySky
        get() = SpookySky.INSTANCE

    abstract fun onCommand(args: Array<String>)
    open fun onTabComplete(args: Array<String>): List<String> {
        return arrayListOf()
    }

    protected fun assertArgumentCount(amount: Int) = assertArgumentCount(amount..amount)

    protected fun assertArgumentCount(range: IntProgression) {
        if (args!!.size !in range) {
            failIncorrectUsage()
        }
    }

    protected fun failIncorrectUsage(): Nothing {
        fail("Incorrect Usage! Usage: $name $argumentsUsage")
    }

    protected fun fail(reason: String): Nothing {
        throw CommandException(reason)
    }

}