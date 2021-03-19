package com.example.yexperi

import com.google.firebase.auth.FirebaseAuth


object FirebaseDatas{
    var userID: String = ""

    fun assignUserID(){
        userID = FirebaseAuth.getInstance().currentUser!!.uid
    }
}


