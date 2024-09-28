package com.example.hazardhub

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.hazardhub.adapters.MyHospitalAdapter
import com.example.hazardhub.adapters.MyVolunteerAdapter
import com.example.hazardhub.databinding.ActivityDetailedCalamityBinding
import com.example.hazardhub.dataclass.Hospitals
import com.example.hazardhub.dataclass.Volunteers
import com.google.firebase.firestore.FirebaseFirestore

class DetailedCalamityActivity : AppCompatActivity() {
    lateinit var detailedCalamityBinding : ActivityDetailedCalamityBinding
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        detailedCalamityBinding = ActivityDetailedCalamityBinding.inflate(layoutInflater)
        setContentView(detailedCalamityBinding.root)

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        detailedCalamityBinding.backBtn.setOnClickListener { finish() }

        // Get calamity data from Intent
        val calamityImageUrl = intent.getStringExtra("calamityImage")
        val calamityTitle = intent.getStringExtra("calamityTitle")
        val affectedPeople = intent.getIntExtra("calamityAffected", 0)
        val location = intent.getStringExtra("location")
        val pincode = intent.getStringExtra("pincodeCalamity")
        val calamityDescription = intent.getStringExtra("calamityDescription")

        // Set UI elements
        detailedCalamityBinding.calamityTitleText.text = calamityTitle
        Glide.with(this).load(calamityImageUrl).placeholder(R.drawable.loading).into(detailedCalamityBinding.calamityImage)
        detailedCalamityBinding.calamityAffected.text = affectedPeople.toString()
        detailedCalamityBinding.calamityLocation.setText("$location, $pincode")
        detailedCalamityBinding.calamityDesc.text = calamityDescription

        // Fetch Volunteers and Hospitals by Pincode
        pincode?.let {
            fetchVolunteersByPincode(it)
            fetchHospitalsByPincode(it)
        } ?: run {
            Toast.makeText(this, "Pincode not provided", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchVolunteersByPincode(pincode: String) {
        if (pincode.isNotEmpty()) {
            db.collection("volunteers")
                .whereEqualTo("pincode", pincode)
                .get()
                .addOnSuccessListener { documents ->
                    val volunteerList = documents.map { it.toObject(Volunteers::class.java) }
                    if (volunteerList.isEmpty()) {
                        Toast.makeText(this, "No Volunteers found for the selected pincode.", Toast.LENGTH_SHORT).show()
                    } else {
                        detailedCalamityBinding.volunteerRecycler.apply {
                            layoutManager = LinearLayoutManager(this@DetailedCalamityActivity)
                            adapter = MyVolunteerAdapter(this@DetailedCalamityActivity, volunteerList)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error fetching volunteers: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter a valid pincode.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchHospitalsByPincode(pincode: String) {
        if (pincode.isNotEmpty()) {
            db.collection("hospitals")
                .whereEqualTo("pincode", pincode)
                .get()
                .addOnSuccessListener { documents ->
                    val hospitalList = documents.map { it.toObject(Hospitals::class.java) }
                    if (hospitalList.isEmpty()) {
                        Toast.makeText(this, "No hospitals found for the selected pincode.", Toast.LENGTH_SHORT).show()
                    } else {
                        detailedCalamityBinding.calamityHospitalsRecycler.apply {
                            layoutManager = LinearLayoutManager(this@DetailedCalamityActivity)
                            adapter = MyHospitalAdapter(this@DetailedCalamityActivity, hospitalList)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error fetching hospitals: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please enter a valid pincode.", Toast.LENGTH_SHORT).show()
        }
    }
}
