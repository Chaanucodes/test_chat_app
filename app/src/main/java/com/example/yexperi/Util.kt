package com.example.yexperi

import java.text.SimpleDateFormat

fun Long.convertLongToDateString(): String {
    return SimpleDateFormat("hh:mm aaa MM-dd-yyyy")
        .format(this).toString()
}