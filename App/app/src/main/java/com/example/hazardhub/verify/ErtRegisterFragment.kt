package com.example.hazardhub.verify

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.hazardhub.R
import com.example.hazardhub.databinding.ActivityVerifyBinding
import com.example.hazardhub.databinding.FragmentErtRegisterBinding
import com.example.hazardhub.dataclass.Department
import com.example.hazardhub.dataclass.ERT
import com.google.firebase.firestore.FirebaseFirestore

class ErtRegisterFragment : Fragment() {
    lateinit var verifyBinding : ActivityVerifyBinding
    lateinit var ertBinding : FragmentErtRegisterBinding
    val ertCollection = FirebaseFirestore.getInstance().collection("ert")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        verifyBinding = ActivityVerifyBinding.inflate(layoutInflater, container, false)
        ertBinding = FragmentErtRegisterBinding.inflate(layoutInflater,container,false)

        ertBinding.registerBtn.setOnClickListener {
            val email = ertBinding.editTextEmail.text.toString()
            val password = ertBinding.editRegisterPassword.text.toString()

            if(validateInput(email, password)){
                saveERTData(email,password)
            }
        }

        ertBinding.backHome.setOnClickListener{
            val fragmentManager = requireActivity().supportFragmentManager.beginTransaction().replace(verifyBinding.verifyFrame.id,LogInFragment()).commit()
        }
        return ertBinding.root
    }

    private fun saveERTData(email: String, password: String){
        val newErtRef = ertCollection.document()
        val ertId = newErtRef.id

        val newErt = ERT(
            ertId = ertId,
            email = email,
            password = password
        )

        ertCollection.document(ertId).set(newErt)
            .addOnSuccessListener {
                Toast.makeText(requireActivity(),"Emergency Team registered successfully", Toast.LENGTH_SHORT).show()
                ertBinding.registerBtn.visibility = View.GONE
                ertBinding.backHome.visibility =View.VISIBLE
            }
            .addOnFailureListener{e->
                Toast.makeText(requireActivity(),"Error : ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    fun validateInput(email:String, password :String) : Boolean{

        if (email.isEmpty()) {
            ertBinding.editTextEmail.error = "Email is required"
            return false
        }
        if (password.isEmpty()) {
            ertBinding.editRegisterPassword.error = "Password is required"
            return false
        }
        return true
    }

}