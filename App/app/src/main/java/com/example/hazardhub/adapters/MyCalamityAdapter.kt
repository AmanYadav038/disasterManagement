package com.example.hazardhub.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hazardhub.DetailedCalamityActivity
import com.example.hazardhub.R
import com.example.hazardhub.dataclass.Project

class MyCalamityAdapter(val context: Context, val dataList : List<Project>) : RecyclerView.Adapter<MyCalamityAdapter.VH>() {

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.calamityImageView)
        val titleTextView: TextView = itemView.findViewById(R.id.calamityTitleTextView)
        val pincodeTextView: TextView = itemView.findViewById(R.id.calamityPincodeTextView)
        val affectedPeopleTextView: TextView = itemView.findViewById(R.id.affectedPeopleTextView)
        val calamityView : CardView = itemView.findViewById(R.id.calamity_card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calamity_item,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val project = dataList[position]

        // Bind project data to the views
        holder.titleTextView.text = project.title
        holder.pincodeTextView.text = project.pincode
        holder.affectedPeopleTextView.text = project.approx_no_of_affected_people.toString()

        // Load image if imageUrl is not null
        if (!project.imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(project.imageUrl)
                .placeholder(R.drawable.loading) // Replace with your placeholder image
                .into(holder.imageView)
        } else {
            holder.imageView.setImageResource(R.drawable.loading)
        }

        val address = "${project.address_line1}, ${project.address_line2}, ${project.state}"

        // Set click listener on the card view (if needed)
        holder.calamityView.setOnClickListener {
            val intent = Intent(context, DetailedCalamityActivity::class.java)
            intent.putExtra("calamityImage",project.imageUrl)
            intent.putExtra("calamityTitle",project.title)
            intent.putExtra("calamityAffected",project.approx_no_of_affected_people)
            intent.putExtra("location",address)
            intent.putExtra("pincodeCalamity",project.pincode)
            intent.putExtra("calamityDescription",project.description)
            context.startActivity(intent)
        }
    }
}