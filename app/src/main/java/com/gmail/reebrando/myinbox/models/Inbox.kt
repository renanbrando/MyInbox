package com.gmail.reebrando.myinbox.models

class Inbox {
    companion object Factory {
        fun create(): Inbox = Inbox()
    }
    var objectId: String? = null
    var itemText: String? = null
    var done: Boolean? = false
}