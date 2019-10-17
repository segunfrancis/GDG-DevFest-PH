package com.android.segunfrancis.gdgph.model

data class Chat(var msgText: String, var msgUser: String) {
    constructor() : this("", "")
}