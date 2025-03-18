package com.example.hostelhubuser.Data

class HostelDetails(
    var hostelName: String,
    var hostelWarden: String,
    var hostelWardenContact: String,
    var hostelWardenEmail: String
)

val details = listOf(
    HostelDetails("BOYS", "Mr. Abc Dex", "1234567890", "boyshostel@iiitk.ac.in"),
    HostelDetails("GIRLS", "Mr. XYZ SDF", "1234567891", "girlshostel@iiitk.ac.in"),

)
