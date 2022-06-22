package me.fan87.spookysky.client.events.events

class GuiChatMessageEvent(var message: String) {
    var cancelled = false
}
class AutoCompleteEvent(var message: String) {
    var cancelled = false
}
class KeyEvent(val key: Int)