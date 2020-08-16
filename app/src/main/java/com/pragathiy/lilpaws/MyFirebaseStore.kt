package com.pragathiy.lilpaws

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyFirebaseStore {

    private lateinit var firebaseUser: FirebaseUser

    companion object {
        @JvmStatic
        fun getInstance(firebaseUser: FirebaseUser) = MyFirebaseStore().apply {
            this.firebaseUser = firebaseUser
        }
    }

    val pets: DatabaseReference by lazy {
        Firebase.database.reference.child("pets")
    }
}