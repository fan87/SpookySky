package me.fan87.spookysky.client.mapping

abstract class WrapperClass protected constructor(val original: Any) {

    protected operator fun invoke(vararg args: Any) {

    }

}