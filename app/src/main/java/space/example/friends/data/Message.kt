package space.example.friends.data

import java.util.*

class Message(
    var messageText: String = "",
    var author: String = "",
) {
    var timeMessage: Long = Date().time

}