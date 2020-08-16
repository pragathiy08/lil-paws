package com.pragathiy.lilpaws

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Pet {

    var key: String? = null
    var type: String? = null
    var name: String? = null
    var age: Int? = null
    var owner: String? = null

}