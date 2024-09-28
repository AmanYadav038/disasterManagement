package com.example.hazardhub.verify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.hazardhub.R
import com.example.hazardhub.databinding.ActivityVerifyBinding
import com.example.hazardhub.databinding.FragmentVolunteerBinding
import com.example.hazardhub.dataclass.Volunteers
import com.google.firebase.firestore.FirebaseFirestore

class VolunteerFragment : Fragment() {
    lateinit var volunteerBinding: FragmentVolunteerBinding
    lateinit var verifyBinding : ActivityVerifyBinding
    val volunteerCollection = FirebaseFirestore.getInstance().collection("volunteers")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        volunteerBinding = FragmentVolunteerBinding.inflate(layoutInflater, container, false)
        verifyBinding = ActivityVerifyBinding.inflate(layoutInflater, container, false)

        val statesAdapter = ArrayAdapter.createFromResource(
            container!!.context,
            R.array.states,
            android.R.layout.simple_spinner_item
        )
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        volunteerBinding.statesSpinner.adapter = statesAdapter
        volunteerBinding.statesSpinner.setSelection(0)

        val bloodAdapter = ArrayAdapter.createFromResource(
            container.context,
            R.array.blood_groups,
            android.R.layout.simple_spinner_item
        )
        volunteerBinding.backHome.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager.beginTransaction().replace(verifyBinding.verifyFrame.id,LogInFragment()).commit()
        }
        bloodAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        volunteerBinding.bloodSpinner.adapter = bloodAdapter
        volunteerBinding.bloodSpinner.setSelection(0)


        volunteerBinding.registerBtn.setOnClickListener {
            saveVolunteerData()
        }
        return volunteerBinding.root
    }

    private fun saveVolunteerData() {
        val email = volunteerBinding.editTextEmail.text.toString()
        val name = volunteerBinding.editName.text.toString()
        val password = volunteerBinding.editRegisterPassword.text.toString()
        val phoneNum = volunteerBinding.editTextNum.text.toString()
        val addressLine1 = volunteerBinding.line1.text.toString()
        val addressLine2 = volunteerBinding.line2.text.toString()
        val address = "$addressLine1, $addressLine2"
        val state = volunteerBinding.statesSpinner.selectedItem.toString()
        val pincode = volunteerBinding.pincodeDetail.text.toString()
        val bloodGrp = volunteerBinding.bloodSpinner.selectedItem.toString()

        if (validateVolunteer(
                email,
                name,
                password,
                phoneNum,
                addressLine1,
                state,
                pincode,
                bloodGrp
            )
        ) {
            val newVolunteerRef = volunteerCollection.document()
            val volunteerId = newVolunteerRef.id

            val newVolunteer = Volunteers(
                email = email,
                password = password,
                name = name,
                address = address,
                state = state,
                pincode = pincode,
                mobile = phoneNum,
                bloodGroup = bloodGrp
            )

            volunteerCollection.document(volunteerId).set(newVolunteer)
                .addOnSuccessListener {
                    Toast.makeText(
                        requireActivity(),
                        "Volunteer registered Successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                    volunteerBinding.registerBtn.visibility = View.GONE
                    volunteerBinding.backHome.visibility =View.VISIBLE
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireActivity(), "Error : ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun validateVolunteer(
        email: String,
        name: String,
        password: String,
        phoneNum: String,
        address: String,
        state: String,
        pincode: String,
        bloodGrp: String
    ): Boolean {
        return when {
            email.isEmpty() -> {
                volunteerBinding.editTextEmail.error = "Email is required"
                false
            }

            password.isEmpty() -> {
                volunteerBinding.editRegisterPassword.error = "Password is required"
                false
            }

            bloodGrp.isEmpty() -> {
                Toast.makeText(requireActivity(), "Please select a blood grp", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            name.isEmpty() -> {
                volunteerBinding.editName.error = "Timings are required"
                false
            }

            address.isEmpty() -> {
                volunteerBinding.line1.error = "Address Line 1 is required"
                false
            }

            state.isEmpty() -> {
                Toast.makeText(requireActivity(), "Please select a state", Toast.LENGTH_SHORT)
                    .show()
                false
            }

            pincode.isEmpty() -> {
                volunteerBinding.pincodeDetail.error = "Pincode is required"
                false
            }

            phoneNum.isEmpty() -> {
                volunteerBinding.editTextNum.error = "Phone Number is required"
                false
            }

            else -> true
        }
    }
}