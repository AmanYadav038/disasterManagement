package com.example.hazardhub.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hazardhub.R
import com.example.hazardhub.dataclass.Hospitals
import android.widget.Button

class MyHospitalAdapter(
    private val context: Context,
private val hospitalList: List<Hospitals>
) : RecyclerView.Adapter<MyHospitalAdapter.HospitalViewHolder>() {

    // ViewHolder for Hospital items
    class HospitalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.hospital_name)
        val bedsTextView: TextView = itemView.findViewById(R.id.address)
        val callButton: Button = itemView.findViewById(R.id.call_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HospitalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.hospital_available, parent, false)
        return HospitalViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hospitalList.size
    }

    override fun onBindViewHolder(holder: HospitalViewHolder, position: Int) {
        val hospital = hospitalList[position]

        // Bind the hospital data to the views
        holder.nameTextView.text = hospital.name
        holder.bedsTextView.text = "Address: ${hospital.address}, ${hospital.pincode}"
        // Set a click listener on the call button
        holder.callButton.setOnClickListener {
            initiateCall(hospital.phoneNumber)
        }
    }

    private fun initiateCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:$phoneNumber")
        context.startActivity(intent)
    }
}