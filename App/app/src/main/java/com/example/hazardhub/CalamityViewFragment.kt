package com.example.hazardhub

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hazardhub.adapters.MyCalamityAdapter
import com.example.hazardhub.databinding.FragmentCalamityViewBinding
import com.example.hazardhub.dataclass.Department
import com.example.hazardhub.dataclass.Project
import com.google.firebase.firestore.FirebaseFirestore

class CalamityViewFragment : Fragment() {
    lateinit var calamityViewBinding: FragmentCalamityViewBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var calamityAdapter: MyCalamityAdapter
    private var projectList: MutableList<Project> = mutableListOf() // Mutable list to hold projects

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        updateUIBasedOnAccountType()
        calamityViewBinding = FragmentCalamityViewBinding.inflate(layoutInflater, container, false)

        val options = arrayOf("State", "Pincode", "Project Name")
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, options)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        calamityViewBinding.searchOptionsSpinner.adapter = spinnerAdapter

        // Initialize Firestore
        db = FirebaseFirestore.getInstance()

        // Initialize the RecyclerView and Adapter
        calamityViewBinding.calamityRecycler.layoutManager = LinearLayoutManager(requireContext())
        calamityAdapter = MyCalamityAdapter(requireContext(), projectList)
        calamityViewBinding.calamityRecycler.adapter = calamityAdapter

        calamityViewBinding.searchButton.setOnClickListener {
            when (calamityViewBinding.searchOptionsSpinner.selectedItemPosition) {
                0 -> fetchByState()
                1 -> fetchByPincode()
                2 -> fetchByTitle()
            }
        }

        return calamityViewBinding.root
    }

    private fun fetchByState() {
        val selectedState = calamityViewBinding.searchAutoCompleteTextView.text.toString().trim()
        if (selectedState.isNotEmpty()) {
            db.collection("projects")
                .whereEqualTo("state", selectedState)
                .get()
                .addOnSuccessListener { documents ->
                    projectList.clear() // Clear previous data
                    if (documents.isEmpty) {
                        Toast.makeText(requireContext(), "No projects found for the selected state.", Toast.LENGTH_SHORT).show()
                    } else {
                        for (document in documents) {
                            val project = document.toObject(Project::class.java)
                            projectList.add(project) // Add the project to the list
                        }
                    }
                    calamityAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error fetching projects: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Please enter a state.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchByPincode() {
        val selectedPincode = calamityViewBinding.searchAutoCompleteTextView.text.toString().trim()
        if (selectedPincode.isNotEmpty()) {
            db.collection("projects")
                .whereEqualTo("pincode", selectedPincode)
                .get()
                .addOnSuccessListener { documents ->
                    projectList.clear() // Clear previous data
                    if (documents.isEmpty) {
                        Toast.makeText(requireContext(), "No projects found for the selected pincode.", Toast.LENGTH_SHORT).show()
                    } else {
                        for (document in documents) {
                            val project = document.toObject(Project::class.java)
                            projectList.add(project) // Add the project to the list
                        }
                    }
                    calamityAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error fetching projects: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Please enter a pincode.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchByTitle() {
        val selectedTitle = calamityViewBinding.searchAutoCompleteTextView.text.toString().trim()
        if (selectedTitle.isNotEmpty()) {
            db.collection("projects")
                .whereEqualTo("title", selectedTitle)
                .get()
                .addOnSuccessListener { documents ->
                    projectList.clear() // Clear previous data
                    if (documents.isEmpty) {
                        Toast.makeText(requireContext(), "No projects found for the selected title.", Toast.LENGTH_SHORT).show()
                    } else {
                        for (document in documents) {
                            val project = document.toObject(Project::class.java)
                            projectList.add(project) // Add the project to the list
                        }
                    }
                    calamityAdapter.notifyDataSetChanged() // Notify the adapter of data changes
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error fetching projects: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireContext(), "Please enter a project name.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getUserSession() : String?{
        val sharedPreferences = requireActivity().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        return sharedPreferences.getString("account_type", null)
    }

    private fun updateUIBasedOnAccountType(){
        val accountType = getUserSession()

        if(accountType!= null){
            when(accountType){
                "departments"->{
                    requireActivity().getSharedPreferences("UserSession",Context.MODE_PRIVATE).getString("user_email",null)
                        ?.let { fetchCalamitiesByDepartment(it) }
                }
                "ert"->{

                }
                "hospitals"->{

                }
                "volunteers"->{

                }
            }
        }
    }


    private fun fetchCalamitiesByDepartment(email: String) {
        val db = FirebaseFirestore.getInstance()
        val calamityList = mutableListOf<Project>() // List to store fetched calamities

        if (email.isNotEmpty()) {
            // Fetch department details to get states of operation
            db.collection("departments")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        for (document in documents) {
                            val department = document.toObject(Department::class.java)
                            val statesOfOperation = department.stateOfOperation // Assuming this is an array of states

                            // Fetch calamities based on states of operation
                            db.collection("projects")
                                .whereIn("state", statesOfOperation)
                                .get()
                                .addOnSuccessListener { calamityDocuments ->
                                    if (calamityDocuments.isEmpty) {
                                        Toast.makeText(requireActivity(), "No calamities found for the department.", Toast.LENGTH_SHORT).show()
                                    } else {
                                        for (calamityDoc in calamityDocuments) {
                                            val calamity = calamityDoc.toObject(Project::class.java)
                                            calamityList.add(calamity)
                                        }
                                        // Display the calamities in a RecyclerView
                                        populateCalamityRecyclerView(calamityList)
                                    }
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(requireActivity(), "Error fetching calamities: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        Toast.makeText(requireActivity(), "No department found with the provided email.", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireActivity(), "Error fetching department: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(requireActivity(), "Please provide a valid email.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateCalamityRecyclerView(calamityList: MutableList<Project>) {
        calamityViewBinding.calamityRecycler.layoutManager = LinearLayoutManager(requireContext())
        calamityAdapter = MyCalamityAdapter(requireContext(), calamityList)
        calamityViewBinding.calamityRecycler.adapter = calamityAdapter
    }

}
