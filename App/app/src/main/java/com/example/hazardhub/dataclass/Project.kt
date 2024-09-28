package com.example.hazardhub.dataclass

data class Project(
    val id: String? = null, // Optional, this will be populated by Firestore
    val title: String = "", // Provide default value
    val description: String? = null, // Optional
    val approx_no_of_affected_people: Int = 0, // Provide default value
    val address_line1: String = "", // Provide default value
    val address_line2: String = "", // Provide default value
    val state: String = "", // Provide default value
    val pincode: String = "", // Provide default value
    val initiator: String = "", // Reference to the department's email
    val created_at: Long = System.currentTimeMillis(), // Timestamp
    val status: String = "active", // Default status
    val imageUrl: String? = null // Optional
)
