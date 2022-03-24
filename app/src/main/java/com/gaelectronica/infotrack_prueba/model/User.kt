package com.gaelectronica.infotrack_prueba.model

class User() {
    var name: String? = null
    var email: String? = null

    constructor(name: String,  email:String) : this() {
        this.name = name
        this.email = email
    }
}