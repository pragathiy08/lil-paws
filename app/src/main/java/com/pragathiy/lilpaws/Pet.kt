package com.pragathiy.lilpaws

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Pet() {

    lateinit var type: PetType
    lateinit var name: String
    var age: Int = 0

    constructor(type: PetType, name: String, age: Int) : this() {
        this.type = type
        this.name = name
        this.age = age
    }

    enum class PetType {
        DOG, CAT, BIRD
    }
}