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
import com.example.hazardhub.databinding.FragmentHospitalRegisterBinding
import com.example.hazardhub.dataclass.Hospitals
import com.google.firebase.firestore.FirebaseFirestore

class HospitalRegisterFragment : Fragment() {
    lateinit var hospitalRegisterBinding : FragmentHospitalRegisterBinding
    lateinit var verifyBinding : ActivityVerifyBinding
    private val hospitalCollection = FirebaseFirestore.getInstance().collection("hospitals")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        verifyBinding = ActivityVerifyBinding.inflate(layoutInflater, container, false)
        hospitalRegisterBinding = FragmentHospitalRegisterBinding.inflate(layoutInflater,container,false)
        val statesAdapter = ArrayAdapter.createFromResource(container!!.context,R.array.states,android.R.layout.simple_spinner_item)
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        hospitalRegisterBinding.statesSpinner.adapter = statesAdapter

        hospitalRegisterBinding.registerBtn.setOnClickListener {
            saveHospitalData()
        }

        hospitalRegisterBinding.backHome.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager.beginTransaction().replace(verifyBinding.verifyFrame.id,LogInFragment()).commit()
        }


        return hospitalRegisterBinding.root
    }

    fun saveHospitalData() {
        val name = hospitalRegisterBinding.editTextName.text.toString()
        val email = hospitalRegisterBinding.editTextEmail.text.toString()
        val password = hospitalRegisterBinding.editRegisterPassword.text.toString()
        val bedCount = hospitalRegisterBinding.bedCount.text.toString().toIntOrNull() ?: 0
        val timings = hospitalRegisterBinding.time.text.toString()
        val addressLine1 = hospitalRegisterBinding.line1.text.toString()
        val addressLine2 = hospitalRegisterBinding.line2.text.toString()
        val address = "$addressLine1 , $addressLine2"
        val state = hospitalRegisterBinding.statesSpinner.selectedItem.toString()
        val pincode = hospitalRegisterBinding.pincodeDetail.text.toString()
        val phoneNum = hospitalRegisterBinding.editTextNum.text.toString()
        if (validateInputs(name, email, password, bedCount, timings, address, state, pincode, phoneNum)) {
            val newDepartmentRef = hospitalCollection.document()
            val hospitalId = newDepartmentRef.id

            val newHospital = Hospitals(
                hospitalId = hospitalId,
                name = name,
                email = email,
                password = password,
                numberOfBeds = bedCount,
                timings = timings,
                address = address,
                state = state,
                pincode = pincode,
                phoneNumber = phoneNum
            )

            hospitalCollection.document(hospitalId).set(newHospital)
                .addOnSuccessListener {
                    Toast.makeText(requireActivity(),"Hospital registered Successfully",Toast.LENGTH_SHORT).show()
                    hospitalRegisterBinding.registerBtn.visibility = View.GONE
                    hospitalRegisterBinding.backHome.visibility =View.VISIBLE
                }
                .addOnFailureListener{e->
                    Toast.makeText(requireActivity(),"Error : ${e.message}",Toast.LENGTH_SHORT).show()
                }
        }
    }
    private fun validateInputs(name: String, email: String, password: String, bedCount: Int, timings: String, addressLine1: String, state: String, pincode: String, phoneNum :String): Boolean {
        return when {
            name.isEmpty() ->{
                hospitalRegisterBinding.editTextName.error = "Name is Mandatory"
                false
            }
            email.isEmpty() -> {
                hospitalRegisterBinding.editTextEmail.error = "Email is required"
                false
            }
            password.isEmpty() -> {
                hospitalRegisterBinding.editRegisterPassword.error = "Password is required"
                false
            }
            bedCount < 0 -> {
                hospitalRegisterBinding.bedCount.error = "Bed count must be non-negative"
                false
            }
            timings.isEmpty() -> {
                hospitalRegisterBinding.time.error = "Timings are required"
                false
            }
            addressLine1.isEmpty() -> {
                hospitalRegisterBinding.line1.error = "Address Line 1 is required"
                false
            }
            state.isEmpty() -> {
                Toast.makeText(requireActivity(), "Please select a state", Toast.LENGTH_SHORT).show()
                false
            }
            pincode.isEmpty() -> {
                hospitalRegisterBinding.pincodeDetail.error = "Pincode is required"
                false
            }
            phoneNum.isEmpty() -> {
                hospitalRegisterBinding.editTextNum.error = "Phone Number is required"
                false
            }
            else -> true
        }
    }
}