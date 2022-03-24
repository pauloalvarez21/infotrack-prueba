package com.gaelectronica.infotrack_prueba.model

class User() {
    var name: String? = null
    var username: String? = null
    var email: String? = null

    constructor(name: String, username:String, email:String) : this() {
        this.name = name
        this.username = username
        this.email = email
    }
}