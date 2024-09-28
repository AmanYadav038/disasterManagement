package com.example.hazardhub.dataclass

data class Department(
    val id : String = "",
    val name: String = "",
    val email : String = "",
    val password : String = "",
    val stateOfOperation:ArrayList<String> = arrayListOf()
)
