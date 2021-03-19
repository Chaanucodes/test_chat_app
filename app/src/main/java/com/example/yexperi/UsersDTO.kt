package com.example.yexperi

data class UsersDTO(
    var timeStamp: Long? = System.currentTimeMillis(),
    var author : String? = "",
    var message : String? = "",
    var userId : String? = ""
)