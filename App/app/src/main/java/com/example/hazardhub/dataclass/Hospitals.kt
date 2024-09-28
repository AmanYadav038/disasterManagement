package com.example.hazardhub.dataclass

data class Hospitals(
    val hospitalId : String = "",
    val name : String ="",
    val email : String = "",
    val password : String = "",
    val numberOfBeds : Int = 0,
    val timings :String = "",
    val address : String = "",
    val state : String = "",
    val pincode : String = "",
    val phoneNumber : String = ""
)
