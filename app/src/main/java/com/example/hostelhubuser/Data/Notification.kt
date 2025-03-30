package com.example.hostelhubuser.Data

import java.time.LocalDate

data class Notification (
    var date: String = LocalDate.now().toString(),
    var id: String = "",
    var message:String="",
    var hostels:String=""
)